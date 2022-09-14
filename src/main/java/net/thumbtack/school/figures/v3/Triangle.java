package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

import java.util.Objects;

public class Triangle extends Figure {

    private Point2D point1;
    private Point2D point2;
    private Point2D point3;

    // REVU В классе должен быть только один конструктор, явно присваивающий значения полям. Остальные должны вызывать другой конструктор
    public Triangle(Point2D point1, Point2D point2, Point2D point3, Color color) throws ColorException {
        setPoint1(point1);
        setPoint2(point2);
        setPoint3(point3);
        setColor(color);
    }

    public Triangle(Point2D point1, Point2D point2, Point2D point3, String color) throws ColorException {
        setPoint1(point1);
        setPoint2(point2);
        setPoint3(point3);
        setColor(color);
    }

    public Point2D getPoint1() {
        return point1;
    }

    public Point2D getPoint2() {
        return point2;
    }

    public Point2D getPoint3() {
        return point3;
    }

    public void setPoint1(Point2D point) {
        this.point1 = point;
    }

    public void setPoint2(Point2D point) {
        this.point2 = point;
    }

    public void setPoint3(Point2D point) {
        this.point3 = point;
    }

    private double getSideLength(Point2D point1, Point2D point2) {
        return Math.sqrt((point1.getX() - point2.getX()) * (point1.getX() - point2.getX())
                + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY()));
    }
    public double getSide12() {
        return getSideLength(point1, point2);
    }

    public double getSide13() {
        return getSideLength(point1, point3);
    }

    public double getSide23() {
        return getSideLength(point2, point3);
    }

    @Override
    public void moveRel(int dx, int dy) {
        point1.moveRel(dx, dy);
        point2.moveRel(dx, dy);
        point3.moveRel(dx, dy);
    }

    @Override
    public double getArea() {
        return Math.sqrt((getPerimeter() / 2) * (getPerimeter() / 2 - getSide12())
                * (getPerimeter() / 2 - getSide13()) * (getPerimeter() / 2 - getSide23()));
    }

    @Override
    public double getPerimeter() {
        return getSide12() + getSide13() + getSide23();
    }

    @Override
    public boolean isInside(int x, int y) {

        return (point1.getX() - x) * (point2.getY() - point1.getY()) - (point2.getX() - point1.getX()) * (point1.getY() - y) >= 0
                && (point2.getX() - x) * (point3.getY() - point2.getY()) - (point3.getX() - point2.getX()) * (point2.getY() - y) >= 0
                && (point3.getX() - x) * (point1.getY() - point3.getY()) - (point1.getX() - point3.getX()) * (point3.getY() - y) >= 0
                || (point1.getX() - x) * (point2.getY() - point1.getY()) - (point2.getX() - point1.getX()) * (point1.getY() - y) <= 0
                && (point2.getX() - x) * (point3.getY() - point2.getY()) - (point3.getX() - point2.getX()) * (point2.getY() - y) <= 0
                && (point3.getX() - x) * (point1.getY() - point3.getY()) - (point1.getX() - point3.getX()) * (point3.getY() - y) <= 0;

    }

    @Override
    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(point1, triangle.point1)
                && Objects.equals(point2, triangle.point2)
                && Objects.equals(point3, triangle.point3) && getColor() == triangle.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1, point2, point3);
    }
}
