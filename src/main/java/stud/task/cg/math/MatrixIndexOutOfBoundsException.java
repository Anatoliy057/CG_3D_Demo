package stud.task.cg.math;

public class MatrixIndexOutOfBoundsException extends IndexOutOfBoundsException {

    public MatrixIndexOutOfBoundsException() {
        super();
    }


    public MatrixIndexOutOfBoundsException(int indexI, int indexJ) {
        super("Array index out of range: " + indexI + " : " + indexJ);
    }


    public MatrixIndexOutOfBoundsException(String s) {
        super(s);
    }
}
