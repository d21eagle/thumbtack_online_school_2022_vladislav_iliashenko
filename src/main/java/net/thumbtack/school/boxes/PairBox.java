package net.thumbtack.school.boxes;

import net.thumbtack.school.figures.v3.Figure;
import net.thumbtack.school.area.HasArea;

public class PairBox<T extends Figure, W extends Figure> implements HasArea {

    private T contentFirst;
    private W contentSecond;

    public PairBox(T contentFirst, W contentSecond) {
        this.contentFirst = contentFirst;
        this.contentSecond = contentSecond;
    }

    public T getContentFirst() {
        return contentFirst;
    }

    public void setContentFirst(T contentFirst) {
        this.contentFirst = contentFirst;
    }

    public W getContentSecond() {
        return contentSecond;
    }

    public void setContentSecond(W contentSecond) {
        this.contentSecond = contentSecond;
    }

    @Override
    public double getArea() {
        return contentFirst.getArea() + contentSecond.getArea();
    }

    public boolean isAreaEqual(PairBox pairBox) {
        return pairBox.getArea() == getArea();
    }

    public static boolean isAreaEqual(PairBox pairBox1, PairBox pairBox2) {
        return pairBox1.getArea() == pairBox2.getArea();
    }
}
