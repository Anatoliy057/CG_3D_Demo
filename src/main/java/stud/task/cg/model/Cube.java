package stud.task.cg.model;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.MatrixUtil;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Cube implements Model {

    private Vector4 center;
    private Color cc, cp;
    private final double a;

    public Cube(Vector4 center, Color c, double a) {
        this.center = center;
        cc = cp = c;
        this.a = a;
    }

    public Cube(Vector4 center, Color cc, Color cp, double a) {
        this.center = center;
        this.cc = cc;
        this.cp = cp;
        this.a = a;
    }

    @Override
    public Collection<Contour> getContour() {
        return createContours(cc);
    }

    @Override
    public Collection<Contour> getPolygon() {
        return createContours(cp);
    }

    private List<Contour> createContours(Color c) {
        List<Contour> contours = new LinkedList<>();
        for (int i = -3; i < 4; i++) {
            if (i == 0) continue;
            contours.add(createContour(c, i));
        }
        return contours;
    }

    private Contour createContour(Color color, int axis) {
        Contour c = new Contour(color);
        switch (Math.abs(axis)) {
            case MatrixUtil.AXIS_X+1: {
                Vector4 start = new Vector4(
                        center.getX() + a/2 * Math.signum(axis),
                        center.getY() - a/2,
                        center.getZ() - a/2
                );
                c.addVertex(start);
                c.addVertex(new Vector4(
                        start.getX(),
                        start.getY() + a,
                        start.getZ()
                ));
                c.addVertex(new Vector4(
                        start.getX(),
                        start.getY() + a,
                        start.getZ() + a
                ));
                c.addVertex(new Vector4(
                        start.getX(),
                        start.getY(),
                        start.getZ() + a
                ));
                break;
            }
            case MatrixUtil.AXIS_Y + 1: {
                Vector4 start = new Vector4(
                        center.getX() - a/2,
                        center.getY() + a/2 * Math.signum(axis),
                        center.getZ() - a/2
                );
                c.addVertex(start);
                c.addVertex(new Vector4(
                        start.getX() + a,
                        start.getY(),
                        start.getZ()
                ));
                c.addVertex(new Vector4(
                        start.getX() + a,
                        start.getY(),
                        start.getZ() + a
                ));
                c.addVertex(new Vector4(
                        start.getX(),
                        start.getY(),
                        start.getZ() + a
                ));
                break;
            }
            case MatrixUtil.AXIS_Z + 1: {
                Vector4 start = new Vector4(
                        center.getX() - a/2,
                        center.getY() - a/2,
                        center.getZ() + a/2 * Math.signum(axis)
                );
                c.addVertex(start);
                c.addVertex(new Vector4(
                        start.getX() + a,
                        start.getY(),
                        start.getZ()
                ));
                c.addVertex(new Vector4(
                        start.getX() + a,
                        start.getY() + a,
                        start.getZ()
                ));
                c.addVertex(new Vector4(
                        start.getX(),
                        start.getY() + a,
                        start.getZ()
                ));
                break;
            }
        }
        c.close();
        return c;
    }
}
