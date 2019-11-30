package stud.task.cg.thirdDimention;

import stud.task.cg.math.Matrix4;
import stud.task.cg.math.MatrixUtil;
import stud.task.cg.math.Vector3;
import stud.task.cg.math.Vector4;

public class Camera {

    private Matrix4 translate, rotate, scale, projection;
    private Vector4 pos;
    private Vector3 a;

    private double
            fovy = Math.PI / 4,
            aspect = 1,
            n = 0.1,
            f = 200;

    public Camera() {
        translate = Matrix4.one();
        rotate = Matrix4.one();
        scale = Matrix4.one();
        projection = MatrixUtil.projection(fovy, aspect, n, f);
        pos = Vector4.empty();
        a = Vector3.empty();
    }

    public Camera(Vector4 v4) {
        rotate = Matrix4.one();
        scale = Matrix4.one();
        projection = MatrixUtil.projection(fovy, aspect, n, f);
        a = Vector3.empty();

        pos = new Vector4(v4);
        translate = MatrixUtil.transfer(v4.toVector3());
    }

    public Camera(Vector4 v4, double fovy, double aspect, double n, double f) {
        pos = new Vector4(v4);
        translate = MatrixUtil.transfer(v4.toVector3());

        projection = MatrixUtil.projection(f, aspect, n, f);
        a = Vector3.empty();

        this.fovy = fovy;
        this.aspect = aspect;
        this.n = n;
        this.f = f;
    }

    public Camera(Vector4 v4, double ax, double ay, double az) {
        pos = new Vector4(v4);
        translate = MatrixUtil.transfer(v4.toVector3());
        a = new Vector3(ax, ay, az);
        rotate = MatrixUtil.rotate(new Vector3(ax, ay, az));
        projection = MatrixUtil.projection(fovy, aspect, n, f);

        scale = Matrix4.one();
    }

    public Camera(Vector4 v4, Vector3 a, double fovy, double aspect, double n, double f) {
        pos = new Vector4(v4);
        translate = MatrixUtil.transfer(v4.toVector3());
        rotate = MatrixUtil.rotate(a);
        projection = MatrixUtil.projection(fovy, aspect, n, f);

        scale = Matrix4.one();
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

    public void setPos(Vector4 pos) {
        this.pos = new Vector4(pos);
        updateTranslate();
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
        return new Vector4(
                projection.mul(
                        translate.mul(
                                rotate.mul(
                                        scale.mul(new Vector4(v))
                                )
                        )
                )
        ).normalize();
    }

    private void updateProjection() {
        projection = MatrixUtil.projection(f, aspect, n, f);
    }

    private void updateTranslate() {
        translate = MatrixUtil.transfer(pos.toVector3());
    }

    private void updateRotate() {
        rotate = MatrixUtil.rotate(a);
    }
}
