package stud.task.cg.model;


import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Sphere implements Model {

    private List<List<Vector4>> vertex = new ArrayList<>();
    private float radius;
    private Color color;
    private Vector4 center;

    private List<Contour> contours;

    public Sphere(Color color, int n, int m, float radius, Vector4 center) {
        this.color = color;
        this.radius = radius;
        this.center = center;
        contours = createContours(n, m);
    }

    private void createVertexes(float n, float m) {
        double p;
        double z;
        double a = (Math.PI * 2 / m);
        for (int i = 0; i <= n-1; i++) {
            List<Vector4> l = new LinkedList<>();
            vertex.add(l);
            for (int j = 0; j <= m; j++) {
                z = radius * (1 - 2 * i / n);
                p = Math.sqrt(Math.pow(radius, 2) - Math.pow(z, 2));
                l.add(new Vector4(
                        (p * Math.cos(a * j))+center.getX(),
                        (p * Math.sin(a * j))+center.getY(),
                        z+center.getZ())
                );
            }
        }
    }

    @Override
    public List<Contour> getContours() {
       return contours;
    }

    @Override
    public Collection<Contour> getPolygon() {
        return contours;
    }

    private List<Contour> createContours(float n, float m) {
        createVertexes(n,m);
        List<Contour> contours = new LinkedList<>();
        for (int i = 0; i < vertex.size()-1; i++) {
            Iterator<Vector4> upIt = vertex.get(i).iterator();
            Iterator<Vector4> downIt = vertex.get(i+1).iterator();
            Vertex lastUp, lastDown, curUp, curDown;
            if (upIt.hasNext()) {
                lastUp = createVertex(upIt.next()); lastDown = createVertex(downIt.next());
            } else break;
            while (downIt.hasNext()) {
                curUp = createVertex(upIt.next());
                curDown = createVertex(downIt.next());
                contours.add(new Contour(Arrays.asList(lastDown, curDown, curUp, lastUp, lastDown), color, true));
                lastDown = curDown;
                lastUp = curUp;
            }
        }

        contours.forEach(c -> {
            c.getVertices().forEach(v -> v.addNormalOfContour(c.getNormal()));
        });

        contours.forEach(c -> c.getVertices().forEach(Vertex::calNormal));

        return contours;
    }

    private Vertex createVertex(Vector4 v) {
        return new Vertex(new Vector4(v), color);
    }
}
