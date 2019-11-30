package stud.task.cg.domain;

import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Contour implements Iterable<Vector4>  {

    private List<Vector4> vertexes;
    private Color c = Color.BLACK;
    private boolean close;

    public Contour(List<Vector4> vertexes, Color c, boolean close) {
        this.vertexes = new ArrayList<>(vertexes);
        this.close = close;
        this.c = c;
    }

    public Contour(List<Vector4> vertexes, boolean close) {
        this.vertexes = new ArrayList<>(vertexes);
        this.close = close;
    }

    public Contour(List<Vector4> vertexes) {
        this(vertexes, false);
    }

    public Contour(Vector4...vertexes) {
        this(Arrays.asList(vertexes), false);
    }

    public Contour(Color c, boolean close, Vector4...vertexes) {
        this(Arrays.asList(vertexes), c, close);
    }

    public Contour(Color c) {
        this();
        this.c = c;
    }

    public Contour() {
        vertexes = new ArrayList<>();
        close = false;
        c = Color.BLACK;
    }

    public static Contour conversion(Contour src, Function<Vector4, Vector4> conv) {
        Contour c = new Contour();
        src.forEach(v -> c.addVertex(conv.apply(v)));
        return c;
    }

    public static Contour conversion(Contour src, Predicate<Vector4> p, Function<Vector4, Vector4> conv) {
        Contour c = new Contour(src.getColor());
        src.forEach(v -> {
            Vector4 cv = conv.apply(v);
            if (p.test(cv))
                c.addVertex(cv);
        });
        return c;
    }

    public boolean addVertex(Vector4 v4) {
        vertexes.add(v4);
        return !close;
    }

    public double abgZ() {
        double abgZ = 0;
        for (Vector4 v :
                vertexes) {
            abgZ += v.getZ();
        }
        return abgZ / vertexes.size();
    }

    public Vector4 abg() {
        double abgZ = 0, abgX = 0, abgY = 0;
        int size = isClose() ? size()-1 : size();
        for (int i = 0; i < size; i++) {
            abgX += vertexes.get(i).getX();
            abgY += vertexes.get(i).getY();
            abgZ += vertexes.get(i).getZ();
        }
        return new Vector4(
                abgX / size,
                abgY / size,
                abgZ / size
        );
    }

    public Vector4 normal() {
        if (isEmpty() || size() < 3) return Vector4.empty();
        Vector4[] vs = new Vector4[3];
        Iterator<Vector4> it = iterator();
        for (int i = 0; i < vs.length; i++) {
            vs[i] = new Vector4(it.next());
        }
        return new Vector4(
                (vs[1].getY() - vs[0].getY()) * (vs[2].getZ() - vs[0].getZ()) - (vs[2].getY() - vs[0].getY()) * (vs[1].getZ() - vs[0].getZ()),
                (vs[1].getX() - vs[0].getX()) * (vs[2].getZ() - vs[0].getZ()) - (vs[2].getX() - vs[0].getX()) * (vs[1].getZ() - vs[0].getZ()),
                (vs[1].getX() - vs[0].getX()) * (vs[2].getY() - vs[0].getY()) - (vs[2].getX() - vs[0].getX()) * (vs[1].getY() - vs[0].getY()),
                0
        );
    }

    public double D() {
        double d = 0;
        if (isEmpty() || size() < 3) return d;
        Vector4 n = normal();
        return D(n);
    }

    public double D(Vector4 n) {
        double d = 0;
        Vector4 p0 = vertexes.get(0);
        d += -p0.getX() * n.getX();
        d += -p0.getY() * n.getY();
        d += -p0.getZ() * n.getZ();
        return d;
    }

    public void close() {
        if (vertexes.isEmpty() || vertexes.size() < 3) return;
        vertexes.add(vertexes.get(0));
        close = true;
    }

    public boolean isClose() {
        return close;
    }

    public boolean isEmpty() {
        return vertexes.isEmpty();
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public int size() {
        return vertexes.size();
    }

    public static Iterator<Vector4> getCloseIterator(Contour c) {
        if (c.isEmpty()) return Collections.emptyIterator();
        if (c.isClose() || c.getVertexes().size() < 3) return c.getVertexes().iterator();
        List<Vector4> list = new LinkedList<>(c.getVertexes());
        list.add(list.get(0));
        return list.iterator();
    }

    @Override
    public Iterator<Vector4> iterator() {
        return vertexes.iterator();
    }

    private List<Vector4> getVertexes() {
        return vertexes;
    }
}
