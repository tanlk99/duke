package duck.util.parser;

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
}
