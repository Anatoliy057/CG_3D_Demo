package stud.task.cg.command;

import stud.task.cg.thirdDimention.Scene;

public class ActiveDrawer implements Command {

    private String key;
    private Scene scene;

    public ActiveDrawer(String key, Scene scene) {
        this.key = key;
        this.scene = scene;
    }

    @Override
    public void execute() {
        scene.activeDrawer(key);
    }

    @Override
    public void unExecute() {
        scene.activeDrawer(key);
    }
}
