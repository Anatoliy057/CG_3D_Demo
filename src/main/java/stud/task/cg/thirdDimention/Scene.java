package stud.task.cg.thirdDimention;

import com.sun.javafx.scene.paint.GradientUtils;
import com.sun.pisces.GradientColorMap;
import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.light.Light;
import stud.task.cg.model.Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Scene {
    public List<Model> models = new ArrayList<>();
    public List<Light> lights = new ArrayList<>();

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
        List<Contour> contours = new ArrayList<>();
        switch (t) {
            case POLYGON:
                for (Model model :
                        models) {
                    for (Contour cont :
                            model.getPolygon()) {
                        cont = calColor(cont);
                        Contour newAdd = Contour.conversionDeep(cont, v -> Math.abs(v.getZ()) <= 1, c::w2c);
                        contours.add(newAdd);
                    }
                }
                break;
            case CONTOUR:
                for (Model model :
                        models) {
                    for (Contour cont :
                            model.getContours()) {
                        contours.add(Contour.conversionDeep(cont, v -> Math.abs(v.getZ()) <= 1, c::w2c));
                    }
                }
                break;
        }
        contours.sort(Comparator.comparingDouble(cont -> -cont.getPosition().getZ()));

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
        for (Vertex v :
                c.getVertices()) {
            ScreenPoint sp = sc.r2s(v.getPosition().toVector3());
            x[i] = sp.getI();
            y[i] = sp.getJ();
            i++;
        }
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        gr.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Polygon polygon = new Polygon(x, y, x.length);
        gr.setPaint(c.getColor());
        gr.fill(polygon);

       // gr.setColor(Color.RED);
        //gr.drawPolygon(polygon);
//        ScreenPoint sp = sc.r2s(c.getPosition().toVector3());
//        gr.setColor(Color.RED);
//        gr.fillOval(sp.getI(), sp.getJ(), 10, 10);
//
//        c.getVertices().forEach( v -> {
//            ScreenPoint sp2 = sc.r2s(v.getPosition().toVector3());
//            gr.setColor(Color.BLUE);
//            gr.fillOval(sp2.getI(), sp2.getJ(), 10, 10);
//        });
    }

    private void drawContour(Graphics g, ScreenConverter sc, Contour c) {
        g.setColor(c.getColor());
        if (c.isEmpty()) return;
        Iterator<Vertex> it = c.iterator();
        ScreenPoint current, last = sc.r2s(it.next().getPosition().toVector3());
        while (it.hasNext()) {
            current = sc.r2s(it.next().getPosition().toVector3());
            g.drawLine(last.getI(), last.getJ(), current.getI(), current.getJ());
            last = current;
        }
    }

    private Contour calColor(Contour cont) {
        Contour copy = Contour.copyOf(cont);
        for (Light l :
                lights) {
            l.light(copy);
        }
        return copy;
    }
}
