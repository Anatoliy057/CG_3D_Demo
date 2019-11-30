package stud.task.cg.thirdDimention;

import stud.task.cg.domain.Contour;

import java.awt.*;
import java.util.List;


public interface Light {

    Color lightUp(List<Contour> contourList, Contour c, Color color);

    default Color mix(Color src, Color dest, double brightness) {
        Color bck = new Color(src.getRGB());
        double r = (bck.getRed() * (1 - brightness) + dest.getRed() * brightness);
        double g = (bck.getGreen() * (1 - brightness) + dest.getGreen() * brightness);
        double b =  (bck.getBlue() * (1 - brightness) + dest.getBlue() * brightness);
        return new Color((int) r, (int) g, (int) b);
    }
}
