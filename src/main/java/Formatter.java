public class Formatter {
    public static void printHorizontalLine() {
        formatLine("________________________________________" +
            "________________________________________\n");
    }

    public static void formatLine(String output) {
        System.out.println("    " + output);
    }
}
