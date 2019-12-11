package stud.task.cg.view.menu;

import stud.task.cg.command.*;
import stud.task.cg.light.TypeLight;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.Scene;

import javax.swing.*;
import java.awt.*;

public class LightMenu extends JPanel {

    private Scene scene;
    private Commander commander;

    private String[] keys;
    private TypeLight type = TypeLight.NONE;

    private int index;
    private JList<String> lights;
    private JTextArea ta;
    private JColorChooser colorChooser;
    private JTextField rtf, xtf, ytf, ztf, ktf, atf, name;

    public LightMenu(Scene scene, Commander commander) {
        super();
        this.scene = scene;
        this.commander = commander;

        setLayout(new BorderLayout());

        JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));

        JCheckBox boxW = new JCheckBox("World");
        JCheckBox boxD = new JCheckBox("Diffuse");
        JCheckBox boxG = new JCheckBox("Guro");

        control.add(boxW);
        control.add(boxD);
        control.add(boxG);

        boxW.addActionListener(l -> {
            if (boxW.isSelected()) {
                boxD.setSelected(false);
                boxG.setSelected(false);
                type = TypeLight.WORLD;
            }else {
                type = TypeLight.NONE;
            }
        });

        boxD.addActionListener(l -> {
            if (boxD.isSelected()) {
                boxW.setSelected(false);
                boxG.setSelected(false);
                type = TypeLight.DIFF;
            }else {
                type = TypeLight.NONE;
            }
        });

        boxG.addActionListener(l -> {
            if (boxG.isSelected()) {
                boxW.setSelected(false);
                boxD.setSelected(false);
                type = TypeLight.GURO;
            } else {
                type = TypeLight.NONE;
            }
        });

        JButton remove = new JButton("remove");
        remove.setFocusable(false);
        remove.addActionListener(e -> {
            Command removeCom = new Remove(scene, keys[index]);
            commander.execute(removeCom);
            repaint();
        });
        remove.setPreferredSize(new Dimension(150, 50));
        control.add(remove);

        JButton create = new JButton("create");
        create.setFocusable(false);
        create.addActionListener(e -> {
            if (type != TypeLight.NONE) {
                String key = name.getText();
                double x, y, z, a, r, k;
                try {
                    String number = xtf.getText().equals("") ? "0" : xtf.getText();
                    x = Double.valueOf(number);
                    number = ytf.getText().equals("") ? "0" : ytf.getText();
                    y = Double.valueOf(number);
                    number = ztf.getText().equals("") ? "0" : ztf.getText();
                    z = Double.valueOf(number);
                    number = rtf.getText().equals("") ? "0" : rtf.getText();
                    r = Double.valueOf(number);
                    number = ktf.getText().equals("") ? "0" : ktf.getText();
                    k = Double.valueOf(number);
                    number = atf.getText().equals("") ? "0" : atf.getText();
                    a = Double.valueOf(number);
                } catch (NumberFormatException ex) {
                    System.err.println("Неверно введенные данные");
                    return;
                }
                Color color = colorChooser.getColor();
                switch (type) {
                    case DIFF:
                        commander.execute(new CreateDiffuseLight(scene, key, new Vector4(x, y, z), color, a, r, k));
                        break;
                    case GURO:
                        commander.execute(new CreateGouraundLight(scene, key, new Vector4(x, y, z), color, a, r, k));
                        break;
                    case WORLD:
                        commander.execute(new CreateWorldLight(scene, key, color, k));
                        break;
                }
            }
            getParent().repaint();
            repaint();
        });
        create.setPreferredSize(new Dimension(150, 50));
        control.add(create);

        lights = new JList<>();
        keys = scene.getLights().toArray(new String[0]);
        lights.setListData(keys);
        lights.addListSelectionListener(l -> {
            if (l.getValueIsAdjusting() & index != -1) {
                index = ((JList<?>) l.getSource()).
                        getSelectedIndex();
                ta.setText("");
                ta.append(scene.getLight(keys[index]).toString());
            }
        });

        ta = new JTextArea();
        ta.setEditable(false);

        JPanel info = new JPanel(new FlowLayout());
        colorChooser = new JColorChooser();
        xtf = new JTextField("x", 3);
        info.add(xtf);
        ytf = new JTextField("y", 3);
        info.add(ytf);
        ztf = new JTextField("z", 3);
        info.add(ztf);
        rtf = new JTextField("r", 3);
        info.add(rtf);
        ktf = new JTextField("k", 3);
        info.add(ktf);
        atf = new JTextField("a", 3);
        info.add(atf);
        name = new JTextField("key", 6);
        info.add(name);

        add(control, BorderLayout.WEST);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(info);
        panel.add(colorChooser);
        panel.add(ta);
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(lights), BorderLayout.CENTER);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        keys = scene.getLights().toArray(new String[0]);
        lights.setListData(keys);
    }
}
