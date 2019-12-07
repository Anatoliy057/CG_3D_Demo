package stud.task.cg.light;

import stud.task.cg.domain.Contour;
import stud.task.cg.model.Move;

public interface Light extends Move {

    void light(Contour c);

    TypeLight getType();
}
