package net.thumbtack.school.introduction;
import java.util.stream.*;

public class FirstSteps {

    public int sum(int x, int y) {
        return x + y;
    }

    public int mul(int x, int y) {
        return x * y;
    }

    public int div(int x, int y) {
        return x / y;
    }

    public int mod(int x, int y) {
        return x % y;
    }

    public boolean isEqual(int x, int y) {
        return x == y;
    }

    public boolean isGreater(int x, int y) {
        return x > y;
    }

    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y) {
        return (xLeft <= x) && (x <= xRight) && (yTop <= y) && (y <= yBottom);
    }

    public int sum(int[] array) {
        int sum = 0;
        if (array.length == 0) {
            return 0;
        }
        for (int item: array) {
            sum += item;
        }
        return sum;
    }

    public int mul(int[] array) {
        int mul = 1;
        if (array.length == 0) {
            return 0;
        }
        for (int item: array) {
            mul *= item;
        }
        return mul;
    }

    public int min(int[] array) {
        if(array.length == 0) {
            return Integer.MAX_VALUE;
        }
        int min = array[0];
        for (int item: array) {
            if (item < min) {
                min = item;
            }
        }
        return min;
    }

    public int max(int[] array) {
        if(array.length == 0) {
            return Integer.MIN_VALUE;
        }
        int max = array[0];
        for (int item: array) {
            if (item > max) {
                max = item;
            }
        }
        return max;
    }

    public double average(int[] array) {
        double sum = 0;
        if (array.length == 0) {
            return 0;
        }
        sum = IntStream.of(array).sum();
        return sum / array.length;
    }

    public boolean isSortedDescendant(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] <= array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void cube(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= array[i] * array[i];
        }
    }

    public boolean find(int[] array, int value) {
        for (int item: array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }

    public void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int rev = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = rev;
        }
    }

    public boolean isPalindrome(int[] array) {
        if (array.length == 0) {
            return true;
        }
        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    public int sum(int[][] matrix) {
        int sum = 0;
        for (int[] arrs: matrix) {
            sum += IntStream.of(arrs).sum();
        }
        return sum;
    }

    public int max(int[][] matrix) {
        if (matrix[0].length == 0) return Integer.MIN_VALUE;
        int max = matrix[0][0];
        for (int[] arrs: matrix) {
            for (int nums: arrs) {
                if (nums > max) {
                    max = nums;
                }
            }
        }
        return max;
    }

    public int diagonalMax(int[][] matrix) {
        if (matrix[0].length == 0) return Integer.MIN_VALUE;
        int max = matrix[0][0];
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][i] > max) {
                max = matrix[i][i];
            }
        }
        return max;
    }

    public boolean isSortedDescendant(int[][] matrix) {
        for (int[] item: matrix) {
            if (!isSortedDescendant(item)) {
                return false;
            }
        }
        return true;
    }
}
