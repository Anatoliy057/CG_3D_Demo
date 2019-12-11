package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Model;
import stud.task.cg.model.Move;

import java.awt.*;
import java.util.Collection;

public class DiffuseLight implements Light, Model, Move {

    private Vector4 position;
    private Color color;
    private Cube cube;
    private double radius;
    private double k;

    public DiffuseLight(Vector4 position, Color color, double a, double radius, double k) {
        this.position = position;
        this.color = color;
        this.radius = radius;
        this.k = k;

        cube = new Cube(position, color, a, false);
    }

    @Override
    public void light(Contour c) {
        double ratio = LightUtil.ratio(c.getNormal(), position.add(c.getPosition().negative()), radius);
        c.setColor(LightUtil.mix(c.getColor(), this.color, ratio * k));
    }


    @Override
    public Collection<Contour> getContours() {
        return cube.getContours();
    }

    @Override
    public Vector4 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector4 vector4) {
        position = new Vector4(vector4);
        cube.setPosition(position);
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
        cube.setColor(c);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public TypeLight getType() {
        return TypeLight.DIFF;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getK() {
        return k;
    }

    @Override
    public void setK(double k) {
        if (k < 0 || k > 1) throw new ArithmeticException("K must be in [0,1], then: " + k);
        this.k = k;
    }
}
