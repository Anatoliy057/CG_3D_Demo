package stud.task.cg.thirdDimention;

import javafx.util.Pair;
import stud.task.cg.domain.Contour;
import stud.task.cg.drawer.Drawer3D;
import stud.task.cg.drawer.ShadedGuroContour;
import stud.task.cg.light.Light;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Scene {

    private Map<String, Model> models;
    private ArrayList<Model> hiddenModels;
    private Map<String, Pair<Light, Integer>> lights;

    private int countM = 1, countL = 1;

    private Drawer3D drawer = new ShadedGuroContour();

    public Scene() {
        models = new HashMap<>();
        hiddenModels = new ArrayList<>();
        lights = new HashMap<>();
    }

    public boolean put(Model model) {
        return put("Модель: " + countM++, model);
    }

    public boolean put(String name, Model model) {
        if (models.containsKey(name)) return false;
        models.put(name, model);
        return true;
    }

    public boolean put(Model model, Light light) {
        return put("Свет: " + countL++, new Pair<>(light, model));
    }

    public boolean put(Light light) {
        return put("Свет: " + countL++, light);
    }

    public boolean put(String name, Light light) {
        if (light instanceof Model)
            return put(name, new Pair<>(light, (Model) light));
        else
            return put(name, new Pair<>(light, null));
    }

    public boolean put(String name, Pair<Light, Model> p) {
        if (lights.containsKey(name)) return false;
        hiddenModels.add(p.getValue());
        lights.put(name, new Pair<>(p.getKey(), hiddenModels.size()-1));
        return true;
    }

    public boolean removeModel(String name) {
        return models.remove(name) != null;
    }

    public boolean removeLight(String name) {
        Pair<Light, Integer> p = lights.remove(name);
        if (p == null) return false;
        hiddenModels.remove((int) p.getValue());
        return true;
    }

    public Model getModelLight(String name) {
        return hiddenModels.get(lights.get(name).getValue());
    }

    public Model getModel(String name) {
        return models.get(name);
    }

    public void drawScene(Drawer3D drawer, Drawer3D.Builder builder) {
        List<Contour> contours = new LinkedList<>();
        hiddenModels.forEach(m -> contours.addAll(m.getContours()));
        models.values().forEach(m -> contours.addAll(m.getContours()));
        Drawer3D.DrawerParams params = builder
                .addContours(contours)
                .addLightList(lights.values().stream().map(Pair::getKey).collect(Collectors.toList()))
                .build();
        drawer.draws(params);
    }

    public void drawScene(Drawer3D drawer, BufferedImage bi, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc) {
        Drawer3D.Builder builder = new Drawer3D.Builder();
        List<Contour> contours = new LinkedList<>();
        hiddenModels.forEach(m -> contours.addAll(m.getContours()));
        models.values().forEach(m -> contours.addAll(m.getContours()));
        Drawer3D.DrawerParams params = builder.addBufferedImage(bi)
                .addCamera(camera)
                .addPredicate(predicate)
                .addScreeConverter(sc)
                .addLightList(lights.values().stream().map(Pair::getKey).collect(Collectors.toList()))
                .addContours(contours)
                .build();
        drawer.draws(params);
    }
}
