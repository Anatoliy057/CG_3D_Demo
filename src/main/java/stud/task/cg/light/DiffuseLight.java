package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Model;

import java.awt.*;
import java.util.Collection;

import static stud.task.cg.math.VectorUtil.*;

public class DiffuseLight implements Light, Model {

    private Vector4 position;
    private Color color;
    private Cube cube;
    private double radius;

    public DiffuseLight (Vector4 position, Color color, double radius, double a) {
        this.position = new Vector4(position);
        this.color = color;
        this.radius = radius;
        cube = new Cube(position, color, a);
    }

    @Override
    public void light(Contour c) {
        if (c.isEmpty() || !c.isClose()) return;

        Vector3 contourNormal = c.getNormal();
        Vector4 lengthVector4 = position.add(c.getPosition());
        Vector3 lightNormal = normalize(lengthVector4.toVector3());

        double dot = dot(lightNormal, contourNormal);
        if (dot <= 0) {
            return;
        }

        double length = module(lengthVector4);
        if (length > radius) return;

        double ratio = dot * ((Math.pow((radius - length), 2)) / (Math.pow(radius, 2)));
        Color color = Light.mix(c.getColor(), this.color, ratio);
        c.setColor(color);
        c.getVertices().forEach(v -> v.setColor(color));
    }


    @Override
    public Collection<Contour> getContours() {
        return cube.getContours();
    }

    public Vector4 getPosition() {
        return position;
    }

    public void setPosition(Vector4 vector4) {
        position = new Vector4(vector4);
        cube.setPosition(position);
    }
}
