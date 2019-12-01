package stud.task.cg.math;

import java.util.Arrays;
import java.util.function.DoubleFunction;

public class Vector4 implements Vector {

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

    public Vector4(double x, double y, double z, double w) {
        crd = new double[length];
        crd[0] = x;
        crd[1] = y;
        crd[2] = z;
        crd[3] = w;
    }

    Vector4(double[] crd) {
        this.crd = crd;
    }

    public static Vector4 empty() {
        return new Vector4(0, 0, 0);
    }

    public static Vector4 one() {
        return new Vector4(1, 1, 1);
    }

    public static Vector4 nonTransfer(Vector v) {
        return new Vector4(v.at(0), v.at(1), v.at(2), 0);
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

    public double getW() {
        return crd[3];
    }

    @Override
    public double at(int index) {
        return crd[index];
    }

    @Override
    public int length() {
        return length;
    }

    public Vector4 mul(double a) {
        return new Vector4(
                crd[0] * a,
                crd[1] * a,
                crd[2] * a,
                crd[3]
        );
    }

    public Vector4 add(Vector v) {
        return new Vector4(
                crd[0] + v.at(0),
                crd[1] + v.at(1),
                crd[2] + v.at(2),
                crd[3]
        );
    }

    public Vector4 negative() {
        return new Vector4(VectorUtil.covert(v -> -v, crd));
    }

    public Vector4 convert(DoubleFunction<Double> function) {
        return new Vector4(VectorUtil.covert(function, crd));
    }

    @Override
    public double[] toArray() {
        return Arrays.copyOf(crd, length);
    }

    public Vector3 toVector3() {
        return new Vector3(this);
    }

    public Vector3 toNormalVector3() {
        return new Vector3(VectorUtil.normalizeToW(this));
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
