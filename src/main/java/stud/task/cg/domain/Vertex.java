package stud.task.cg.domain;

import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.math.VectorUtil;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Vertex {

    private Vector4 position;
    private Vector3 normal = Vector3.empty();
    private Color color;

    private List<Vector3> normals;

    public Vertex(Vector4 position, Color color) {
        this.position = new Vector4(position);
        this.color = color;
        normals = new LinkedList<>();
    }

    public Vertex(Vector4 position, Vector3 normal, Color color) {
        this.position = new Vector4(position);
        this.color = color;
        this.normal = new Vector3(normal);
    }

    public void addNormalOfContour(Vector3 n) {
        normals.add(n);
    }

    public void calNormal() {
        if (normals == null || normals.isEmpty()) return;
        normal = Vector3.empty();
        for (Vector3 v :
                normals) {
            normal = normal.add(v);
        }
        normal = VectorUtil.normalize(normal);
        normals.clear();
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector4 getPosition() {
        return position;
    }

    public void setPosition(Vector4 position) {
        this.position = position;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public static Vertex conversion(Vertex v, Function<Vector4, Vector4> fun) {
        return new Vertex(fun.apply(v.getPosition()), v.getNormal(), v.getColor());
    }

    public static Vertex conversion(Vertex v, Predicate<Vector4> pred, Function<Vector4, Vector4> fun) {
        Vector4 vv = fun.apply(v.getPosition());
        if (pred.test(vv))
            return new Vertex(vv, v.getNormal(), v.getColor());
        else
            return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return position.equals(vertex.position) &&
                Objects.equals(normal, vertex.normal) &&
                color.equals(vertex.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, normal, color);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vertex.class.getSimpleName() + "[", "]")
                .add("position=" + position)
                .add("normal=" + normal)
                .add("color=" + color)
                .toString();
    }
}
