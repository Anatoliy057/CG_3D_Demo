package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.light.Light;
import stud.task.cg.light.LightUtil;
import stud.task.cg.light.TypeLight;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Camera;
import stud.task.cg.thirdDimention.ScreenConverter;
import stud.task.cg.thirdDimention.ScreenPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractShadedContour implements Drawer3D {


    protected List<TypeLight> ts;

    @Override
    public void draws(BufferedImage bi, List<Contour> c, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc, List<Light> lightList) {
        List<Light> lights = lightList.stream()
                .filter(l -> ts.contains(l.getType()))
                .sorted(Comparator.comparing(Light::getType).reversed())
                .collect(Collectors.toList());
        c = c
                .stream()
                .filter(Contour::isClose)
                .peek(contour -> lights.forEach(l -> l.light(contour)))
                .map(cont -> Contour.conversionDeep(cont, predicate, camera::w2c))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Contour::isClose)
                .sorted(Comparator.comparingDouble(contour -> -contour.getPosition().getZ()))
                .collect(Collectors.toList());
        c.forEach(contour -> draw(bi, contour, sc));
    }

    private void draw(BufferedImage bi, Contour c, ScreenConverter sc) {
        Collection<Contour> triangles = toTriangle(c);
        triangles.forEach(triangle -> drawTriangle(bi, sc, triangle));
    }


    protected abstract void drawTriangle(BufferedImage bi, ScreenConverter sc, Contour triangle);

    protected int[] interpolate(int x0, int y0, int x1, int y1) {
        if (y1 - y0 == 0) return new int[0];
        int[] x = new int[y1 - y0];
        double dx = ((double) (x1 - x0)) / x.length;
        for (int i = 0; i < x.length; i++) {
            x[i] = ((int) (dx * (i))) + x0;
        }
        x[x.length-1] = x1;
        return x;
    }

    protected Color[] interpolate(Color c0, int y0, Color c1, int y1) {
        if (y1 - y0 == 0) return new Color[0];
        Color[] colors = new Color[Math.abs(y1 - y0)];
        double dt = 1.0 / colors.length;
        for (int i = 0; i < colors.length; i++) {
            double brightness = dt * i;
            colors[i] = LightUtil.mix(c0, c1, brightness);
        }
        colors[colors.length-1] = c1;
        return colors;
    }

    public static Color[] concat(Color[] arr1, Color[] arr2) {
        ArrayList<Color> arrayList = new ArrayList<>(arr1.length + arr2.length + 2);
        arrayList.addAll(0, Arrays.asList(arr1));
        arrayList.addAll(Arrays.asList(arr2));
        Color[] color = new Color[arrayList.size()];
        return arrayList.toArray(color);
    }

    public static int[] concat(int[] arr1, int[] arr2) {
        int[] arr = new int[arr1.length + arr2.length];
        for (int i = 0; i < arr1.length; i++) {
            arr[i] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) {
            arr[i + arr1.length] = arr2[i];
        }
        return arr;
    }

    private Collection<Contour> toTriangle(Contour c) {
        if (c.size() == 3) return Collections.singleton(c);

        Collection<Contour> triangles = new LinkedList<>();
        LinkedList<Vertex> vertices = new LinkedList<>(c.getVertices());
        Iterator<Vertex> it = vertices.iterator();

        Vertex
                first = it.next(),
                second,
                third = it.next();

        do {
            second = third;
            third = it.next();
            List<Vertex> vertexList = new ArrayList<>(Arrays.asList(first, second, third));
            triangles.add(new Contour(vertexList, c.getNormal(), c.getColor(), false));
        } while (it.hasNext());
        return triangles;
    }
}
