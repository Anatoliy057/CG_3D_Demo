package stud.task.cg.drawer;

import javafx.util.Pair;
import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.ScreenConverter;
import stud.task.cg.thirdDimention.ScreenPoint;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class ShadedTriangle extends ShadedContour {

    @Override
    public void drawTriangle(BufferedImage bi, ScreenConverter sc, Contour triangle) {
        List<Vertex> vertexList = triangle.getVertices();

        Iterator<Pair<Vertex, ScreenPoint>> itPoints = vertexList.stream()
                .map(v -> new Pair<>(v, sc.r2s(v)))
                .sorted(Comparator.comparingInt(p -> p.getValue().getJ()))
                .iterator();

        LinkedList<Pair<Vertex, ScreenPoint>> points = new LinkedList<>();
        itPoints.forEachRemaining(points::add);

        ScreenPoint minY = points.get(0).getValue();

        int[]
                xLeft,
                xRight;
        Color[]
                hLeft,
                hRight;

        Vertex firstV, secondV, thirdV;
        ScreenPoint firstSP, secondSP, thirdSP;
        Iterator<Pair<Vertex, ScreenPoint>> it = points.iterator();

        Pair<Vertex, ScreenPoint> tempPair = it.next();
        firstV = tempPair.getKey();
        firstSP = tempPair.getValue();
        tempPair = it.next();
        secondV = tempPair.getKey();
        secondSP = tempPair.getValue();
        tempPair = it.next();
        thirdV = tempPair.getKey();
        thirdSP = tempPair.getValue();

        int[] x01 = interpolate(firstSP.getI(), firstSP.getJ(), secondSP.getI(), secondSP.getJ());
        int[] x02 = interpolate(secondSP.getI(), secondSP.getJ(), thirdSP.getI(), thirdSP.getJ());
        int[] x2 = interpolate(firstSP.getI(), firstSP.getJ(), thirdSP.getI(), thirdSP.getJ());
        Color[] h01 = interpolate(firstV.getColor(), firstSP.getJ(), secondV.getColor(), secondSP.getJ());
        Color[] h02 = interpolate(secondV.getColor(), secondSP.getJ(), thirdV.getColor(), thirdSP.getJ());
        Color[] h2 = interpolate(firstV.getColor(), firstSP.getJ(), thirdV.getColor(), thirdSP.getJ());

        int[] x1;
        Color[] h1;

        if (x01.length == 0) {
            x1 = x02;
            h1 = h02;
        } else if (x02.length == 0) {
            x1 = x01;
            h1 = h01;
        } else {
            x1 = concat(x01, x02);
            h1 = concat(h01, h02);
        }

        int m = x1.length / 2;
        if (m >= x1.length) {
            return;
        }
        if (x2[m] < x1[m]) {
            xLeft = x2;
            xRight = x1;

            hLeft = h2;
            hRight = h1;
        } else {
            xLeft = x1;
            xRight = x2;

            hLeft = h1;
            hRight = h2;
        }

        for (int i = 0; i < xRight.length; i++) {
            int y = i + minY.getJ();
            int xl = xLeft[i];
            int xr = xRight[i];

            Color[] hSegment = interpolate(hLeft[i], xl, hRight[i], xr);
            for (int x = xl; x < xr; x++) {
                Color color = hSegment[x - xl];
                bi.setRGB(x, y, color.getRGB());
            }
        }
        Graphics2D gr = (Graphics2D) bi.getGraphics();
//        points.forEach(p -> {
//            ScreenPoint sp = p.getValue();
//            gr.setColor(Color.ORANGE);
//            gr.fillOval(sp.getI(), sp.getJ(), 10, 10);
//            ScreenPoint sp1 = sc.r2s(p.getKey().getPosition().add(new Vector4(p.getKey().getNormal())).toVector3());
//            gr.setColor(Color.RED);
//            gr.drawLine(sp.getI(), sp.getJ(), sp1.getI(), sp1.getJ());
//        });
    }
}
