package stud.task.cg.model;

import stud.task.cg.domain.Contour;

import java.awt.*;
import java.util.Collection;

public interface Model {

    Collection<Contour> getContours();

    void setColor(Color c);

    Color getColor();

}
