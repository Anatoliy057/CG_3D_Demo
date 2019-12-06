package stud.task.cg.math;

import static java.lang.Math.*;

public final class MatrixUtil {

    public final static int AXIS_X = 1;
    public final static int AXIS_Y = 2;
    public final static int AXIS_Z = 3;

    public static Matrix4 rotate(Vector3 v) {
        return MatrixUtil.rotate(v.getX(), AXIS_X).mul(MatrixUtil.rotate(v.getY(), AXIS_Y).mul(MatrixUtil.rotate(v.getZ(), AXIS_Z)));
    }

    public static Matrix4 rotateEuler(Vector3 v) {
        double ca = cos(v.getX());
        double cb = cos(v.getY());
        double cy = cos(v.getZ());
        double sa = sin(v.getX());
        double sb = sin(v.getY());
        double sy = sin(v.getZ());
        double[][] crd = new double[][]{
                {ca * cy - sa * cb * sy, -ca * sy - sa * cb * cy, sa * sb, 0},
                {sa * cy + ca * cb * sy, -sa * sy + ca * cb * cy, -ca * sb, 0},
                {sb*sy, sb*cy, cb, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4(crd);
    }

    public static Matrix4 rotate(double angle, int axis) {
        axis--;
        Matrix4 r = IMatrix4.one();
        int a1 = (axis + 1) % 3;
        int a2 = (axis + 2) % 3;

        r.setAt(a1, a1, cos(angle));
        r.setAt(a1, a2, -Math.sin(angle));
        r.setAt(a2, a1, Math.sin(angle));
        r.setAt(a2, a2, cos(angle));

        return r;
    }

    public static Matrix4 projection(double fovy, double aspect, double n, double f) {
        Matrix4 pr = IMatrix4.one();
        double ctgF = 1/Math.tan(fovy/2);
        pr.set(0, ctgF/aspect);
        pr.set(1, 1, ctgF);
        pr.set(2, 2, (f + n)/(f-n));
        pr.set(2, 3, -2*f*n / (f - n));
        pr.set(3, 2, 1);
        return pr;
    }
}
