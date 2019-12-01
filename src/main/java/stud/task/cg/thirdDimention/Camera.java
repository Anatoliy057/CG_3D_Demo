package stud.task.cg.thirdDimention;

import stud.task.cg.math.*;

public class Camera {

    private MatrixScale scale;
    private MatrixTransfer translate;
    private Matrix4 rotate;
    private Matrix4 projection;
    private Vector4 pos;
    private Vector3 a;

    private double
            fovy = Math.PI / 4,
            aspect = 1,
            n = 0.1,
            f = 200;

    public Camera() {
        translate = new MatrixTransfer();
        rotate = IMatrix4.one();
        scale = new MatrixScale();
        projection = MatrixUtil.projection(fovy, aspect, n, f);
        pos = Vector4.empty();
        a = Vector3.empty();
    }

    public Camera(Vector4 v4) {
        rotate = IMatrix4.one();
        scale = new MatrixScale();
        projection = MatrixUtil.projection(fovy, aspect, n, f);
        a = Vector3.empty();

        pos = new Vector4(v4);
        translate = new MatrixTransfer(v4);
    }

    public Camera(Vector4 v4, double fovy, double aspect, double n, double f) {
        pos = new Vector4(v4);
        translate = new MatrixTransfer(v4);

        projection = MatrixUtil.projection(f, aspect, n, f);
        a = Vector3.empty();

        this.fovy = fovy;
        this.aspect = aspect;
        this.n = n;
        this.f = f;
    }

    public Camera(Vector4 v4, double ax, double ay, double az) {
        pos = new Vector4(v4);
        translate = new MatrixTransfer(v4.toVector3());
        a = new Vector3(ax, ay, az);
        rotate = MatrixUtil.rotate(new Vector3(ax, ay, az));
        projection = MatrixUtil.projection(fovy, aspect, n, f);

        scale = new MatrixScale();
    }

    public Camera(Vector4 v4, Vector3 a, double fovy, double aspect, double n, double f) {
        pos = new Vector4(v4);
        translate = new MatrixTransfer(v4);
        rotate = MatrixUtil.rotate(a);
        projection = MatrixUtil.projection(fovy, aspect, n, f);

        scale = new MatrixScale();
        this.a = new Vector3(a);

        this.fovy = fovy;
        this.aspect = aspect;
        this.n = n;
        this.f = f;
    }

    public Vector3 getAngle() {
        return a;
    }

    public void setAngle(Vector3 a) {
        this.a = new Vector3(a);
        updateRotate();
    }

    public Vector4 getPos() {
        return pos;
    }

    public void addPos(Vector4 pos) {
        System.out.println("------------");
        System.out.println(translate.getVector3());
        System.out.println(pos);
        translate = translate.add(pos);
        System.out.println(translate.getVector3());
    }

    public void setPos(Vector4 pos) {
        translate = new MatrixTransfer(pos);
    }

    public double getFovy() {
        return fovy;
    }

    public double getAspect() {
        return aspect;
    }

    public double getN() {
        return n;
    }

    public double getF() {
        return f;
    }

    public void setFovy(double fovy) {
        this.fovy = fovy;
        updateProjection();
    }

    public void setAspect(double aspect) {
        this.aspect = aspect;
        updateProjection();
    }

    public void setN(double n) {
        this.n = n;
        updateProjection();
    }

    public void setF(double f) {
        this.f = f;
        updateProjection();
    }

    public Vector4 w2c(Vector4 v) {
        return VectorUtil.normalizeToW(new Vector4(
                projection.mul(
                        rotate.mul(
                                translate.mul(
                                        scale.mul(new Vector4(v))
                                )
                        )
                )
        ));
    }

    private void updateProjection() {
        projection = MatrixUtil.projection(f, aspect, n, f);
    }

    private void updateRotate() {
        rotate = MatrixUtil.rotate(a);
    }
}
