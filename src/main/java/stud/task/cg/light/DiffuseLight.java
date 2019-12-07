package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Model;

import java.awt.*;
import java.util.Collection;

public class DiffuseLight implements Light, Model {

    private Vector4 position;
    private Color color;
    private Cube cube;
    private double radius;

    public DiffuseLight (Vector4 position, Color color, double radius, double a) {
        this.position = new Vector4(position);
        this.color = color;
        this.radius = radius;
        cube = new Cube(position, color, a, false);
    }

    @Override
    public void light(Contour c) {
        double ratio = LightUtil.ratio(c.getNormal(), position.add(c.getPosition().negative()), radius);
        c.setColor(LightUtil.mix(c.getColor(), this.color, ratio));
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
}
