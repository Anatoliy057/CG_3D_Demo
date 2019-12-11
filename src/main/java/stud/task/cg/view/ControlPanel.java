package stud.task.cg.view;

import javafx.scene.layout.HBox;
import stud.task.cg.command.Commander;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Camera;
import stud.task.cg.thirdDimention.Scene;
import stud.task.cg.view.menu.DrawerMenu;
import stud.task.cg.view.menu.ItemMenu;
import stud.task.cg.view.menu.LightMenu;
import stud.task.cg.view.menu.ModelMenu;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private Scene scene;
    private Commander commander;

    private Container last = new Container();

    public ControlPanel(Scene scene, Commander commander) {
        super();
        setLayout(new BorderLayout());
        this.scene = scene;
        this.commander = commander;

        JPanel mainMenu = new JPanel(new FlowLayout());

        JButton b = new JButton("Models");
        b.setFocusable(false);
        b.addActionListener(e -> {
            mainMenu.remove(last);
            last = new ModelMenu(scene, commander);
            mainMenu.add(last, BorderLayout.SOUTH);
            repaint();
            getParent().repaint();
        });
        mainMenu.add(b);

        b = new JButton("Lights");
        b.setFocusable(false);
        b.addActionListener(e -> {
            mainMenu.remove(last);
            last = new LightMenu(scene, commander);
            mainMenu.add(last, BorderLayout.SOUTH);
            repaint();
            getParent().repaint();
        });
        mainMenu.add(b);

        b = new JButton("Items");
        b.setFocusable(false);
        b.addActionListener(e -> {
            mainMenu.remove(last);
            last = new ItemMenu(scene, commander);
            mainMenu.add(last, BorderLayout.SOUTH);
            repaint();
            getParent().repaint();
        });
        mainMenu.add(b);

        b = new JButton("Drawers");
        b.setFocusable(false);
        b.addActionListener(e -> {
            mainMenu.remove(last);
            last = new DrawerMenu(commander, scene);
            mainMenu.add(last, BorderLayout.SOUTH);
            repaint();
            getParent().repaint();
        });
        mainMenu.add(b);

        add(mainMenu, BorderLayout.NORTH);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setPreferredSize(new Dimension(getParent().getWidth()/2, getParent().getHeight()));
    }
}
