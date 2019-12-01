package stud.task.cg.math;

public interface IMatrix4 {

    double at(int i);
    double get(int i, int j);
    double at(int i, int j);

    IMatrix4 mul(double a);
    Vector4 mul(Vector4 v);
    IMatrix4 mul(IMatrix4 m);

    double[][] toArray();
    double[] toSingleArray();

    static int length() {
        return 4;
    }

    static int lengthX2() {
        return 16;
    }

    static Matrix4 zero() {
        return new Matrix4(new double[lengthX2()]);
    }

    static Matrix4 one() {
        Matrix4 matrix4 = zero();
        for (int i = 0; i < length(); i++) {
            matrix4.set(i * (length() + 1), 1);
        }
        return matrix4;
    }
}
