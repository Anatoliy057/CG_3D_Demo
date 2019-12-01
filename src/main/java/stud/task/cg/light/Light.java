package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector4;

import java.awt.*;

public interface Light {

    Color lightUp(Contour c, Color color);

    Vector4 getPos();

    void setPos(Vector4 vector4);

    default Color mix(Color src, Color dest, double brightness) {
        Color bck = new Color(src.getRGB());
        double r = (bck.getRed() * (1 - brightness) + dest.getRed() * brightness);
        double g = (bck.getGreen() * (1 - brightness) + dest.getGreen() * brightness);
        double b =  (bck.getBlue() * (1 - brightness) + dest.getBlue() * brightness);
        return new Color((int) r, (int) g, (int) b);
    }
}
