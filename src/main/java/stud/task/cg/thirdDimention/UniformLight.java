package stud.task.cg.thirdDimention;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Model;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UniformLight implements Light, Model {

    private Vector4 pos;
    private Color up = Color.WHITE;
    private Cube cube;
    private double r;

    public UniformLight(Vector4 pos, double r) {
        this.pos = new Vector4(pos);
        this.r = r;
        cube = new Cube(pos, Color.WHITE, 1);
    }

    public UniformLight(Vector4 pos, Color up, double r) {
        this(pos, r);
        this.up = up;
    }

    @Override
    public Color lightUp(List<Contour> list, Contour c, Color color) {
        if (c.isEmpty() || c.size() < 3) return c.getColor();

        Vector4 abgV = c.abg();
        Vector4 n = c.normal();

        Vector4 ln = new Vector4(
                abgV.getX() - pos.getX(),
                abgV.getY() - pos.getY(),
                abgV.getZ() - pos.getZ(),
                0
        );

        boolean inter = false;
        double dis = distance(c, ln, pos);
        for (Contour cur :
                list) {
            if (cur != c && dis > (distance(cur, ln, pos))) {
                inter = true;
                break;
            }
        }

        if (!inter) {
            double angle = ln.scalar(n) / (ln.module() * n.module());
            double length = ln.module();

            double ratio = Math.abs(angle);
            ratio *= r > length ? ((r-length) * (r-length)) / (r * r) : 0;
            return mix(color, up, ratio * 0.8);
        } else
            return color;
    }


    @Override
    public Collection<Contour> getContour() {
        return cube.getContour();
    }

    @Override
    public Collection<Contour> getPolygon() {
        return Collections.emptyList();
    }

    private double distance(Contour c, Vector4 ln, Vector4 p0) {
        Vector4 n = c.normal();
        double scalar = n.scalar(ln);
        if (scalar == 0) return Double.MAX_VALUE;
        double d = c.D(n);
        double t0 = (-n.toVector3().scalar(p0.toVector3()) - d) / scalar;
        Vector4 inter = ln.mul(t0).add(p0);
        Iterator<Vector4> it = c.iterator();
        Vector4 cur,  last = it.next();
        double angle = 0;
        while (it.hasNext()) {
            cur = it.next();
            angle += calAnge(inter, last, cur);
            last = cur;
        }
        return angle > 2 * Math.PI - 1e-1 ? inter.toVertex().add(pos.toVector3().negative()).module() : Double.MAX_VALUE;
    }

    private double calAnge(Vector4 s, Vector4 r, Vector4 l) {
        Vector3 rv3 = r.toVector3();
        Vector3 lv3 = l.toVector3();
        Vector3 sv3 = s.toVector3().negative();
        Vector3 v1 = rv3.add(sv3);
        Vector3 v2 = lv3.add(sv3);
        return  Math.acos(v1.scalar(v2) / (v1.module() * v2.module()));
    }
}
