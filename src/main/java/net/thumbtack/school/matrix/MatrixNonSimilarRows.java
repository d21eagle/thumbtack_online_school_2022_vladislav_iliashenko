package net.thumbtack.school.matrix;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixNonSimilarRows {

    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    public Set<int[]> getNonSimilarRows() {
        // REVU Вы делаете работу, которую за Вас мог бы сделать класс
        // проверить, есть ли такой элемент
        // Вместо Set<int[]> - Set<Set<Integer>>
        // а еще лучше Map<<Set<Integer>, int[]>
        Map<Set<Integer>, int[]> setMap = new HashMap<>();

        for (int[] array: matrix) {
            Set<Integer> integers = new HashSet<>();
            for (int element: array) {
                integers.add(element);
            }
            setMap.putIfAbsent(integers, array);
        }

        return new HashSet<>(setMap.values());
    }


}
