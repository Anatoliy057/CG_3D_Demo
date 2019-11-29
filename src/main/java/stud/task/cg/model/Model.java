package stud.task.cg.model;

import stud.task.cg.domain.Contour;

import java.util.Collection;

public interface Model {

    Collection<Contour> getContour();

    default Collection<Contour> getPolygon() {
        return getContour();
    }

}
