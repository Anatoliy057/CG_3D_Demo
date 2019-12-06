package stud.task.cg.thirdDimention;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.drawer.DrawerContour;
import stud.task.cg.drawer.ShadedTriangle;
import stud.task.cg.light.Light;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Scene {
    public List<Model> models = new ArrayList<>();
    public List<Light> lights = new ArrayList<>();

    private DrawerContour drawer = new ShadedTriangle();

    public BufferedImage drawScene(ScreenConverter sc, Camera c, TypeVision t) {
        BufferedImage bi = new BufferedImage(sc.getWs(), sc.getHs(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        return drawScene(bi, sc, c, t);
    }

    public BufferedImage drawScene(BufferedImage bi, ScreenConverter sc, Camera c, TypeVision t) {
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        /**/
        List<Contour> contours = new ArrayList<>();
        switch (t) {
            case POLYGON:
                for (Model model :
                        models) {
                    for (Contour cont :
                            model.getPolygons()) {
                        cont = calColor(cont);
                        Contour.conversionDeep(cont,
                                v -> Math.abs(v.getZ()) <= 1 &&
                                Math.abs(v.getX()) <= 2 &&
                                Math.abs(v.getY()) <= 2,
                                c::w2c)
                                .ifPresent(contours::add);
                    }
                }
                break;
            case CONTOUR:
                for (Model model :
                        models) {
                    for (Contour cont :
                            model.getContours()) {
                        cont = calColor(cont);
                        cont.getVertices().forEach(v -> {
                            ScreenPoint sp = sc.r2s(c.w2c(v.getPosition()).toVector3());
                            gr.setColor(v.getColor());
                            gr.fillOval(sp.getI(), sp.getJ(), 15, 15);
                            ScreenPoint sp1 = sc.r2s(c.w2c(v.getPosition().add(new Vector4(v.getNormal()))).toVector3());
                            gr.setColor(Color.RED);
                            gr.drawLine(sp.getI(), sp.getJ(), sp1.getI(), sp1.getJ());
                        });
                       Contour.conversionDeep(cont, v -> Math.abs(v.getZ()) <= 1, c::w2c)
                                .ifPresent(contours::add);
                    }
                }
                break;
        }
        contours.sort(Comparator.comparingDouble(cont -> -cont.getPosition().getZ()));

        for (Contour pl :
                contours) {
            switch (t) {
                case POLYGON:
                    drawPolygon(bi, sc, pl);
                    break;
                case CONTOUR:
                    //drawContour(gr, sc, pl);
                    break;
            }
        }

        /**/
        return bi;
    }

    private void drawPolygon(BufferedImage bi, ScreenConverter sc, Contour c) {
        drawer.draw(bi, sc, c);

       // gr.setColor(Color.RED);
        //gr.drawPolygon(polygon);
    }

    private void drawContour(Graphics g, ScreenConverter sc, Contour c) {
        g.setColor(c.getColor());
        if (c.isEmpty()) return;
        Iterator<Vertex> it = c.closeIterator();
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
