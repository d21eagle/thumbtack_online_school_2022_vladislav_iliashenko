package net.thumbtack.school.colors;

import net.thumbtack.school.colors.Color;

public interface Colored {

    void setColor(Color color) throws ColorException;

    void setColor(String colorString) throws ColorException;

    Color getColor();
}
