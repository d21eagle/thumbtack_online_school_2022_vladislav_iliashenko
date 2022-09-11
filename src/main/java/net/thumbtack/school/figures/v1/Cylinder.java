package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Cylinder extends Circle {

    private int height;

    public Cylinder(Point2D center, int radius, int height) {
        super(center, radius);
        this.height = height;
    }

    public Cylinder(int xCenter, int yCenter, int radius, int height) {
        this(new Point2D(xCenter, yCenter), radius, height);
    }

    public Cylinder(int radius, int height) {
        this(new Point2D(0, 0), radius, height);
    }

    public Cylinder() {
        this(new Point2D(), 1, 1);
    }

    public Point2D getCenter() {
        return super.getCenter();
    }

    public int getRadius() {
        return super.getRadius();
    }

    public void setCenter(Point2D center) {
        super.setCenter(center);
    }

    public void setRadius(int radius) {
        super.setRadius(radius);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void moveRel(int dx, int dy) {
        super.moveRel(dx, dy);
    }

    public void enlarge(int n) {
        super.enlarge(n);
    }

    public double getArea() {
        return super.getArea();
    }

    public double getPerimeter() {
        return super.getPerimeter();
    }

    public double getVolume() {
        return getArea() * height;
    }

    public boolean isInside(int x, int y, int z) {
        return super.isInside(x, y) && (getHeight() >= z) && (z >= 0);
    }

    public boolean isInside(Point3D point) {
        return isInside(point.getX(),point.getY(),point.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return height == cylinder.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}
