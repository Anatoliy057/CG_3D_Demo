package stud.task.cg.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;

import static java.lang.Math.pow;

public final class VectorUtil {

    public static double dot(Vector4 v1, Vector4 v2) {
        return dot((Vector) v1, (Vector) v2);
    }

    public static double dot(Vector3 v1, Vector3 v2) {
        return dot((Vector) v1, (Vector) v2);
    }

    private static double dot(Vector v1, Vector v2) {
        double scal = 0;
        for (int i = 0; i < v1.length(); i++) {
            scal += v1.at(i) * v2.at(i);
        }
        return scal;
    }

    public static double module(Vector v) {
        double mX2 = 0;
        for (int i = 0; i < v.length(); i++) {
            mX2 += Math.pow(v.at(i), 2);
        }
        return Math.sqrt(mX2);
    }

    public  static double cos(Vector4 v1, Vector4 v2) {
        return cos((Vector) v1, (Vector) v2);
    }

    public  static double cos(Vector3 v1, Vector3 v2) {
        return cos((Vector) v1, (Vector) v2);
    }

    private static double cos(Vector v1, Vector v2) {
        return dot(v1, v2) / (module(v1) * module(v2));
    }

    public static Vector3 normalize(Vector3 v) {
        double length = Math.sqrt(pow(v.getX(), 2) + pow(v.getY(), 2) + pow(v.getZ(), 2));
        return new Vector3(v.getX()/length, v.getY()/length, v.getZ()/length);
    }

    public static Vector4 normalize(Vector4 v) {
        double length = Math.sqrt(pow(v.getX(), 2) + pow(v.getY(), 2) + pow(v.getZ(), 2));
        return new Vector4(v.getX()/length, v.getY()/length, v.getZ()/length, v.at(v.length()-1));
    }

    public static Vector4 normalizeTo(int i, Vector4 v) {
        return new Vector4(normalizeCrdTo(i, v));
    }

    public static Vector4 normalizeToW(Vector4 v) {
        return new Vector4(normalizeCrdTo(Vector4.length-1, v));
    }

    public static Vector3 normalizeTo(int i, Vector3 v) {
        return new Vector3(normalizeCrdTo(i, v));
    }

    private static double[] normalizeCrdTo(int i, Vector v) {
        double value = v.at(i);
        double[] crd = v.toArray();
        for (int j = 0; j < crd.length; j++) {
            crd[j] /= value;
        }
        return crd;
    }

    static double[] covert(DoubleFunction<Double> function, double[] crd) {
        double[] convertArr = Arrays.copyOf(crd, crd.length);
        for (int i = 0; i < convertArr.length; i++) {
            convertArr[i] = function.apply(convertArr[i]);
        }
        return convertArr;
    }

    public static class Builder {
        private ArrayList<Double> array;

        public Builder() {
            this(4);
        }

        public Builder(int capacity) {
            array = new ArrayList<>(capacity);
        }

        public Builder set(int i, double value) {
            array.ensureCapacity(i);
            array.set(i, value);
            return this;
        }

        public Builder addLast(double value) {
            array.add(value);
            return this;
        }

        public Builder addLastAll(double[] crd) {
            array.addAll(Arrays.asList(Arrays.stream(crd).boxed().toArray(Double[]::new)));
            return this;
        }

        public Builder negative() {
            for (int i = 0; i < array.size(); i++) {
                array.set(i, array.get(i) * -1);
            }
            return this;
        }

        public Vector3 buildV3() {
            return new Vector3(toPrimitive(3, array));
        }

        public Vector4 buildV4() {
            return new Vector4(toPrimitive(4, array));
        }

        public  Vector build() {
            return new Vector() {
                private final double[] crd;

                {
                    crd = toPrimitive(array.size(), array);
                }

                @Override
                public double at(int i) {
                    return crd[i];
                }

                @Override
                public double[] toArray() {
                    return Arrays.copyOf(crd, crd.length);
                }

                @Override
                public int length() {
                    return crd.length;
                }
            };
        }

        private static double[] toPrimitive(int size, List<Double> list) {
            double[] crd = new double[size];
            for (int i = 0; i < size; i++) {
                crd[i] = list.get(i);
            }
            return crd;
        }
    }
}
