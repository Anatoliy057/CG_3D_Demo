package stud.task.cg.model;


import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Sphere implements Model {

    private List<List<Vector4>> vertex = new ArrayList<>();
    private float radius;
    private Color c;
    private Vector4 center;

    public Sphere(Color c, int n, int m, float radius, Vector4 center) {
        this.c = c;
        this.radius = radius;
        this.center = center;
        createVertexes(n,m);
    }

    private void createVertexes(float n, float m) {
        double p;
        double z;
        double a = (Math.PI * 2 / m);
        for (int i = 0; i <= n; i++) {
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
    public List<Contour> getContour() {
       return Collections.emptyList();
    }

    @Override
    public Collection<Contour> getPolygon() {
        List<Contour> contours = new LinkedList<>();
        for (int i = 0; i < vertex.size()-1; i++) {
            Iterator<Vector4> upIt = vertex.get(i).iterator();
            Iterator<Vector4> downIt = vertex.get(i+1).iterator();
            Vector4 lastUp, lastDown, curUp, curDown;
            if (upIt.hasNext()) {
                lastUp = upIt.next(); lastDown = downIt.next();
            } else break;
            while (downIt.hasNext()) {
                curUp = upIt.next();
                curDown = downIt.next();
                contours.add(new Contour(Arrays.asList(lastUp, lastDown, curDown, curUp, lastUp), c, true));
                lastDown = curDown;
                lastUp = curUp;
            }
        }
        return contours;
    }
}
