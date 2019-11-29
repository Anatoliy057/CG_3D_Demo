package stud.task.cg.node;


import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;
import stud.task.cg.model.Cube;
import stud.task.cg.model.Line;
import stud.task.cg.thirdDimention.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener {

    private ScreenConverter sc;
    private Camera camera;
    private Scene scene;

    private boolean cont = true, pol = true;

    private ScreenPoint last = null;

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-2, 2, 4, 4, 500, 500);
        camera = new Camera(new Vector4(0, 0, 0), 0, 0, Math.PI/2);
        scene = new Scene();

        scene.models.add(new Cube(new Vector4(0, 0, 0), Color.GREEN, Color.BLUE, 3));
        scene.models.add(new Line(
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
        ));
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage bi = new BufferedImage(sc.getWs(), sc.getHs(), BufferedImage.TYPE_INT_RGB);
        if (pol)
            bi = scene.drawScene(bi, sc, camera, TypeVision.POLYGON);
        if (cont)
            bi = scene.drawScene(bi, sc, camera, TypeVision.CONTOUR);
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
        Vector4 pos = camera.getPos(), newPos = pos;
        switch (e.getKeyChar()) {
            case '8': {
                newPos = new Vector4(pos.getX(), pos.getY(), pos.getZ()-1);
                break;
            }
            case '2': {
                newPos = new Vector4(pos.getX(), pos.getY(), pos.getZ()+1);
                break;
            }
        }
        camera.setPos(newPos.toVertex());
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Vector4 pos = camera.getPos(), newPos = pos;
        switch (e.getKeyCode()) {
            case 37: {
                newPos = new Vector4(pos.getX(), pos.getY()+1, pos.getZ());
                break;
            }
            case 38: {
                newPos = new Vector4(pos.getX()-1, pos.getY(), pos.getZ());
                break;
            }
            case 39: {
                newPos = new Vector4(pos.getX(), pos.getY()-1, pos.getZ());
                break;
            }
            case 40: {
                newPos = new Vector4(pos.getX()+1, pos.getY(), pos.getZ());
                break;
            }
        }
        camera.setPos(newPos.toVertex());
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
