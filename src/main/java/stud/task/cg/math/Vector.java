package stud.task.cg.math;


import java.util.function.DoubleConsumer;

public interface Vector {

    double at(int i);

    double[] toArray();

    int length();

    default void suppliner(DoubleConsumer ds) {
        for (int i = 0; i < length(); i++) {
            ds.accept(at(i));
        }
    }

}
