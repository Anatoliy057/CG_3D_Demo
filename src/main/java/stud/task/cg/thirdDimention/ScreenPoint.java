package stud.task.cg.thirdDimention;

import java.util.Objects;
import java.util.StringJoiner;

public class ScreenPoint {

    private int i, j;

    public ScreenPoint(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public ScreenPoint() {
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenPoint that = (ScreenPoint) o;
        return i == that.i &&
                j == that.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ScreenPoint.class.getSimpleName() + "[", "]")
                .add("i=" + i)
                .add("j=" + j)
                .toString();
    }
}
