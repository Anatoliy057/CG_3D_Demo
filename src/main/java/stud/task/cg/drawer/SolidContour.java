package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.light.Light;
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
import java.util.stream.Stream;

public class SolidContour implements Drawer3D {

    private List<TypeLight> ts;

    public SolidContour() {
        ts = new LinkedList<>();
        ts.add(TypeLight.DIFF);
        ts.add(TypeLight.WORLD);
    }

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
                .sorted(Comparator.comparingDouble(cont -> -cont.getPosition().getZ()))
                .collect(Collectors.toList());
        c.forEach(contour -> draw(bi, contour, sc));
    }

    private void draw(BufferedImage bi, Contour c, ScreenConverter sc) {
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        ScreenPoint[] sps = new ScreenPoint[c.getVertices().size()];
        Iterator<ScreenPoint> it = c.getVertices()
                .stream()
                .map(sc::r2s)
                .iterator();
        for (int i = 0; it.hasNext(); i++) {
            sps[i] = it.next();
        }
        int[]
                x = Stream.of(sps).mapToInt(ScreenPoint::getI).toArray(),
                y = Stream.of(sps).mapToInt(ScreenPoint::getJ).toArray();

        Color color = c.getColor();
        Polygon polygon = new Polygon(x, y, x.length);
        gr.setPaint(color);
        gr.fillPolygon(polygon);
    }
}
