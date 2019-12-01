package stud.task.cg.math;

import java.util.Arrays;
import java.util.StringJoiner;

import static stud.task.cg.math.IMatrix4.*;

public class Matrix4 extends AbstractMatrix4 {

    private final double[] mat;

    public Matrix4(double[][] mat) {
        this.mat = new double[lengthX2()];
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                this.mat[j + i*length()] = mat[i][j];
            }
        }
    }

    public Matrix4(IMatrix4 m) {
        mat = new double[16];
        for (int i = 0; i < lengthX2(); i++) {
            mat[i] = m.at(i);
        }
    }

    Matrix4(double[] arr) {
        mat = arr;
    }

    @Override
    public double at(int i) {
        return mat[i];
    }

    @Override
    public double get(int i, int j) {
        return mat[i * length() + j];
    }

    public void setAt(int i, int j, double v) {
        if (i >= length() || i < 0 || j >= length() || j < 0) {
            throw new MatrixIndexOutOfBoundsException("Array index out of range: " + i + ":" + j);
        }
        set(i * length() + j, v);
    }

    public void set(int i, int j, double v) {
        set(i * length() + j, v);
    }

    public void set(int index, double v) {
        mat[index] = v;
    }

    @Override
    public Vector4 mul(Vector4 v) {
        double[] r = new double[Vector4.length];
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < Vector4.length; j++) {
                r[i] += v.at(j) * get(i, j);
            }
        }
        return new Vector4(r);
    }

    @Override
    public Matrix4 mul(double a) {
        double[] r = new double[16];
        for (int i = 0; i < lengthX2(); i++) {
            r[i] = mat[i] * a;
        }
        return new Matrix4(r);
    }

    @Override
    public Matrix4 mul(IMatrix4 m) {
        Matrix4 r = IMatrix4.zero();
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                for (int k = 0; k < length(); k++) {
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
