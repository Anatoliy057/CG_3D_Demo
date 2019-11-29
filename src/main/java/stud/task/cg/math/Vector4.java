package stud.task.cg.math;

import java.util.Arrays;
import java.util.Vector;
import java.util.function.DoubleConsumer;

public class Vector4 {

    public final static int length = 4;

    private final double[] crd;

    public Vector4(double x, double y, double z) {
        this(x, y, z, 1);
    }

    public Vector4(Vector3 v3) {
        this(v3.getX(), v3.getY(), v3.getZ());
    }

    public Vector4(Vector4 v4) {
        crd = new double[length];
        for (int i = 0; i < length; i++) {
            crd[i] = v4.crd[i];
        }
    }

    Vector4(double x, double y, double z, double w) {
        crd = new double[length];
        crd[0] = x;
        crd[1] = y;
        crd[2] = z;
        crd[3] = w;
    }

    public static Vector4 empty() {
        return new Vector4(0, 0, 0);
    }

    public static Vector4 one() {
        return new Vector4(1, 1, 1);
    }

    public static Vector4 nonTransfer(double x, double y, double z) {
        return new Vector4(x, y, z, 0);
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

    public Vector4 mul(double a) {
        return new Vector4(
                crd[0] * a,
                crd[1] * a,
                crd[2] * a,
                crd[3]
        );
    }

    public Vector4 add(Vector3 v) {
        return new Vector4(
                crd[0] + v.getX(),
                crd[1] + v.getY(),
                crd[2] + v.getZ()
        );
    }

    public Vector4 add(Vector4 v) {
        return new Vector4(
                crd[0] + v.getX(),
                crd[1] + v.getY(),
                crd[2] + v.getZ()
        );
    }

    public double scalar(Vector4 v) {
        double scalar = 0;
        for (int i = 0; i < length; i++) {
            scalar += crd[i] * v.crd[i];
        }
        return scalar;
    }

    public void suppliner(DoubleConsumer ds) {
        for (int i = 0; i < length; i++) {
            ds.accept(crd[i]);
        }
    }

    public Vector4 toVertex() {
        if (Math.abs(crd[3]) < 1e-10)
            return new Vector4(
                    crd[0],
                    crd[1],
                    crd[2]
            );
        else
            return new Vector4(
                    crd[0] / crd[3],
                    crd[1] / crd[3],
                    crd[2] / crd[3]
            );
    }

    public Vector4 normalize() {
        return toVertex();
    }

    public Vector3 toVector3() {
        if (Math.abs(crd[3]) < 1e-10) {
            return new Vector3(
                    crd[0],
                    crd[1],
                    crd[2]
            );
        }
        return new Vector3(
                crd[0] / crd[3],
                crd[1] / crd[3],
                crd[2] / crd[3]
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4 vector4 = (Vector4) o;
        return Arrays.equals(crd, vector4.crd);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(crd);
    }


    @Override
    public String toString() {
        return "Vector4{" + Arrays.toString(crd) + '}';
    }
}
