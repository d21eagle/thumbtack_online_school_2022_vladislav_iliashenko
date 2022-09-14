package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

import java.util.Objects;

public class Rectangle extends Figure {

    private Point2D leftTop;
    private Point2D rightBottom;

    public Rectangle(Point2D leftTop, Point2D rightBottom, Color color) throws ColorException {
        setTopLeft(leftTop);
        setBottomRight(rightBottom);
        setColor(color);
    }

    public Rectangle(Point2D leftTop, Point2D rightBottom, String color) throws ColorException {
        setTopLeft(leftTop);
        setBottomRight(rightBottom);
        setColor(color);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException {
        setTopLeft(new Point2D(xLeft, yTop));
        setBottomRight(new Point2D(xRight, yBottom));
        setColor(color);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, String color) throws ColorException {
        setTopLeft(new Point2D(xLeft, yTop));
        setBottomRight(new Point2D(xRight, yBottom));
        setColor(color);
    }

    public Rectangle(int length, int width, Color color) throws ColorException {
        setTopLeft(new Point2D(0, -width));
        setBottomRight(new Point2D(length, 0));
        setColor(color);
    }

    public Rectangle(int length, int width, String color) throws ColorException {
        setTopLeft(new Point2D(0, -width));
        setBottomRight(new Point2D(length, 0));
        setColor(color);
    }

    public Rectangle(Color color) throws ColorException {
        setTopLeft(new Point2D(0, -1));
        setBottomRight(new Point2D(1, 0));
        setColor(color);
    }

    public Rectangle(String color) throws ColorException {
        setTopLeft(new Point2D(0, -1));
        setBottomRight(new Point2D(1, 0));
        setColor(color);
    }

    public Point2D getTopLeft() {
        return leftTop;
    }

    public Point2D getBottomRight() {
        return rightBottom;
    }

    public void setTopLeft(Point2D topLeft) {
        this.leftTop = topLeft;
    }

    public void setBottomRight(Point2D bottomRight) {
        this.rightBottom = bottomRight;
    }

    public int getLength() {
        return getBottomRight().getX() - getTopLeft().getX();
    }

    public int getWidth() {
        return getBottomRight().getY() - getTopLeft().getY();
    }

    @Override
    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx, dy);
        rightBottom.moveRel(dx, dy);
    }

    public void enlarge(int nx, int ny) {
        rightBottom.moveTo(leftTop.getX() + getLength() * nx, leftTop.getY() + getWidth() * ny);
    }

    @Override
    public double getArea() {
        return getLength() * getWidth();
    }

    @Override
    public double getPerimeter() {
        return (getLength() + getWidth()) * 2;
    }

    @Override
    public boolean isInside(int x, int y) {
        return (x >= getTopLeft().getX()) && (x <= getBottomRight().getX()) &&
                (y <= getBottomRight().getY()) && (y >= getTopLeft().getY());
    }

    @Override
    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    public boolean isIntersects(Rectangle rectangle) {
        return !(getTopLeft().getY() > rectangle.getBottomRight().getY()
                || getBottomRight().getY() < rectangle.getTopLeft().getY()
                || getTopLeft().getX() > rectangle.getBottomRight().getX()
                || getBottomRight().getX() < rectangle.getTopLeft().getX());
    }

    public boolean isInside(Rectangle rectangle) {
        return isInside(rectangle.getTopLeft()) && isInside(rectangle.getBottomRight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(leftTop, rectangle.leftTop) &&
                Objects.equals(rightBottom, rectangle.rightBottom) && getColor() == rectangle.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, rightBottom);
    }
}
