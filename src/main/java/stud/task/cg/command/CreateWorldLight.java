package stud.task.cg.command;

import stud.task.cg.light.WorldLight;
import stud.task.cg.thirdDimention.Scene;

import java.awt.*;

public class CreateWorldLight implements Command {

    private Scene scene;
    private String key;
    private Color c;
    private double k;

    public CreateWorldLight(Scene scene, Color c, double k) {
        this.scene = scene;
        this.c = c;
        this.k = k;
    }

    public CreateWorldLight(Scene scene, String key, Color c, double k) {
        this.scene = scene;
        this.key = key;
        this.c = c;
        this.k = k;
    }

    @Override
    public void execute() {
        WorldLight worldLight = new WorldLight(c, k);
        key = CreateUtil.create(scene, key, worldLight);
    }

    @Override
    public void unExecute() {
        scene.remove(key);
    }
}
