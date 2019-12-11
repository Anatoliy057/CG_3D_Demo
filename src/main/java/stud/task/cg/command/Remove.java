package stud.task.cg.command;

import stud.task.cg.light.Light;
import stud.task.cg.model.Model;
import stud.task.cg.model.Move;
import stud.task.cg.thirdDimention.Scene;

public class Remove implements Command {

    private Scene scene;
    private String key;
    private Object o;

    public Remove(Scene scene, String key) {
        this.scene = scene;
        this.key = key;
    }

    @Override
    public void execute() {
        o = scene.remove(key);
    }

    @Override
    public void unExecute() {
        if (o instanceof Move) {
            scene.put(key, (Move) o);
        } else if (o instanceof Light) {
            scene.put(key, (Light) o);
        } else if (o instanceof Model) {
            scene.put(key, (Model) o);
        }
    }
}
