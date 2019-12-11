package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.model.Move;

import java.awt.*;

public interface Light {

    void light(Contour c);

    TypeLight getType();

    void setColor(Color color);

    Color getColor();

    double getRadius();

    void setRadius(double radius);

    double getK();

    void setK(double k);
}
