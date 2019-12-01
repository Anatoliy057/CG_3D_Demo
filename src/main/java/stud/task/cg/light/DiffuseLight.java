package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Model;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;

import static stud.task.cg.math.VectorUtil.*;

public class DiffuseLight implements Light, Model {

    private Vector4 pos;
    private Color up = Color.WHITE;
    private Cube cube;
    private double r;

    public DiffuseLight (Vector4 pos, double r) {
        this.pos = new Vector4(pos);
        this.r = r;
        cube = new Cube(pos, Color.WHITE, 1);
    }

    public DiffuseLight (Vector4 pos, Color up, double r) {
        this(pos, r);
        this.up = up;
    }

    @Override
    public Color lightUp(Contour c, Color color) {
        if (c.isEmpty() || !c.isClose()) return c.getColor();

        Vector4 n = Vector4.nonTransfer(normalize(c.normal()));
        Vector4 ln = Vector4.nonTransfer(c.abg().add(pos.negative()));
        Vector4 normalLN = normalize(ln);

        double dot = dot(normalLN, n);
        if (dot >= 0) return color;

        double length = module(ln);
        if (length > r) return color;

        double ratio = -dot * ((Math.pow((r - length), 2)) / (Math.pow(r, 2)));
        return mix(color, up, ratio);
    }


    @Override
    public Collection<Contour> getContour() {
        return cube.getContour();
    }

    @Override
    public Collection<Contour> getPolygon() {
        return Collections.emptyList();
    }

    @Override
    public Vector4 getPos() {
        return pos;
    }

    @Override
    public void setPos(Vector4 vector4) {
        pos = new Vector4(vector4);
        cube.setCenter(pos);
    }
}
