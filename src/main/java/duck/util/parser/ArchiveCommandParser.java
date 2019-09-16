package duck.util.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import duck.command.ArchiveAllCommand;
import duck.command.ArchiveCommand;
import duck.exception.DuckException;

/**
 * Parses an ArchiveCommand.
 */
public class ArchiveCommandParser implements CommandParser<ArchiveCommand> {
    private static final String ARCHIVE_COMMAND_EMPTY_INPUT = "The list of tasks to archive cannot be empty.";
    private static final String ARCHIVE_COMMAND_HELP = "Use 'archive' followed by a list "
            + "of whitespace-separated indexes to archive a list of tasks given by the indexes.\n\n"
            + "Alternatively, use 'archive all' or 'archive *' to archive all tasks in the task list.";

    private static final String[] ARCHIVE_ALL_STRINGS = {"all", "*"};

    /**
     * Parses a command to archive one or more tasks.
     * Duplicate indexes are ignored.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return An ArchiveCommand instance to execute the command
     * @throws DuckException If input format is invalid
     */
    public ArchiveCommand parseCommand(String rawInput) throws DuckException {
        if (!rawInput.contains(" ")) {
            throw new DuckException(ARCHIVE_COMMAND_EMPTY_INPUT);
        }

        String indexesString = rawInput.split(" ", 2)[1].trim(); //remove command phrase

        for (String archiveAllString : ARCHIVE_ALL_STRINGS) {
            if (indexesString.compareTo(archiveAllString) == 0) {
                return new ArchiveAllCommand();
            }
        }

        String[] indexesArray = indexesString.split(" ");
        Set<Integer> indexesToArchiveSet = new HashSet<>(); //to filter duplicates

        for (String untrimmedIndexString : indexesArray) {
            String indexString = untrimmedIndexString.trim();
            if (indexString.length() == 0) {
                continue;
            }

            try {
                indexesToArchiveSet.add(Integer.parseInt(indexString));
            } catch (NumberFormatException e) {
                throw new DuckException(ARCHIVE_COMMAND_HELP);
            }
        }

        ArrayList<Integer> indexesToArchive = new ArrayList<>(indexesToArchiveSet);
        return new ArchiveCommand(indexesToArchive);
    }
}
