package net.thumbtack.school.figures.v2;

import java.util.Objects;

public class Circle extends Figure {
    private Point2D center;
    private int radius;

    public Circle(Point2D center, int radius, int color) {
        this.center = center;
        this.radius = radius;
        setColor(color);
    }

    public Circle(int xCenter, int yCenter, int radius, int color) {
        this(new Point2D(xCenter, yCenter), radius, color);
    }

    public Circle(int radius, int color) {
        this(0, 0, radius, color);
    }

    public Circle(int color) {
        this(1, color);
    }

    public Point2D getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }

    public void enlarge(int n) {
        radius *= n;
    }

    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public boolean isInside(int x, int y) {
        return (center.getX() - x) * (center.getX() - x) +
                (center.getY() - y) * (center.getY() - y) <= radius * radius;
    }

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return radius == circle.radius && Objects.equals(center, circle.center) && getColor() == circle.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}
