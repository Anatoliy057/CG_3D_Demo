package stud.task.cg.node;


import stud.task.cg.drawer.*;
import stud.task.cg.light.DiffuseLight;
import stud.task.cg.light.GouraudLight;
import stud.task.cg.light.Light;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Line;
import stud.task.cg.model.Sphere;
import stud.task.cg.thirdDimention.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener {

    private ScreenConverter sc;
    private Camera camera;
    private Scene scene;

    private ScreenPoint last = null;

    public DrawPanel() {
        super();
        camera = new Camera(new Vector4(0, 0, 0), 0, 0, Math.PI/2);
        scene = new Scene();

        //scene.put(new Cube(new Vector4(0, 0, 0), Color.WHITE, 4, true));
/*        scene.models.add(new Line(
                new Vector4(0, 0, 0),
                new Vector4(0, 0, 10),
                Color.RED
        ));
        scene.models.add(new Line(
                new Vector4(0, 0, 0),
                new Vector4(0, 10, 0),
                Color.BLUE
        ));
        scene.models.add(new Line(
                new Vector4(0, 0, 0),
                new Vector4(10, 0, 0),
                Color.YELLOW
        ));*/
        GouraudLight l = new GouraudLight(new Vector4(-0, -0, 20), Color.RED, 100, 1);
        scene.put((Light) l);
//        //scene.models.add(l);
        l = new GouraudLight(new Vector4(10, 0, 20), Color.BLUE, 100, 5);
//        this.l = l;
        scene.put((Light) l);
//        //scene.models.add(l);
        scene.put(new Sphere(Color.WHITE, 20, 20, 5, new Vector4(0, 0, 0)));
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        sc = new ScreenConverter(-2, 2, 4, 4, getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(sc.getWs(), sc.getHs(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.getGraphics();
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        scene.drawScene(new FrameDrawer(),
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
        }
        camera.addPos(newPos);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
