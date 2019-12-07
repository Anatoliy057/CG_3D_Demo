package stud.task.cg.drawer;

import stud.task.cg.domain.Contour;
import stud.task.cg.light.Light;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Camera;
import stud.task.cg.thirdDimention.ScreenConverter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface Drawer3D {

    void draws(BufferedImage bi, List<Contour> c, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc, List<Light> lightList);

    default void draws(DrawerParams params) {
        draws(params.bi, params.c, params.camera, params.predicate, params.sc, params.lightList);
    }

    class DrawerParams {
        private BufferedImage bi;
        private List<Contour> c;
        private Camera camera;
        private Predicate<Vector4> predicate;
        private ScreenConverter sc;
        private List<Light> lightList;
    }

    class Builder {
        private DrawerParams params;

        public Builder() {
            params = new DrawerParams();
        }

        public Builder addBufferedImage(BufferedImage bi) {
            params.bi = bi;
            return this;
        }

        public Builder addContours(List<Contour> contours) {
            params.c = new ArrayList<>();
            contours.forEach(c -> params.c.add(Contour.copyOf(c)));
            return this;
        }

        public Builder addCamera(Camera camera) {
            params.camera = camera;
            return this;
        }

        public Builder addPredicate(Predicate<Vector4> predicate) {
            params.predicate = predicate;
            return this;
        }

        public Builder addScreeConverter(ScreenConverter sc) {
            params.sc = sc;
            return this;
        }

        public Builder addLightList(List<Light> lights) {
            params.lightList = lights;
            return this;
        }

        public DrawerParams build() {
            return params;
        }
    }
}
