package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Rectangle3D extends Rectangle {

    private int height;

    public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height) {
        super(leftTop, rightBottom);
        this.height = height;
    }

    public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height) {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), height);
    }

    public Rectangle3D(int length, int width, int height) {
        this(0, -width, length, 0, height);
    }

    public Rectangle3D() {
        this(1, 1, 1);
    }

    public Point2D getTopLeft() {
        return super.getTopLeft();
    }

    public Point2D getBottomRight() {
        return super.getBottomRight();
    }

    public int getHeight() {
        return height;
    }

    public void setTopLeft(Point2D topLeft) {
        super.setTopLeft(topLeft);
    }

    public void setBottomRight(Point2D bottomRight) {
        super.setBottomRight(bottomRight);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return super.getLength();
    }

    public int getWidth() {
        return super.getWidth();
    }

    public void moveRel(int dx, int dy) {
        super.moveRel(dx, dy);
    }

    public void enlarge(int nx, int ny) {
        super.enlarge(nx, ny);
    }

    public double getArea() {
        return super.getArea();
    }

    public double getPerimeter() {
        return super.getPerimeter();
    }

    public double getVolume() {
        return super.getArea() * height;
    }

    public boolean isInside(int x, int y) {
        return super.isInside(x, y);
    }

    public boolean isInside(Point2D point) {
        return super.isInside(point);
    }

    public boolean isInside(int x, int y, int z) {
        return (super.isInside(x,y) && ((z >= 0) && (z <= height)));
    }

    public boolean isInside(Point3D point) {
        return isInside(point.getX(), point.getY(), point.getZ());
    }

    public boolean isIntersects(Rectangle3D rectangle) {
        return super.isIntersects(rectangle);
    }

    public boolean isInside(Rectangle3D rectangle) {
        return super.isInside(rectangle)
                && (getHeight() <= rectangle.getHeight() && getArea() <= rectangle.getArea()
                || getHeight() >= rectangle.getHeight() && getArea() >= rectangle.getArea());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle3D that = (Rectangle3D) o;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}
