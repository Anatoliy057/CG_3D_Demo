package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.light.Light;
import stud.task.cg.light.TypeLight;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Camera;
import stud.task.cg.thirdDimention.ScreenConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VertexDrawer implements Drawer3D {
    @Override
    public void draws(BufferedImage bi, List<Contour> c, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc, List<Light> lightList) {
        List<Light> list = lightList.stream().filter(l -> l.getType() == TypeLight.GURO).collect(Collectors.toList());
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        c.forEach(cont -> {
            list.forEach(l -> l.light(cont));
            cont.getVertices()
                    .stream()
                    .peek(v -> gr.setPaint(v.getColor()))
                    .map(Vertex::getPosition)
                    .map(camera::w2c)
                    .filter(predicate)
                    .map(Vector4::toVector3)
                    .map(sc::r2s)
                    .peek(sp -> gr.fillOval(sp.getI() - 10, sp.getJ() - 10, 10, 10))
                    .count();
                }
        );
    }
}
