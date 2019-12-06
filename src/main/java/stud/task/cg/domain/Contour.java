package stud.task.cg.domain;

import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.math.VectorUtil;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Contour implements Iterable<Vertex>  {

    private List<Vertex> vertices;
    private Color color;
    private Vector3 normal;
    private Vector4 position;
    private boolean close;

    public Contour(List<Vertex> vertices, Color color, boolean close) {
        this.vertices = new LinkedList<>(vertices);
        this.close = close;
        this.color = color;
        abg();
        normal();
    }

    public Contour(List<Vertex> vertices, Vector3 normal, Color color, boolean close) {
        this.vertices = new ArrayList<>(vertices);
        this.close = close;
        this.color = color;
        this.normal = new Vector3(normal);
        abg();
    }

    public Contour(List<Vertex> vertices, Vector4 position, Color color, boolean close) {
        this.vertices = new ArrayList<>(vertices);
        this.close = close;
        this.color = color;
        this.position = new Vector4(position);
        normal();
    }

    public Contour(List<Vertex> vertices, Vector3 normal, Vector4 position, Color color, boolean close) {
        this.vertices = new ArrayList<>(vertices);
        this.close = close;
        this.color = color;
        this.position = new Vector4(position);
        this.normal = new Vector3(normal);
    }

    private Contour() {}

    public static Contour copyOf(Contour c) {
        Contour copy = new Contour();
        List<Vertex> vs = new ArrayList<>();
        c.getVertices().forEach(v -> {
            vs.add(new Vertex(v.getPosition(), v.getNormal(), v.getColor()));
        });
        copy.vertices = vs;
        copy.close = c.close;
        copy.color = c.color;
        copy.normal = c.normal;
        copy.position = c.position;
        return copy;
    }

    public static Contour conversion(Contour contour, Function<Vertex, Vertex> fun) {
        List<Vertex> vertices = new LinkedList<>();
        contour.forEach(v -> vertices.add(fun.apply(v)));
        return new Contour(vertices, contour.getColor(), contour.isClose());
    }

    public static Contour conversion(Contour contour, Predicate<Vertex> p, Function<Vertex, Vertex> fun) {
        List<Vertex> vertices = new LinkedList<>();
        contour.forEach(v -> {
            Vertex vv = fun.apply(v);
            if (p.test(vv))
                vertices.add(vv);
        });
        return new Contour(vertices, contour.getColor(), contour.isClose());
    }

    public static Contour conversionDeep(Contour contour, Function<Vector4, Vector4> fun) {
        List<Vertex> vertices = new LinkedList<>();
        contour.forEach(v ->  {
            vertices.add(new Vertex(fun.apply(v.getPosition()), v.getNormal(), v.getColor()));
        });
        return new Contour(vertices, contour.getNormal(), contour.getColor(), contour.isClose());
    }

    public static Contour conversionDeep(Contour contour, Predicate<Vector4> p, Function<Vector4, Vector4> fun) {
        List<Vertex> vertices = new LinkedList<>();
        contour.forEach(v -> {
            Vector4 vv = fun.apply(v.getPosition());
            if (p.test(vv))
                vertices.add(new Vertex(vv, v.getNormal(), v.getColor()));
        });
        return new Contour(vertices, contour.getColor(), contour.isClose());
    }

    public static void toClose(Contour contour) {
        if (contour.isClose() || contour.size() < 3 || contour.isEmpty())
            return;
        List<Vertex> vertices = contour.getVertices();
        vertices.add(vertices.get(0));
        contour.setClose(true);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public Vector4 getPosition() {
        return position;
    }

    public void setPosition(Vector4 position) {
        this.position = position;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public int size() {
        return vertices.size();
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public Iterator<Vertex> iterator() {
        return vertices.iterator();
    }

    public void abg() {
        position = Vector4.empty();
        int size = isClose() ? size()-1 : size();
        Iterator<Vertex> it = vertices.iterator();
        for (int i = 0; i < size; i++) {
            position = position.add(it.next().getPosition());
        }
        position = position.mul(1 / (double) size);
    }

    public void normal() {
        if (isEmpty() || size() < 3) {
            normal = Vector3.empty();
            return;
        }
        Vector4[] vs = new Vector4[3];
        Iterator<Vertex> it = iterator();
        for (int i = 0; i < vs.length; i++) {
            vs[i] = new Vector4(it.next().getPosition());
        }
        normal = VectorUtil.normalize(new Vector3(
                (vs[1].getY() - vs[0].getY()) * (vs[2].getZ() - vs[0].getZ()) - (vs[2].getY() - vs[0].getY()) * (vs[1].getZ() - vs[0].getZ()),
                (vs[1].getX() - vs[0].getX()) * (vs[2].getZ() - vs[0].getZ()) - (vs[2].getX() - vs[0].getX()) * (vs[1].getZ() - vs[0].getZ()),
                (vs[1].getX() - vs[0].getX()) * (vs[2].getY() - vs[0].getY()) - (vs[2].getX() - vs[0].getX()) * (vs[1].getY() - vs[0].getY())
        ));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Contour.class.getSimpleName() + "[", "]")
                .add("vertices=" + vertices)
                .add("color=" + color)
                .add("normal=" + normal)
                .add("position=" + position)
                .add("close=" + close)
                .toString();
    }
}
