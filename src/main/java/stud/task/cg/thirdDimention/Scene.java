package stud.task.cg.thirdDimention;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Scene {
    public List<Model> models = new ArrayList<>();

    public BufferedImage drawScene(ScreenConverter sc, Camera c, TypeVision t) {
        BufferedImage bi = new BufferedImage(sc.getWs(), sc.getHs(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        return drawScene(bi, sc, c, t);
    }

    public BufferedImage drawScene(BufferedImage bi, ScreenConverter sc, Camera c, TypeVision t) {
        Graphics2D g = (Graphics2D) bi.getGraphics();
        /**/
        ArrayList<Contour> contours = new ArrayList<>();
        switch (t) {
            case POLYGON:
                for (Model model :
                        models) {
                    for (Contour cont :
                            model.getPolygon()) {
                        contours.add(Contour.conversion(cont, v -> Math.abs(v.getZ()) <= 1, c::w2c));
                    }
                }
                break;
            case CONTOUR:
                for (Model model :
                        models) {
                    for (Contour cont :
                            model.getContour()) {
                        contours.add(Contour.conversion(cont, v -> Math.abs(v.getZ()) <= 1, c::w2c));
                    }
                }
                break;
        }
        contours.sort(Comparator.comparingDouble(Contour::abgZ));

        for (Contour pl :
                contours) {
            switch (t) {
                case POLYGON:
                    drawPolygon(g, sc, pl);
                    break;
                case CONTOUR:
                    drawContour(g, sc, pl);
                    break;
            }
        }

        /**/
        return bi;
    }

    private void drawPolygon(Graphics g, ScreenConverter sc, Contour c) {
        int[] x = new int[c.size()];
        int[] y = new int[c.size()];
        int i = 0;
        for (Vector4 v :
                c) {
            ScreenPoint sp = sc.r2s(v.toVector3());
            x[i] = sp.getI();
            y[i] = sp.getJ();
            i++;
        }
        Polygon polygon = new Polygon(x, y, x.length);
        g.setColor(c.getColor());
        g.fillPolygon(polygon);
    }

    private void drawContour(Graphics g, ScreenConverter sc, Contour c) {
        g.setColor(c.getColor());
        if (c.isEmpty()) return;
        Iterator<Vector4> it = Contour.getCloseIterator(c);
        ScreenPoint current, last = sc.r2s(it.next().toVector3());
        while (it.hasNext()) {
            current = sc.r2s(it.next().toVector3());
            g.drawLine(last.getI(), last.getJ(), current.getI(), current.getJ());
            last = current;
        }
    }
}
