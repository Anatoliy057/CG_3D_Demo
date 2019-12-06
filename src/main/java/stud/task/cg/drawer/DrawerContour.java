package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.thirdDimention.ScreenConverter;
import stud.task.cg.thirdDimention.ScreenPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface DrawerContour {

    void draw(BufferedImage bi, ScreenConverter sc, Contour c);
}
