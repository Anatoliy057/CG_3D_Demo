package stud.task.cg.model;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Line implements Model, Move{

    private Vector4 start, end;
    private Color c;

    public Line(Vector4 start, Vector4 end, Color c) {
        this.start = start;
        this.end = end;
        this.c = c;
    }

    public Line(Vector4 start, Vector3 vector, Color c) {
        this(start, start.add(vector), c);
    }

    @Override
    public void setPosition(Vector4 v) {
        Vector3 vector = end.add(start.negative()).toVector3();
        start = v;
        end = v.add(vector);
    }

    @Override
    public Vector4 getPosition() {
        return start;
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public Collection<Contour> getContours() {
        return Collections.singleton(new Contour(Arrays.asList(
                new Vertex(start, c),
                new Vertex(end, c)
        ), c, false));
    }
}
