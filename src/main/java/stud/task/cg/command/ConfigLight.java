package stud.task.cg.command;

import stud.task.cg.light.Light;

import java.awt.*;

public class ConfigLight implements Command {

    private Light light;

    private Color color;
    private double radius;
    private double k;

    private Color oldColor;
    private double oldRadius;
    private double oldK;

    public ConfigLight(Light light, Color color, double k, double radius) {
        this.light = light;
        this.color = color;
        this.radius = radius;
        this.k = k;

        oldColor = light.getColor();
        oldRadius = light.getRadius();
        oldK = light.getK();
    }

    public ConfigLight(Light light, Color color) {
        this(light, color, -1, -1);
    }

    public ConfigLight(Light light, Color color, double k) {
        this(light, color, k, -1);
    }

    @Override
    public void execute() {
        light.setColor(color);
        light.setK(k);
        light.setRadius(radius);
    }

    @Override
    public void unExecute() {
        light.setColor(oldColor);
        light.setK(oldK);
        light.setRadius(oldRadius);
    }
}
