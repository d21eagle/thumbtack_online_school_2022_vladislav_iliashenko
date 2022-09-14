package net.thumbtack.school.colors;

public enum Color {

    RED,
    GREEN,
    BLUE;

    public static Color colorFromString(String color) throws ColorException {
        if (color == null)
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        try {
            return Color.valueOf(color);
        } catch (IllegalArgumentException e) {
            throw new ColorException(ColorErrorCode.WRONG_COLOR_STRING, color.toString());
        }
    }

    public static void checkColor(Color color) throws ColorException {
        if (color == null)
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        // REVU не нужно совсем. Передан Color, не null, чего еще надо, зачем его проверять ?
        // он может быть только одной из enum констант и ничем больше
        try {
            Color.valueOf(color.toString());
        } catch (IllegalArgumentException e) {
            throw new ColorException(ColorErrorCode.WRONG_COLOR_STRING, color.toString());
        }
    }
}
