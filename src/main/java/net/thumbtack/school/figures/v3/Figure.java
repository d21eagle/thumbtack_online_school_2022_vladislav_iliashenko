package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.Colored;
import net.thumbtack.school.colors.ColorErrorCode;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.area.HasArea;


import java.util.Objects;

import static net.thumbtack.school.colors.Color.checkColor;
abstract public class Figure implements Colored, HasArea {

    private Color color;

    public abstract void moveRel(int dx, int dy);

    // REVU не нужно
    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract boolean isInside(int x, int y);

    // REVU этот метод не должен быть абстрактным. Его здесь и можно описать - раз и навсегда
    // и удалить у всех наследников
    public abstract boolean isInside(Point2D point);

    @Override
    public void setColor(Color color) throws ColorException {
        checkColor(color);
        this.color = color;
    }

    @Override
    public void setColor(String color) throws ColorException {
        // REVU 2 раза вызывать Color.colorFromString незачем
        // да и вообще checkColor тут не нужен - colorFromString не может вернуть null
        checkColor(Color.colorFromString(color));
        this.color = Color.colorFromString(color);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return color == figure.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
