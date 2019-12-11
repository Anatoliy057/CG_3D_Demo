package stud.task.cg.command;

import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.thirdDimention.Scene;

import java.awt.*;

public class CreateCube implements Command {

    private Scene scene;
    private Color color;
    private String key;
    private double a;
    private Vector4 pos;

    public CreateCube(Scene scene, Color color, String key, double a, Vector4 pos) {
        this.scene = scene;
        this.color = color;
        this.key = key;
        this.a = a;
        this.pos = pos;
    }

    public CreateCube(Scene scene, Color color, double a, Vector4 pos) {
        this.scene = scene;
        this.color = color;
        this.a = a;
        this.pos = pos;
    }

    @Override
    public void execute() {
        Cube cube = new Cube(pos, color, a, true);
        key = CreateUtil.create(scene, key, cube, cube);
    }

    @Override
    public void unExecute() {
        scene.remove(key);
    }
}
