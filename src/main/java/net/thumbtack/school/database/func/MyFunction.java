package net.thumbtack.school.database.func;

public interface MyFunction<T, K> {
    K apply(T arg);
}
