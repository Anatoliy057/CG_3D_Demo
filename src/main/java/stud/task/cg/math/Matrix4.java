package stud.task.cg.math;

import java.util.Arrays;
import java.util.StringJoiner;

public class Matrix4 {

    public final static int length = 4;

    final static int lengthX2 = 16;

    private final double[] mat;

    public Matrix4(double[][] mat) {
        this.mat = new double[lengthX2];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                this.mat[j + i*length] = mat[i][j];
            }
        }
    }

    public Matrix4(Matrix4 m) {
        mat = new double[16];
        for (int i = 0; i < lengthX2; i++) {
            mat[i] = m.get(i);
        }
    }

    private Matrix4(double[] arr) {
        mat = arr;
    }

    public static Matrix4 zero() {
        return new Matrix4(new double[lengthX2]);
    }

    public static Matrix4 one() {
        Matrix4 matrix4 = zero();
        for (int i = 0; i < 4; i++) {
            matrix4.set(i, i, 1);
        }
        return matrix4;
    }

    public double getAt(int i, int j) {
        if (i >= length || i < 0 || j >= length || j < 0) {
            throw new MatrixIndexOutOfBoundsException("Array index out of range: " + i + ":" + j);
        }
        return get(i, j);
    }

    public void setAt(int i, int j, double v) {
        if (i >= length || i < 0 || j >= length || j < 0) {
            throw new MatrixIndexOutOfBoundsException("Array index out of range: " + i + ":" + j);
        }
        set(i, j, v);
    }

    double get(int i, int j) {
        return mat[i * length + j];
    }

    void set(int i, int j, double v) {
        mat[i * length + j] = v;
    }

    double get(int index) {
        return mat[index];
    }

    void set(int index, double v) {
        mat[index] = v;
    }

    public Vector4 mul(Vector4 v) {
        double[] r = new double[Vector4.length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < Vector4.length; j++) {
                r[i] += v.at(j) * get(i, j);
            }
        }
        return new Vector4(r[0], r[1], r[2], r[3]);
    }

    public Matrix4 mul(double a) {
        double[] r = new double[16];
        for (int i = 0; i < lengthX2; i++) {
            r[i] = mat[i] * a;
        }
        return new Matrix4(r);
    }

    public Matrix4 mul(Matrix4 m) {
        Matrix4 r = zero();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    r.set(i, j, r.get(i, j) + get(i, k) * m.get(k, j));
                }
            }
        }
        return r;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Matrix4.class.getSimpleName() + "[", "]")
                .add("mat=" + Arrays.toString(mat))
                .toString();
    }
}
