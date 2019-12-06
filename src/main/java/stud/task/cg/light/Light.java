package stud.task.cg.light;

import stud.task.cg.domain.Contour;

import java.awt.*;

public interface Light {

    void light(Contour c);

    default Color mix(Color src, Color dest, double brightness) {
        double r = (src.getRed() * (1 - brightness) + dest.getRed() * brightness);
        double g = (src.getGreen() * (1 - brightness) + dest.getGreen() * brightness);
        double b =  (src.getBlue() * (1 - brightness) + dest.getBlue() * brightness);
        return new Color((int) r, (int) g, (int) b);
    }
}
