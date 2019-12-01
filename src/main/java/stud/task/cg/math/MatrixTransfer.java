package stud.task.cg.math;

import static stud.task.cg.math.IMatrix4.*;

public class MatrixTransfer extends AbstractMatrix4 {

    public final int X_POS = 3;
    public final int Y_POS = 7;
    public final int Z_POS = 11;

    private Matrix4 transfer;

    public MatrixTransfer() {
        transfer = IMatrix4.one();
    }

    public MatrixTransfer(Vector3 v) {
        this();
        for (int i = 0; i < 3; i++) {
            transfer.set(i, length()-1, v.at(i));
        }
    }

    public MatrixTransfer(Vector4 v) {
        this();
        for (int i = 0; i < 3; i++) {
            transfer.set(i, length()-1, v.at(i));
        }
    }

    @Override
    public double at(int i) {
       return transfer.at(i);
    }

    @Override
    public double get(int i, int j) {
        return transfer.get(i, j);
    }

    @Override
    public IMatrix4 mul(double a) {
        return transfer.mul(a);
    }

    @Override
    public Vector4 mul(Vector4 v) {
        if (v.getW() != 0) {
            double[] crd = v.toArray();
            crd[0] += transfer.at(X_POS);
            crd[1] += transfer.at(Y_POS);
            crd[2] += transfer.at(Z_POS);
            return new Vector4(crd);
        } else {
            return new Vector4(v);
        }
    }

    @Override
    public IMatrix4 mul(IMatrix4 m) {
        double[][] r = m.toArray();
        int l = length()-1;
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                r[i][j] += r[l][j] * at(i, l);
            }
        }
        return new Matrix4(r);
    }

    public Vector4 getVector4() {
        return new Vector4(
                transfer.at(X_POS),
                transfer.at(Y_POS),
                transfer.at(Z_POS)
                );
    }

    public Vector3 getVector3() {
        return new Vector3(
                transfer.at(X_POS),
                transfer.at(Y_POS),
                transfer.at(Z_POS)
        );
    }

    public MatrixTransfer add(Vector4 v) {
        v = v.add(getVector4());
        return new MatrixTransfer(v);
    }

    public MatrixTransfer add(Vector3 v) {
        v = v.add(getVector3());
        return new MatrixTransfer(v);
    }
}
