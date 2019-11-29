package stud.task.cg;

import stud.task.cg.node.DrawPanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DrawPanel d = new DrawPanel();
        frame.add(d);
        frame.addKeyListener(d);
        frame.setVisible(true);
    }
}
