package duke.util.parser;

/**
 * Utility class for duke.util.parser package.
 */
public class ParserUtil {
    /**
     * Returns true if <i>string</i> begins with a vowel (i.e. 'a', 'e', 'i', 'o', or 'u').
     *
     * @param string    the String to check
     * @return  true if string begins with a vowel
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
     * @param string a String object
     * @return article to use for the string
     */
    static String getArticle(String string) {
        return isBeginWithVowel(string) ? "an" : "a";
    }
}
