package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector4;

import java.awt.*;

public class WorldLight implements Light {

    private Color color = Color.white;
    private double k;

    public WorldLight(double k) {
        this.k = k;
    }

    public WorldLight(Color color, double k) {
        this.color = color;
        this.k = k;
    }

    @Override
    public void light(Contour c) {
        c.setColor(LightUtil.mix(c.getColor(), color, k));
    }

    @Override
    public TypeLight getType() {
        return TypeLight.WORLD;
    }

    @Override
    public void setColor(Color color) {
        throw new UnsupportedOperationException("World light must by have only WHITE color");
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getRadius() {
        return -1;
    }

    @Override
    public void setRadius(double radius) {

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
