package duke.stubs;

import duke.util.Ui;

/**
 * Simplified version of UI for test purposes. Instead of printing lines to
 * standard output, UiStub stores its output by appending to the end of a string.
 * Outputs from different method calls are separated by a '#' symbol.
 */
public class UiStub extends Ui {
    private String outputString;

    public UiStub() {
        outputString = "";
    }

    @Override
    public void printHorizontalLine() {
        outputString += "LINE#";
    }

    @Override
    public void formatLine(String output) {
        outputString += output + "#";
    }

    /**
     * Returns a string containing all output passed to UiStub so far.
     *
     * @return  string containing all output
     */
    public String getOutputString() {
        return outputString;
    }
}
