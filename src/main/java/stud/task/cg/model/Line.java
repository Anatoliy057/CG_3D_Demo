package stud.task.cg.model;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Line implements Model{

    private Vector4 start, end;
    private Color c;

    public Line(Vector4 start, Vector4 end, Color c) {
        this.start = start;
        this.end = end;
        this.c = c;
    }

    @Override
    public Collection<Contour> getContours() {
        return Collections.singleton(new Contour(Arrays.asList(
                new Vertex(start, c),
                new Vertex(end, c)
        ), c, false));
    }

    @Override
    public Collection<Contour> getPolygon() {
        return Collections.emptyList();
    }
}
