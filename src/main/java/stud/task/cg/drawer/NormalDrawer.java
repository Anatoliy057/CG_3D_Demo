package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.light.Light;
import stud.task.cg.light.TypeLight;
import stud.task.cg.math.Vector4;
import stud.task.cg.math.VectorUtil;
import stud.task.cg.thirdDimention.Camera;
import stud.task.cg.thirdDimention.ScreenConverter;
import stud.task.cg.thirdDimention.ScreenPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NormalDrawer implements Drawer3D {

    TypeLight t = TypeLight.GURO;

    @Override
    public void draws(BufferedImage bi, List<Contour> c, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc, List<Light> lightList) {
        List<Light> list = lightList.stream().filter(l -> l.getType() == t).collect(Collectors.toList());
        c.forEach(cont -> {
            list.forEach(l -> l.light(cont));
            if (cont.isClose())
                cont.getVertices().forEach(v0 -> {
                    Graphics g = bi.getGraphics();
                            ScreenPoint sp0 = sc.r2s(camera.w2c(v0.getPosition()).toVector3());
                            //ScreenPoint spl;
//                    for (Light l :
//                            list) {
//                        spl = sc.r2s(camera.w2c(
//                                v0.getPosition().add(l.getPosition().add(v0.getPosition().negative())).mul(7)).toVector3());
//                        g.setColor(Color.RED);
//                        g.drawLine(sp0.getI(), sp0.getJ(), spl.getI(), spl.getJ());
//                    }
                            ScreenPoint sp1 = sc.r2s(camera.w2c(v0.getPosition().add(v0.getNormal().mul(2))).toVector3());
                            g.setColor(cont.getColor());
                            g.drawLine(sp0.getI(), sp0.getJ(), sp1.getI(), sp1.getJ());
                        }
                );
                }
        );
    }
}
