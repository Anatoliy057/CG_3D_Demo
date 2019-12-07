package stud.task.cg.model;

import stud.task.cg.domain.Contour;
import stud.task.cg.domain.Vertex;
import stud.task.cg.math.Vector4;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Cube implements Model {

    private Vector4 position;
    private Color color;

    private List<Contour> contours;

    public Cube(Vector4 position, Color color, double a, boolean close) {
        this.position = position;
        this.color = color;
        contours = createContours(a/2, close);
    }

    @Override
    public Vector4 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector4 position) {
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setContours(List<Contour> contours) {
        this.contours = contours;
    }

    @Override
    public Collection<Contour> getContours() {
        return contours;
    }

    private List<Contour> createContours(double a, boolean close) {
        List<Contour> contours = new LinkedList<>();

        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(createVertex(-a, -a, -a));
        vertexList.add(createVertex(a, -a, -a));
        vertexList.add(createVertex(a, a, -a));
        vertexList.add(createVertex(-a, a, -a));
        vertexList.add(createVertex(-a, -a, a));
        vertexList.add(createVertex(a, -a, a));
        vertexList.add(createVertex(a, a, a));
        vertexList.add(createVertex(-a, a, a));

        List<Vertex> vertices = new LinkedList<>(Arrays.asList(
                vertexList.get(0),
                vertexList.get(1),
                vertexList.get(5),
                vertexList.get(4)
        ));
        contours.add(new Contour(vertices, color, close));

        vertices = new LinkedList<>(Arrays.asList(
                vertexList.get(1),
                vertexList.get(2),
                vertexList.get(6),
                vertexList.get(5)
        ));
        contours.add(new Contour(vertices, color, close));

        vertices = new LinkedList<>(Arrays.asList(
                vertexList.get(2),
                vertexList.get(3),
                vertexList.get(7),
                vertexList.get(6)
        ));
        contours.add(new Contour(vertices, color, close));

        vertices = new LinkedList<>(Arrays.asList(
                vertexList.get(3),
                vertexList.get(0),
                vertexList.get(4),
                vertexList.get(7)
        ));
        contours.add(new Contour(vertices, color, close));

        vertices = new LinkedList<>(Arrays.asList(
                vertexList.get(0),
                vertexList.get(3),
                vertexList.get(2),
                vertexList.get(1)
        ));
        contours.add(new Contour(vertices, color, close));

        vertices = new LinkedList<>(Arrays.asList(
                vertexList.get(4),
                vertexList.get(5),
                vertexList.get(6),
                vertexList.get(7)
                ));
        contours.add(new Contour(vertices, color, close));

        Contour.setUpNormals(contours);

        return contours;
    }

    private Vertex createVertex(double x, double y, double z) {
        return new Vertex(position.add(new Vector4(x, y, z)), color);
    }
}
