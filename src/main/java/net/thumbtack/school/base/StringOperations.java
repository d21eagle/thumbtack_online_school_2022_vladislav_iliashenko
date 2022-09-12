package net.thumbtack.school.base;

public class StringOperations {

    public static int getSummaryLength(String[] strings) {
        int length = 0;
        for (String str: strings) {
            length += str.length();
        }
        return length;
    }

    public static String getFirstAndLastLetterString(String string) {
        return String.valueOf(string.charAt(0)) +
                string.charAt(string.length() - 1);
    }

    public static boolean isSameCharAtPosition(String string1, String string2, int index) {
        return string1.charAt(index) == string2.charAt(index);
    }

    public static boolean isSameFirstCharPosition(String string1, String string2, char character) {
        return string1.indexOf(character) == string2.indexOf(character);
    }

    public static boolean isSameLastCharPosition(String string1, String string2, char character) {
        return isSameFirstCharPosition(reverse(string1), reverse(string2), character);
    }

    public static boolean isSameFirstStringPosition(String string1, String string2, String str) {
        return string1.lastIndexOf(str) == string2.lastIndexOf(str);
    }

    public static boolean isSameLastStringPosition(String string1, String string2, String str) {
        return isSameFirstStringPosition(reverse(string1), reverse(string2), str);
    }

    public static boolean isEqual(String string1, String string2) {
        return string1.equals(string2);
    }

    public static boolean isEqualIgnoreCase(String string1, String string2) {
        return string1.equalsIgnoreCase(string2);
    }

    public static boolean isLess(String string1, String string2) {
        return string1.compareTo(string2) < 0;
    }

    public static boolean isLessIgnoreCase(String string1, String string2) {
        return string1.compareToIgnoreCase(string2) < 0;
    }

    public static String concat(String string1, String string2) {
        return string1.concat(string2);
    }

    public static boolean isSamePrefix(String string1, String string2, String prefix) {
        return string1.startsWith(prefix) && string2.startsWith(prefix);
    }

    public static boolean isSameSuffix(String string1, String string2, String suffix) {
        return string1.endsWith(suffix) && string2.endsWith(suffix);
    }

    public static String getCommonPrefix(String string1, String string2) {
        int index = 0;
        int min = Math.min(string1.length(), string2.length());
        for (int i = 0; i < min; i++) {
            if (string1.charAt(i) == string2.charAt(i)) {
                index++;
            }
            else break;
        }
        return string1.substring(0, index);
    }

    public static String reverse(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        return stringBuilder.reverse().toString();
    }
    public static boolean isPalindrome(String string) {
        int strLength = string.length();
        for (int i = 0; i < strLength / 2; i++) {
            if (string.charAt(i) != string.charAt(strLength - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindromeIgnoreCase(String string) {
        return isPalindrome(string.toLowerCase());
    }

    public static String getLongestPalindromeIgnoreCase(String[] strings) {
        String str = "";
        for (String item: strings) {
            if (isPalindromeIgnoreCase(item)) {
                if (str.length() < item.length()) {
                    str = item;
                }
            }
        }
        return str;
    }

    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        length += index;
        if ((string1.length() < length) || string2.length() < length) {
            return false;
        }
        else {
            return string1.substring(index, length).equals(string2.substring(index, length));
        }
    }

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1,
                                                        String string2, char replaceInStr2, char replaceByInStr2) {
        return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
    }

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1,
                                                     String string2, String replaceInStr2, String replaceByInStr2) {
        return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
    }

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string) {
        String str = string.replaceAll("\\s+", "");
        return str.equalsIgnoreCase(reverse(str));
    }

    public static boolean isEqualAfterTrimming(String string1, String string2) {
        return string1.trim().equals(string2.trim());
    }

    public static String makeCsvStringFromInts(int[] array) {
        return makeCsvStringBuilderFromInts(array).toString();
    }

    public static String makeCsvStringFromDoubles(double[] array) {
        return makeCsvStringBuilderFromDoubles(array).toString();
    }

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        if (array.length == 0) {}
        else {
            stringBuilder.append(array[0]);
            for (int i = 1; i < array.length; i++) {
                stringBuilder.append(",").append(array[i]);
            }
        }
        return stringBuilder;
    }

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        if (array.length == 0) {}
        else {
            stringBuilder.append(String.format("%.2f", array[0]));
            for (int i = 1; i < array.length; i++) {
                stringBuilder.append(",").append(String.format("%.2f", array[i]));
            }
        }
        return stringBuilder;
    }

    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        for (int i = 0; i < positions.length; i++) {
            stringBuilder.delete(positions[i] - i, positions[i] - i + 1);
        }
        return stringBuilder;
    }

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        for (int i = 0; i < characters.length; i++) {
            stringBuilder.insert(positions[i] + i, characters[i]);
        }
        return stringBuilder;
    }
}
