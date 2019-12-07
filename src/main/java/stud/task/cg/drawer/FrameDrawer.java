package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.light.Light;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Camera;
import stud.task.cg.thirdDimention.ScreenConverter;
import stud.task.cg.thirdDimention.ScreenPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrameDrawer implements Drawer3D {
    @Override
    public void draws(BufferedImage bi, List<Contour> c, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc, List<Light> lightList) {
       c.forEach(cont -> draw(bi, cont, camera, predicate, sc));
    }

    private void draw(BufferedImage bi, Contour c, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc) {
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        gr.setColor(c.getColor());

        Iterator<Vertex> itV = c.closeIterator();
        List<ScreenPoint> list = Stream
                .generate(itV::next)
                .limit(c.size())
                .filter(Objects::nonNull)
                .map(Vertex::getPosition)
                .map(camera::w2c)
                .filter(predicate)
                .map(Vector4::toVector3)
                .map(sc::r2s)
                .collect(Collectors.toList());
        Iterator<ScreenPoint> itSP = list.iterator();
        if (!itSP.hasNext()) return;
        ScreenPoint current, last = itSP.next();
        while (itSP.hasNext()) {
            current = itSP.next();
            gr.drawLine(last.getI(), last.getJ(), current.getI(), current.getJ());
            last = current;
        }
    }
}
