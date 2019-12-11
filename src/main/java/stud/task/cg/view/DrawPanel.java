package stud.task.cg.view;


import stud.task.cg.command.Commander;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.thirdDimention.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener {

    private ScreenConverter sc;
    private Camera camera;
    private Scene scene;
    private Commander commander;

    private Set<Integer> codes = new HashSet<>();

    private ScreenPoint last = null;

    public DrawPanel(Scene scene, Commander commander) {
        super();
        camera = new Camera(new Vector4(0, 0, 0), 0, 0, Math.PI / 2);
        this.scene = scene;
        this.commander = commander;

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setFocusable(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setFocusable(true);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setPreferredSize(new Dimension(getParent().getWidth() / 2, getParent().getHeight()));
        sc = new ScreenConverter(-2, 2, 4, 4, getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(sc.getWs(), sc.getHs(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.getGraphics();
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        scene.drawScene(
                bi,
                camera,
                v -> Math.abs(v.getZ()) <= 1 && Math.abs(v.getX()) <= 2 && Math.abs(v.getY()) <= 2,
                sc
        );

        bi.getGraphics().dispose();
        g.drawImage(bi, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        last = new ScreenPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        last = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        requestFocusInWindow();
        ScreenPoint np = new ScreenPoint(e.getX(), e.getY());
        if (last != null) {
            int dx = np.getI() - last.getI();
            int dy = np.getJ() - last.getJ();
            double da = dx * Math.PI / 180;
            double db = dy * Math.PI / 180;
            Vector3 a = camera.getAngle();
            camera.setAngle(new Vector3(
                    a.getX() + db,
                    a.getY(),
                    a.getZ() + da
            ));
            repaint();
        }
        last = np;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        Vector4 newPos = Vector4.empty();
        switch (e.getKeyChar()) {
            case '8': {
                newPos = new Vector4(0, 0, 1);
                break;
            }
            case '2': {
                newPos = new Vector4(0, 0, -1);
                break;
            }
        }
        camera.addPos(newPos);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Vector4 newPos = Vector4.empty();
        codes.add(e.getKeyCode());
        switch (e.getKeyCode()) {
            case 37: {
                newPos = new Vector4(0, 1, 0);
                break;
            }
            case 38: {
                newPos = new Vector4(-1, 0, 0);
                break;
            }
            case 39: {
                newPos = new Vector4(0, -1, 0);
                break;
            }
            case 40: {
                newPos = new Vector4(1, 0, 0);
                break;
            }
            case 90 : {
                if (codes.contains(17)) {
                    commander.prev();
                }
                break;
            }
            case 88: {
                if (codes.contains(17)) {
                    commander.future();
                }
                break;
            }
        }
        camera.addPos(newPos);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        codes.remove(e.getKeyCode());
    }
}
