package net.thumbtack.school.boxes;
import net.thumbtack.school.figures.v3.Figure;

public class ArrayBox<T extends Figure> {

    private T[] content;
    private T[] element;

    public ArrayBox(T[] content) {
        this.content = content;
    }

    public T[] getContent() {
        return content;
    }

    public void setContent(T[] content) {
        this.content = content;
    }

    public T getElement(int i) {
        return element[i];
    }

    public void setElement(T element, int i) {
        this.element[i] = element;
    }

    // REVU после имени шаблонного класса всегда <>, а потом думайте, что в них должно быть
    public <T extends Figure> boolean isSameSize(ArrayBox<T> arrayBox) {
        return content.length == arrayBox.content.length;
    }
}
