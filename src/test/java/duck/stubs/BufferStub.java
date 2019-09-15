package duck.stubs;

import duck.util.Buffer;

/**
 * Simplified version of Buffer for test purposes.
 * Outputs from different method calls are separated by a '#' symbol.
 */
public class BufferStub extends Buffer {
    private String outputString;

    public BufferStub() {
        outputString = "";
    }

    @Override
    public void formatLine(String output) {
        outputString += output + "#";
    }

    /**
     * Returns a string containing all output passed to BufferStub so far.
     *
     * @return  String containing all output
     */
    public String getOutputString() {
        return outputString;
    }
}
