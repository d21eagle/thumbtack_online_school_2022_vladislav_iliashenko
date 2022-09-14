package net.thumbtack.school.boxes;

import net.thumbtack.school.figures.v3.Rectangle;

public class NamedBox<T extends Rectangle> extends Box<Rectangle> {

    private String name;

    public NamedBox(T content, String name) {
        super(content);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
