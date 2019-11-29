package stud.task.cg.domain;

import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.Objects;
import java.util.StringJoiner;

public class Vertex {

    private Vector4 pos;
    private Color c;

    public Vertex(Vector4 pos, Color c) {
        this.pos = pos;
        this.c = c;
    }

    public Vector4 getPos() {
        return pos;
    }

    public void setPos(Vector4 pos) {
        this.pos = pos;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return pos.equals(vertex.pos) &&
                c.equals(vertex.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, c);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vertex.class.getSimpleName() + "[", "]")
                .add("pos=" + pos)
                .add("c=" + c)
                .toString();
    }
}
