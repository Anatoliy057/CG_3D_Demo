package stud.task.cg.model;


import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Sphere implements Model, Move {

    private List<List<Vertex>> vertex = new ArrayList<>();
    private double radius;
    private Color color;
    private Vector4 center;

    private List<Contour> contours;

    public Sphere(Color color, int n, int m, double radius, Vector4 center) {
        this.color = color;
        this.radius = radius;
        this.center = center;
        contours = createContours(n, m);
    }

    private void createVertexes(float n, float m) {
        double p;
        double z;
        double a = (Math.PI * 2 / m);
        for (int i = 0; i <= n; i++) {
            List<Vertex> l = new LinkedList<>();
            vertex.add(l);
            for (int j = 0; j <= m-1; j++) {
                if (i == 0) {
                    Vertex v = new Vertex(new Vector4(
                            center.getX(),
                            center.getY(),
                            center.getZ() + radius
                    ), color);
                    for (int k = 0; k <= m - 1; k++) {
                        l.add(v);
                    }
                    break;
                } else if (i == n) {
                    Vertex v = new Vertex(new Vector4(
                            center.getX(),
                            center.getY(),
                            center.getZ() - radius
                    ), color);
                    for (int k = 0; k <= m - 1; k++) {
                        l.add(v);
                    }
                    break;
                } else {
                    z = radius * (1 - 2 * i / n);
                    p = Math.sqrt(Math.pow(radius, 2) - Math.pow(z, 2));
                    l.add(new Vertex(new Vector4(
                            (p * Math.cos(a * j)) + center.getX(),
                            (p * Math.sin(a * j)) + center.getY(),
                            z + center.getZ()
                    ), color));
                }
            }
        }
    }

    @Override
    public void setPosition(Vector4 pos) {
        Vector4 diff = pos.add(center.negative());
        center = new Vector4(pos);
        contours = contours.stream()
                .map(c -> Contour.conversionDeep(c, v -> true, v -> v.add(diff)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Vector4 getPosition() {
        return center;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public List<Contour> getContours() {
       return contours;
    }

    private List<Contour> createContours(float n, float m) {
        createVertexes(n,m);
        List<Contour> contours = new LinkedList<>();
        vertex.get(0).add(vertex.get(0).get(0));
        for (int i = 0; i < vertex.size()-1; i++) {
            vertex.get(i+1).add(vertex.get(i+1).get(0));
            Iterator<Vertex> upIt;
            Iterator<Vertex> downIt;
            Vertex lastUp, lastDown, curUp, curDown;
            if (i == vertex.size()-2) {
                upIt = vertex.get(i+1).iterator();
                Collections.reverse(vertex.get(i));
                downIt = vertex.get(i).iterator();
            } else {
                upIt = vertex.get(i).iterator();
                downIt = vertex.get(i+1).iterator();
            }
            if (upIt.hasNext()) {
                lastUp = upIt.next(); lastDown = downIt.next();
            } else break;
            while (downIt.hasNext()) {
                curUp = upIt.next();
                curDown = downIt.next();
                contours.add(new Contour(Arrays.asList(lastDown, curDown, curUp, lastUp), color, true));
                lastDown = curDown;
                lastUp = curUp;
            }
        }

        Contour.setUpNormals(contours);

        return contours;
    }
}
