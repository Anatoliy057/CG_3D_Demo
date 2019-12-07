package stud.task.cg.light;

import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;

import java.awt.*;

import static stud.task.cg.math.VectorUtil.*;

public final class LightUtil {

    public static double ratio(Vector3 n, Vector4 ll, double r) {
        Vector3 ln = normalize(ll.toVector3());

        double dot = dot(ln, n);
        if (dot <= 0) return 0;

        double length = module(ll);
        if (length > r) return 0;

        return dot * ((Math.pow((r - length), 2)) / (Math.pow(r, 2)));
    }

    public static Color mix(Color src, Color dest, double brightness) {
        double r = (src.getRed() * (1 - brightness) + dest.getRed() * brightness);
        double g = (src.getGreen() * (1 - brightness) + dest.getGreen() * brightness);
        double b =  (src.getBlue() * (1 - brightness) + dest.getBlue() * brightness);
        return new Color((int) r, (int) g, (int) b);
    }
}
