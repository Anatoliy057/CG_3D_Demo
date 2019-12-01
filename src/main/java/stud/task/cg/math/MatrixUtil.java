package stud.task.cg.math;

public final class MatrixUtil {

    public final static int AXIS_X = 1;
    public final static int AXIS_Y = 2;
    public final static int AXIS_Z = 3;

    public static Matrix4 rotate(Vector3 v) {
        return MatrixUtil.rotate(v.getX(), AXIS_X).mul(MatrixUtil.rotate(v.getY(), AXIS_Y).mul(MatrixUtil.rotate(v.getZ(), AXIS_Z)));
    }

    public static Matrix4 rotate(double angle, int axis) {
        axis--;
        Matrix4 r = IMatrix4.one();
        int a1 = (axis + 1) % 3;
        int a2 = (axis + 2) % 3;

        r.setAt(a1, a1, Math.cos(angle));
        r.setAt(a1, a2, -Math.sin(angle));
        r.setAt(a2, a1, Math.sin(angle));
        r.setAt(a2, a2, Math.cos(angle));

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
