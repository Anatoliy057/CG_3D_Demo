package stud.task.cg.model;

import stud.task.cg.domain.Contour;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.Collection;

public interface Model {

    Collection<Contour> getContours();

    default Collection<Contour> getPolygons() {
        return getContours();
    }

    void setPosition(Vector4 v);

    Vector4 getPosition();

    void setColor(Color c);

    Color getColor();

}
