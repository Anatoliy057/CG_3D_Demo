package stud.task.cg.math;

public final class MatrixUtil {

    public final static int AXIS_X = 0;
    public final static int AXIS_Y = 1;
    public final static int AXIS_Z = 2;

    public static Matrix4 transfer(Vector3 v) {
        return trfTo(Matrix4.one(), v);
    }

    public static Matrix4 transferTo(Matrix4 m, Vector3 v) {
        Matrix4 t = new Matrix4(m);
        return trfTo(t, v);
    }

    public static Matrix4 transferAdd(Matrix4 m, Vector3 v) {
        Matrix4 t = new Matrix4(m);
        return trfAdd(t, v);
    }

    private static Matrix4 trfTo(Matrix4 t, Vector3 v) {
        for (int i = 0; i < Vector3.length; i++) {
            t.set(i, Matrix4.length-1, v.at(i));
        }
        return t;
    }

    private static Matrix4 trfAdd(Matrix4 t, Vector3 v) {
        for (int i = 0; i < Vector3.length; i++) {
            t.set(i, Matrix4.length-1, t.get(Matrix4.length-1, i) + v.at(i));
        }
        return t;
    }

    public static Matrix4 scale(Vector3 v) {
        return sclTo(Matrix4.one(), v);
    }

    public static Matrix4 scaleTo(Matrix4 m, Vector3 v) {
        Matrix4 s = new Matrix4(m);
        return sclTo(s, v);
    }

    public static Matrix4 scaleAdd(Matrix4 m, Vector3 v) {
        Matrix4 s = new Matrix4(m);
        return sclAdd(s, v);
    }

    private static Matrix4 sclTo(Matrix4 s, Vector3 v) {
        for (int i = 0; i < Vector4.length; i++) {
            s.set(i, i, v.at(i));
        }
        return s;
    }

    private static Matrix4 sclAdd(Matrix4 s, Vector3 v) {
        for (int i = 0; i < Vector4.length; i++) {
            s.set(i, i, s.get(i, i) + v.at(i));
        }
        return s;
    }

    public static Matrix4 rotate(Vector3 v) {
        return MatrixUtil.rotate(v.getX(), 0).mul(MatrixUtil.rotate(v.getY(), 1).mul(MatrixUtil.rotate(v.getZ(), 2)));
    }

    public static Matrix4 rotate(double angle, int axis) {
        Matrix4 r = Matrix4.one();
        int a1 = (axis + 1) % 3;
        int a2 = (axis + 2) % 3;

        r.setAt(a1, a1, Math.cos(angle));
        r.setAt(a1, a2, -Math.sin(angle));
        r.setAt(a2, a1, Math.sin(angle));
        r.setAt(a2, a2, Math.cos(angle));

        return r;
    }

    public static Matrix4 projection(double fovy, double aspect, double n, double f) {
        Matrix4 pr = Matrix4.one();
        double ctgF = 1/Math.tan(fovy/2);
        pr.set(0, ctgF/aspect);
        pr.set(1, 1, ctgF);
        pr.set(2, 2, (f + n)/(f-n));
        pr.set(2, 3, -2*f*n / (f - n));
        pr.set(3, 2, 1);
        return pr;
    }
}
