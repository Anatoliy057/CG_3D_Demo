package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.thirdDimention.ScreenConverter;
import stud.task.cg.thirdDimention.ScreenPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.stream.Stream;

public class SolidContour implements DrawerContour {

    int index = 0;

    @Override
    public void draw(BufferedImage bi, ScreenConverter sc, Contour c) {
        if (!c.isClose()) return;
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        ScreenPoint[] sps = new ScreenPoint[c.getVertices().size()];
        Iterator<ScreenPoint> it = c.getVertices().stream().map(sc::r2s).iterator();
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
