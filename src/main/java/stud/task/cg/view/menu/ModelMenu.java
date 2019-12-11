package stud.task.cg.view.menu;

import stud.task.cg.command.*;
import stud.task.cg.light.TypeLight;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.TypeModel;
import stud.task.cg.thirdDimention.Scene;

import javax.swing.*;
import java.awt.*;

public class ModelMenu extends JPanel {

    private Scene scene;
    private Commander commander;

    private String[] keys;
    private TypeModel type = TypeModel.NONE;

    private int index;
    private JList<String> models;
    private JColorChooser colorChooser;
    private JTextField rtf, xtf, ytf, ztf, name;

    public ModelMenu(Scene scene, Commander commander) {
        super();
        this.scene = scene;
        this.commander = commander;

        setLayout(new BorderLayout());

        JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));

        JCheckBox boxC = new JCheckBox("Cube");
        JCheckBox boxS = new JCheckBox("Sphere");

        control.add(boxC);
        control.add(boxS);

        boxC.addActionListener(l -> {
            if (boxC.isSelected()) {
                boxS.setSelected(false);
                type = TypeModel.CUBE;
            }else {
                type = TypeModel.NONE;
            }
        });

        boxS.addActionListener(l -> {
            if (boxS.isSelected()) {
                boxC.setSelected(false);
                type = TypeModel.SPHERE;
            }else {
                type = TypeModel.NONE;
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
            if (type != TypeModel.NONE) {
                String key = name.getText();
                double x, y, z, r;
                try {
                    String number = xtf.getText().equals("") ? "0" : xtf.getText();
                    x = Double.valueOf(number);
                    number = ytf.getText().equals("") ? "0" : ytf.getText();
                    y = Double.valueOf(number);
                    number = ztf.getText().equals("") ? "0" : ztf.getText();
                    z = Double.valueOf(number);
                    number = rtf.getText().equals("") ? "0" : rtf.getText();
                    r = Double.valueOf(number);
                } catch (NumberFormatException ex) {
                    System.err.println("Неверно введенные данные");
                    return;
                }
                Color color = colorChooser.getColor();
                switch (type) {
                    case CUBE:
                        commander.execute(new CreateCube(scene, color, key, r, new Vector4(x, y, z)));
                        break;
                    case SPHERE:
                        int n = (int) (r*2);
                        int m = (int) (r*2);
                        commander.execute(new CreateSphere(scene, key, new Vector4(x, y, z), color, n, m, r));
                        break;
                }
            }
            getParent().repaint();
            repaint();
        });
        create.setPreferredSize(new Dimension(150, 50));
        control.add(create);

        models = new JList<>();
        keys = scene.getModels().toArray(new String[0]);
        models.setListData(keys);
        models.addListSelectionListener(l -> {
            if (l.getValueIsAdjusting()) {
                index = ((JList<?>) l.getSource()).
                        getSelectedIndex();
            }
        });

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
        name = new JTextField("key", 6);
        info.add(name);

        add(control, BorderLayout.WEST);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(info);
        panel.add(colorChooser);
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(models), BorderLayout.CENTER);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        keys = scene.getModels().toArray(new String[0]);
        models.setListData(keys);
    }
}
