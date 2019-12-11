package stud.task.cg.view.menu;

import stud.task.cg.command.ActiveDrawer;
import stud.task.cg.command.Commander;
import stud.task.cg.thirdDimention.Scene;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class DrawerMenu extends JPanel {

    private Commander commander;
    private Scene scene;

    private String[] keys;

    private JTextArea ta;

    public DrawerMenu(Commander commander, Scene scene) {
        super();
        this.commander = commander;
        this.scene = scene;
        setLayout(new BorderLayout());

        ta = new JTextArea();
        ta.setEditable(false);
        ta.setWrapStyleWord(true);
        ta.setPreferredSize(new Dimension(300, 400));
        add(ta, BorderLayout.SOUTH);

        keys = scene.getDrawers().toArray(new String[0]);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < keys.length; i++) {
            JButton b = new JButton(keys[i]);
            final int k = i;
            b.addActionListener(e -> {
                commander.execute(new ActiveDrawer(keys[k], scene));
                repaint();
            });
            jPanel.add(b);
        }

        add(jPanel, BorderLayout.CENTER);

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ta.setText("");
        Set<String> actives = scene.getActiveDrawers();
        for (String d :
                actives) {
            ta.append(d + '\n');
        }
    }
}
