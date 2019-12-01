package stud.task.cg.math;

import static stud.task.cg.math.IMatrix4.*;

public abstract class AbstractMatrix4 implements IMatrix4{

    @Override
    public double at(int i, int j) {
        if (i >= length() || i < 0 || j >= length() || j < 0) {
            throw new MatrixIndexOutOfBoundsException("Array index out of range: " + i + ":" + j);
        }
        return at(i * length() + j);
    }

    @Override
    public double[][] toArray() {
        double[][] arr = new double[length()][length()];
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                arr[i][j] = get(i, j);
            }
        }
        return arr;
    }

    @Override
    public double[] toSingleArray() {
        double[] arr = new double[lengthX2()];
        for (int i = 0; i < lengthX2(); i++) {
            arr[i] = at(i);
        }
        return arr;
    }
}
