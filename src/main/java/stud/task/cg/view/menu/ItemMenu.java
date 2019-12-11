package stud.task.cg.view.menu;

import stud.task.cg.command.*;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Scene;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ItemMenu extends JPanel {

    private Scene scene;
    private Commander commander;

    private JList<String> items;

    private boolean isAdd = false;
    private String[] keys;

    private int index;

    public ItemMenu(Scene scene, Commander commander) {
        super();
        this.scene = scene;
        this.commander = commander;
        setLayout(new BorderLayout());

        JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));

        JRadioButton swit = new JRadioButton("add");
        swit.addActionListener(e -> {
            isAdd = !isAdd;
        });
        control.add(swit);

        JButton remove = new JButton("remove");
        remove.setFocusable(false);
        remove.addActionListener(e -> {
            Command removeCom = new Remove(scene, keys[index]);
            commander.execute(removeCom);
            repaint();
        });
        remove.setPreferredSize(new Dimension(150, 50));
        control.add(remove);

        JPanel xyz = new JPanel(new FlowLayout());
        JTextField x = new JTextField(3);
        xyz.add(x);
        JTextField y = new JTextField(3);
        xyz.add(y);
        JTextField z = new JTextField(3);
        xyz.add(z);
        control.add(xyz);

        JTextArea ta = new JTextArea();
        ta.setWrapStyleWord(true);
        ta.setEditable(false);

        JButton setPos = new JButton("set position");
        setPos.setFocusable(false);
        setPos.addActionListener(e -> {
            Command command;
            if (isAdd) {
                command = new MoveItem(scene.getItem(keys[index]),
                        new Vector4(
                                Integer.valueOf(x.getText()),
                                Integer.valueOf(y.getText()),
                                Integer.valueOf(z.getText())
                        )
                );
            } else {
                command = new SetPosItem(scene.getItem(keys[index]),
                        new Vector4(
                                Integer.valueOf(x.getText()),
                                Integer.valueOf(y.getText()),
                                Integer.valueOf(z.getText())
                        )
                );
            }
            commander.execute(command);
            getParent().repaint();
            ta.setText("");
            ta.append(scene.getItem(keys[index]).getPosition().toString());
        });
        control.add(setPos);

        add(control, BorderLayout.CENTER);

        add(ta, BorderLayout.SOUTH);

        keys = scene.getItems().toArray(new String[0]);
        items = new JList<>(keys);
        items.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                ta.setText("");
                index = ((JList<?>) e.getSource()).
                        getSelectedIndex();
                ta.append(scene.getItem(keys[index]).getPosition().toString());
            }
        });
        add(new JScrollPane(items), BorderLayout.WEST);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
