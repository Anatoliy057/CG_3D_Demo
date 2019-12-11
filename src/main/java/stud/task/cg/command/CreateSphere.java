package stud.task.cg.command;

import stud.task.cg.math.Vector4;
import stud.task.cg.model.Model;
import stud.task.cg.model.Move;
import stud.task.cg.model.Sphere;
import stud.task.cg.thirdDimention.Scene;

import java.awt.*;

public class CreateSphere implements Command {

    private Scene scene;
    private String key;
    private Vector4 center;
    private Color color;
    private int n, m;
    private double radius;

    public CreateSphere(Scene scene, String key, Vector4 center, Color color, int n, int m, double radius) {
        this.scene = scene;
        this.key = key;
        this.center = center;
        this.color = color;
        this.n = n;
        this.m = m;
        this.radius = radius;
    }

    public CreateSphere(Scene scene, Vector4 center, Color color, int n, int m, double radius) {
        this.scene = scene;
        this.center = center;
        this.color = color;
        this.n = n;
        this.m = m;
        this.radius = radius;
    }

    @Override
    public void execute() {
        Sphere sphere = new Sphere(color, n, m, radius, center);
        key = CreateUtil.create(scene, key, sphere, sphere);
    }

    @Override
    public void unExecute() {
        scene.remove(key);
    }
}
