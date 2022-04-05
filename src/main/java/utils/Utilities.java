package utils;

import java.util.List;

public class Utilities {

    private static String numbersOnly = "[0-9]+";

    public static boolean max30Chars(String string) {
        return string.length() <= 30;
    }

    public static boolean max10Chars(String string) {
        return (string.length() <= 10);
    }

    public static boolean validEmail(String email) {
        if (email != null) {
            return (email.contains("@") && email.contains("."));
        }
        return false;
    }

    public static boolean onlyContainsNumbers(String text) {
        if (text != null) {
            return (text.matches(numbersOnly));
        }
        return false;
    }

    public static boolean validPPS(String PPSNumber) {
        if (PPSNumber.length() == 9) {
            if (PPSNumber.substring(0, 7).matches(numbersOnly) && PPSNumber.substring(7, 9).matches("[A-Za-z]{2}")) {
                return true;
            }
        }
        return false;
    }

    public static boolean validBoothIdentifier(String identifier) {
        if (identifier.length() == 2) {
            if (identifier.substring(0, 1).matches(numbersOnly) && identifier.substring(1, 2).matches("[A-Za-z]")) {
                return true;
            }
        }
        return false;
    }

    public static boolean validIntRange(int start, int end, int number) {
        return number >= start && number <= end;
    }

    public static boolean validIntNonNegative(int number) {
        return number >= 0;
    }

    public static boolean validDoubleNonNegative(double number) {
        return number >= 0;
    }

    public static boolean validIntPositive(int number) {
        return number > 0;
    }

    public static boolean validIndex(int index, List list) {
        return (index >= 0 && index < list.size());
    }

}