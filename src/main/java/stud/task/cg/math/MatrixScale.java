package stud.task.cg.math;

import static stud.task.cg.math.IMatrix4.*;

public class MatrixScale extends AbstractMatrix4 {

    public final static int X_POS = 0;
    public final static int Y_POS = 5;
    public final static int Z_POS = 10;
    public final static int W_POS = 15;

    private Matrix4 scale;

    public MatrixScale(Vector4 v) {
        this();
        for (int i = 0; i < length(); i++) {
            scale.set(i, i, v.at(i));
        }
    }

    public MatrixScale(Vector3 v) {
        this();
        for (int i = 0; i < Vector3.length; i++) {
            scale.set(i, i, v.at(i));
        }
    }

    public MatrixScale() {
        scale = IMatrix4.one();
    }

    @Override
    public double at(int i) {
        return scale.at(i);
    }

    @Override
    public double get(int i, int j) {
        return scale.get(i, j);
    }

    @Override
    public IMatrix4 mul(double a) {
        return scale.mul(a);
    }

    @Override
    public Vector4 mul(Vector4 v) {
        return new Vector4(
                v.getX() * scale.at(X_POS),
                v.getY() * scale.at(Y_POS),
                v.getZ() * scale.at(Z_POS),
                v.getW() * scale.at(W_POS)
        );
    }

    @Override
    public IMatrix4 mul(IMatrix4 m) {
        double[][] r = m.toArray();
        for (int i = 0; i < length(); i++) {
            r[i][i] *= scale.at(i, i);
        }
        return new Matrix4(r);
    }

    public Vector4 getVector4() {
        return new Vector4(
                at(X_POS),
                at(Y_POS),
                at(Z_POS),
                at(W_POS)
        );
    }

    public Vector3 getVector3() {
        double w = at(W_POS);
        return new Vector3(
                at(X_POS) / w,
                at(Y_POS) / w,
                at(Z_POS) / w
        );
    }

    public MatrixScale add(Vector4 v) {
        v = v.add(getVector4());
        return new MatrixScale(v);
    }

    public MatrixScale add(Vector3 v) {
        v = v.add(getVector4());
        return new MatrixScale(v);
    }
}
