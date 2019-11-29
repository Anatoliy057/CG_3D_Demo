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
        this.vertexes = new LinkedList<>(vertexes);
        this.close = close;
        this.c = c;
    }

    public Contour(List<Vector4> vertexes, boolean close) {
        this.vertexes = new LinkedList<>(vertexes);
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
        vertexes = new LinkedList<>();
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

    public void close() {
        if (vertexes.isEmpty() || vertexes.size() < 3) return;
        vertexes.add(vertexes.get(0));
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
