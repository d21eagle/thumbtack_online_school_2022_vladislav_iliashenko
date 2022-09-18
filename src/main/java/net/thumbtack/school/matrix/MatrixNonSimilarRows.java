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
        Set<int[]> matrixSet = new LinkedHashSet<>();
        for (int[] row: matrix) {
            Set<Integer> rowHashSet = arrayToHashSet(row);
            if (matrixSet.stream().noneMatch(s -> (arrayToHashSet(s).equals(rowHashSet)))) {
                matrixSet.add(row);
            }
        }
        return matrixSet;
    }

    public Set<Integer> arrayToHashSet(int[] row) {
        return Arrays.stream(row).boxed().collect(Collectors.toSet());
    }
}
