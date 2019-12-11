package stud.task.cg.command;

import stud.task.cg.light.Light;
import stud.task.cg.model.Model;
import stud.task.cg.model.Move;
import stud.task.cg.thirdDimention.Scene;

public final class CreateUtil {

    public static <T extends Move, V extends Model> String create(Scene scene, String key, T t, V v) {
        if (v != t) throw new ClassCastException("Object t and v is not same");
        if (key == null) {
            scene.put(v);
            key = scene.getLastKey();
            scene.put(key, t);
        } else {
            scene.put(key, t);
            scene.put(key, v);
        }
        return key;
    }

    public static <T extends Model, V extends Light, E extends Move> String create(Scene scene, String key, T t, V v, E e) {
        if (v != t  | (t != e)) throw new ClassCastException("Object t and v is not same");
        if (key == null) {
            scene.put(v);
            key = scene.getLastKey();
            scene.put(key, t);
            scene.put(key, e);
        } else {
            scene.put(key, t);
            scene.put(key, v);
            scene.put(key, e);
        }
        return key;
    }

    public static <T extends Light> String create(Scene scene, String key, T t) {
        if (key == null) {
            scene.put(t);
            key = scene.getLastKey();
        } else {
            scene.put(key, t);
        }
        return key;
    }
}
