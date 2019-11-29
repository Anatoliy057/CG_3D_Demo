package stud.task.cg.math;

import java.util.Arrays;
import java.util.function.DoubleConsumer;

public class Vector3 {

    public final static int length = 3;

    private final double[] crd;

    public Vector3(double x, double y, double z) {
        crd = new double[length];
        crd[0] = x;
        crd[1] = y;
        crd[2] = z;
    }

    public Vector3(Vector3 v3) {
        crd = new double[length];
        for (int i = 0; i < length; i++) {
            crd[i] = v3.crd[i];
        }
    }

    public Vector3(Vector4 v4) {
        crd = new double[length];
        for (int i = 0; i < length; i++) {
            crd[i] = v4.at(i);
        }
    }

    public static Vector3 empty() {
        return new Vector3(0, 0, 0);
    }

    public static Vector3 one() {
        return new Vector3(1, 1, 1);
    }

    public double getX() {
        return crd[0];
    }

    public double getY() {
        return crd[1];
    }

    public double getZ() {
        return crd[2];
    }

    public double at(int index) {
        return crd[index];
    }

    public Vector3 mul(double a) {
        return new Vector3(
                crd[0] * a,
                crd[1] * a,
                crd[2] * a
                );
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(
                crd[0] + v.crd[0],
                crd[1] + v.crd[1],
                crd[2] + v.crd[2]
        );
    }

    public double scalar(Vector3 v) {
        double scalar = 0;
        for (int i = 0; i < length; i++) {
            scalar += crd[i] * v.crd[i];
        }
        return scalar;
    }

    public Vector3 normalization() {
        double length = Math.sqrt(crd[0] * crd[0] + crd[1] * crd[1] + crd[2] * crd[2]);
        return new Vector3(crd[0]/length, crd[1]/length, crd[2]/length);
    }

    public Vector3 negative() {
        return new Vector3(-crd[0], -crd[1], -crd[2]);
    }

    public void suppliner(DoubleConsumer ds) {
        for (int i = 0; i < length; i++) {
            ds.accept(crd[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Arrays.equals(crd, vector3.crd);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(crd);
    }


    @Override
    public String toString() {
        return "Vector3{" + Arrays.toString(crd) + '}';
    }
}
