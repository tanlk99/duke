package duck.util.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for duck.util.parser package.
 */
class ParserUtil {
    /**
     * Returns true if <i>string</i> begins with a vowel (i.e. 'a', 'e', 'i', 'o', or 'u').
     *
     * @param string    The String to check
     * @return  True if string begins with a vowel
     */
    static boolean isBeginWithVowel(String string) {
        if (string.length() == 0) {
            return false;
        }

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};

        for (char vowel : vowels) {
            if (string.charAt(0) == vowel) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the singular article ("a" or "an") to use for a string.
     * Does not handle special exceptions to the vowel rule, e.g. "hour", "universe").
     *
     * @param string A String object
     * @return Article to use for the string
     */
    static String getArticle(String string) {
        return isBeginWithVowel(string) ? "an" : "a";
    }

    /**
     * Parses a string containing a list of whitespace-separated integers. This method
     * filters duplicate elements.
     *
     * @param integerList A String containing a list of integers
     * @return An ArrayList containing parsed integers
     * @throws NumberFormatException If at least one of the integers is invalid
     */
    static ArrayList<Integer> parseUniqueIntegerList(String integerList) throws NumberFormatException {
        String[] integers = integerList.split(" ");
        Set<Integer> integerSet = new HashSet<>(); //to filter duplicates

        for (String untrimmedIndexString : integers) {
            String indexString = untrimmedIndexString.trim();
            if (indexString.length() == 0) {
                continue;
            }

            integerSet.add(Integer.parseInt(indexString));
        }

        return new ArrayList<>(integerSet);
    }
}
