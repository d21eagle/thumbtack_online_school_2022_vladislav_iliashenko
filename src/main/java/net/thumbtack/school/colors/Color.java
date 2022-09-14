package net.thumbtack.school.colors;

public enum Color {

    RED, GREEN, BLUE;

    public static Color colorFromString(String color) throws ColorException {
        try {
            if (color == null) {
                throw new ColorException(ColorErrorCode.NULL_COLOR);
            }
            return Color.valueOf(color);
        }
        catch (IllegalArgumentException ex){
            throw new ColorException(ColorErrorCode.WRONG_COLOR_STRING);
        }
        catch (NullPointerException ex){
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        }
    }

    public static void colorEqualsNull(Color color) throws ColorException {
        if (color == null) {
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        }
    }
}
