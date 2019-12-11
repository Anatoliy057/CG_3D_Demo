package stud.task.cg.thirdDimention;

import stud.task.cg.domain.Contour;
import stud.task.cg.drawer.Drawer3D;
import stud.task.cg.light.Light;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Model;
import stud.task.cg.model.Move;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class Scene {

    private Map<String, Model> models;
    private Map<String, Move> items;
    private Map<String, Light> lights;

    private Set<String> names;

    private int countM = 1, countL = 1, countI = 1;

    private Map<String, Drawer3D> dMap;
    private Set<String> dActive;

    private String lastKey;

    public Scene() {
        models = new HashMap<>();
        items = new HashMap<>();
        lights = new HashMap<>();
        dMap = new HashMap<>();
        dActive = new LinkedHashSet<>();

        names = new HashSet<>();
    }

    public String getLastKey() {
        return lastKey;
    }

    public boolean registerDrawer(String key, Drawer3D d) {
        return dMap.put(key, d) == null;
    }

    public void activeDrawer(String key) {
        if (dActive.contains(key))
            dActive.remove(key);
        else
            dActive.add(key);
    }

    public boolean put(Model model) {
        return put("Модель " + countM++, model);
    }

    public boolean put(String name, Model model) {
        if (names.contains(name) && models.containsKey(name)) return false;
        Model old = models.put(name, model);
        if (old == null) {
            names.add(name);
            lastKey = name;
            return true;
        } else {
            models.put(name, old);
            return false;
        }
    }

    public boolean put(Move move) {
        return put("Объект " + countI++, move);
    }

    public boolean put(String name, Move move) {
        if (names.contains(name) && items.containsKey(name)) return false;
        Move old = items.put(name, move);
        if (old == null) {
            names.add(name);
            lastKey = name;
            return true;
        } else {
            items.put(name, old);
            return false;
        }
    }

    public boolean put(Light light) {
        return put("Свет " + countL++, light);
    }

    public boolean put(String name, Light light) {
        if (names.contains(name) && lights.containsKey(name)) return false;
        Light old = lights.put(name, light);
        if (old == null) {
            names.add(name);
            lastKey = name;
            return true;
        } else {
            lights.put(name, old);
            return false;
        }
    }

    public Move getItem(String key) {
        return items.get(key);
    }

    public Light getLight(String key) {
        return lights.get(key);
    }

    public Model getModel(String key) {
        return models.get(key);
    }

    public Set<String> getItems() {
        return items.keySet();
    }

    public Set<String> getLights() {
        return lights.keySet();
    }

    public Set<String> getModels() {
        return models.keySet();
    }

    public Set<String> getDrawers() {
        return dMap.keySet();
    }

    public Set<String> getActiveDrawers() {
        return dActive;
    }

    public Optional remove(String key) {
        Object o = null, temp;
        if (names.remove(key)) {
            temp = models.remove(key);
            o = temp == null ? o : temp;
            temp = lights.remove(key);
            o = temp == null ? o : temp;
            temp = items.remove(key);
            o = temp == null ? o : temp;
        }
        return o == null ? Optional.empty() : Optional.of(o);
    }

    public void drawScene(Drawer3D.Builder builder) {
        List<Contour> contours = new LinkedList<>();
        models.values().forEach(m -> contours.addAll(m.getContours()));
        Drawer3D.DrawerParams params = builder
                .addContours(contours)
                .addLightList(new ArrayList<>(lights.values()))
                .build();
        dActive.stream().map(dMap::get).peek(d -> d.draws(params)).count();
    }

    public void drawScene(BufferedImage bi, Camera camera, Predicate<Vector4> predicate, ScreenConverter sc) {
        Drawer3D.Builder builder = new Drawer3D.Builder();
        List<Contour> contours = new LinkedList<>();
        models.values().forEach(m -> contours.addAll(m.getContours()));
        Drawer3D.DrawerParams params = builder.addBufferedImage(bi)
                .addCamera(camera)
                .addPredicate(predicate)
                .addScreeConverter(sc)
                .addLightList(new ArrayList<>(lights.values()))
                .addContours(contours)
                .build();
        dActive.stream().map(dMap::get).peek(d -> d.draws(params)).count();
    }
}
