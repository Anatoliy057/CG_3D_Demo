package stud.task.cg.command;

import stud.task.cg.light.GouraudLight;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Scene;

import java.awt.*;

public class CreateGouraundLight implements Command {

    private Scene scene;
    private String key;
    private Vector4 pos;
    private Color c;
    private double a;
    private double radius;
    private double k;

    public CreateGouraundLight(Scene scene, String key, Vector4 pos, Color c, double a, double radius, double k) {
        this.scene = scene;
        this.key = key;
        this.pos = pos;
        this.c = c;
        this.a = a;
        this.radius = radius;
        this.k = k;
    }

    @Override
    public void execute() {
        GouraudLight l = new GouraudLight(pos, c, a, radius, k);
        key = CreateUtil.create(scene, key, l, l, l);
    }

    @Override
    public void unExecute() {
        scene.remove(key);
    }
}
