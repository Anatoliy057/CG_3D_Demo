package stud.task.cg;

import stud.task.cg.command.Commander;
import stud.task.cg.command.CreateCube;
import stud.task.cg.drawer.*;
import stud.task.cg.math.Vector4;
import stud.task.cg.view.ControlPanel;
import stud.task.cg.view.DrawPanel;
import stud.task.cg.thirdDimention.Scene;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Scene scene = new Scene();
        Commander commander = new Commander();
        scene.registerDrawer("SolidContour", new SolidContour());
        scene.registerDrawer("ShadedGuroContour", new ShadedGuroContour());
        scene.registerDrawer("FrameDrawer", new FrameDrawer());
        scene.registerDrawer("NormalDrawer", new NormalDrawer());
        scene.registerDrawer("VertexDrawer", new VertexDrawer());
        DrawPanel d = new DrawPanel(scene, commander);
        frame.add(d, BorderLayout.WEST);
        ControlPanel cp = new ControlPanel(scene, commander);
        frame.add(cp, BorderLayout.EAST);
        frame.setFocusable(true);

        frame.addMouseListener(d);
        frame.addMouseMotionListener(d);
        frame.addKeyListener(d);
        commander.execute(new CreateCube(scene, Color.BLUE, "cube", 3, new Vector4(0, 0, 0)));
        frame.setVisible(true);
    }
}
