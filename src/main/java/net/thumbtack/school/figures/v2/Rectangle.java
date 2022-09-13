package net.thumbtack.school.figures.v2;

import java.util.Objects;

public class Rectangle extends Figure {

    private Point2D leftTop;
    private Point2D rightBottom;

    public Rectangle(Point2D leftTop, Point2D rightBottom, int color) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        setColor(color);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, int color) {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), color);
    }

    public Rectangle(int length, int width, int color) {
        this(0, -width, length, 0, color);
    }

    public Rectangle(int color) {
        this(1,1, color);
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

    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx, dy);
        rightBottom.moveRel(dx, dy);
    }

    public void enlarge(int nx, int ny) {
        rightBottom.moveTo(leftTop.getX() + getLength() * nx, leftTop.getY() + getWidth() * ny);
    }

    public double getArea() {
        return getLength() * getWidth();
    }

    public double getPerimeter() {
        return (getLength() + getWidth()) * 2;
    }

    public boolean isInside(int x, int y) {
        return (x >= getTopLeft().getX()) && (x <= getBottomRight().getX()) &&
                (y <= getBottomRight().getY()) && (y >= getTopLeft().getY());
    }

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
