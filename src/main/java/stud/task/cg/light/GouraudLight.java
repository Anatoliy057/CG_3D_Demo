package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Model;

import java.awt.*;
import java.util.Collection;

import static stud.task.cg.math.VectorUtil.*;

public class GouraudLight implements Light, Model {

    private Vector4 position;
    private Color color;
    private Cube cube;
    private double radius;

    public GouraudLight (Vector4 position, Color color, double radius, double a) {
        this.position = new Vector4(position);
        this.color = color;
        this.radius = radius;
        cube = new Cube(position, color, a, false);
    }

    @Override
    public void light(Contour c) {
        c.getVertices().forEach(this::calColorVertex);
    }

    private void calColorVertex(Vertex v) {
        double ratio = LightUtil.ratio(v.getNormal(), position.add(v.getPosition()), radius);
        v.setColor(LightUtil.mix(v.getColor(), this.color, ratio));
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
        color = c;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public TypeLight getType() {
        return TypeLight.GURO;
    }
}
