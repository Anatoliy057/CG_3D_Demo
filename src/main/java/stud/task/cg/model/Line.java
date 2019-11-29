package stud.task.cg.model;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Line implements Model{

    private Vector4 start, end;
    private Color c;

    public Line(Vector4 start, Vector4 end, Color c) {
        this.start = start;
        this.end = end;
        this.c = c;
    }

    @Override
    public Collection<Contour> getContour() {
        return Collections.singleton(new Contour(c, true, start, end));
    }

}