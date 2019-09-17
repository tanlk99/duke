package duck.util.parser;

import java.util.ArrayList;
import duck.command.ArchiveAddAllCommand;
import duck.command.ArchiveAddCommand;
import duck.command.ArchiveListCommand;
import duck.command.ArchiveRemoveAllCommand;
import duck.command.ArchiveRemoveCommand;
import duck.command.Command;
import duck.exception.DuckException;

/**
 * Parses an "archive" command.
 */
public class ArchiveCommandParser implements CommandParser<Command> {
    private static final String ARCHIVE_COMMAND_HELP = "That was an invalid archive command. Do you mean...\n\n"
            + "  archive add: transfers tasks to archive\n"
            + "  archive list: list tasks in the archive\n"
            + "  archive remove: transfers tasks from archive to task list";
    private static final String ARCHIVE_ADD_COMMAND_EMPTY_INPUT = "The list of tasks to archive cannot be empty.";
    private static final String ARCHIVE_ADD_COMMAND_HELP = "Use 'archive add' followed by a list "
            + "of whitespace-separated indexes to archive a list of tasks given by the indexes.\n\n"
            + "Alternatively, use 'archive add all' or 'archive add *' to archive all tasks in the task list.";
    private static final String ARCHIVE_REMOVE_COMMAND_EMPTY_INPUT = "The list of tasks to un-archive cannot be empty.";
    private static final String ARCHIVE_REMOVE_COMMAND_HELP = "Use 'archive remove' followed by a list "
            + "of whitespace-separated indexes to un-archive a list of tasks given by the indexes.\n\n"
            + "Alternatively, use 'archive remove all' or 'archive remove *' to un-archive all tasks in the task list.";

    private static final String[] ARCHIVE_ADD_STRINGS = {"add", "insert"};
    private static final String[] ARCHIVE_REMOVE_STRINGS = {"remove", "unarchive", "retrieve", "pull"};
    private static final String[] ARCHIVE_ALL_STRINGS = {"all", "*"};
    private static final String[] ARCHIVE_LIST_STRINGS = {"list", "view"};

    /**
     * Parses a command related to the archive (begins with 'archive').
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return An Command instance to execute the command
     * @throws DuckException If input format is invalid
     */
    public Command parseCommand(String rawInput) throws DuckException {
        if (!rawInput.contains(" ")) {
            throw new DuckException(ARCHIVE_COMMAND_HELP);
        }

        String archiveCommandString = rawInput.split(" ", 2)[1].trim(); //to remove "archive"
        String archiveCommandPhrase = archiveCommandString.split(" ", 2)[0].trim();

        for (String archiveListString : ARCHIVE_LIST_STRINGS) {
            if (archiveCommandString.equals(archiveListString)) { //check full string matches
                return new ArchiveListCommand();
            }
        }

        for (String archiveListString : ARCHIVE_ADD_STRINGS) {
            if (archiveCommandPhrase.equals(archiveListString)) {
                return parseAddCommand(archiveCommandString);
            }
        }

        for (String archiveListString : ARCHIVE_REMOVE_STRINGS) {
            if (archiveCommandPhrase.equals(archiveListString)) {
                return parseRemoveCommand(archiveCommandString);
            }
        }

        throw new DuckException(ARCHIVE_COMMAND_HELP);
    }

    /**
     * Parses a command to add task(s) to the archive.
     *
     * @param archiveCommandString Raw input passed to Duck with 'archive' trimmed
     * @return An ArchiveAddCommand instance to execute the command
     */
    private ArchiveAddCommand parseAddCommand(String archiveCommandString) throws DuckException {
        if (!archiveCommandString.contains(" ")) {
            throw new DuckException(ARCHIVE_ADD_COMMAND_EMPTY_INPUT);
        }
        String archiveCommandBody = archiveCommandString.split(" ", 2)[1].trim();

        for (String archiveAllString : ARCHIVE_ALL_STRINGS) {
            if (archiveCommandBody.equals(archiveAllString)) {
                return new ArchiveAddAllCommand();
            }
        }

        try {
            ArrayList<Integer> indexesToUnarchive = ParserUtil.parseUniqueIntegerList(archiveCommandBody);
            return new ArchiveAddCommand(indexesToUnarchive);
        } catch (NumberFormatException e) {
            throw new DuckException(ARCHIVE_ADD_COMMAND_HELP);
        }
    }

    /**
     * Parses a command to remove task(s) from the archive.
     *
     * @param archiveCommandString Raw input passed to Duck with 'archive' trimmed
     * @return An ArchiveRemoveCommand instance to execute the command
     */
    private ArchiveRemoveCommand parseRemoveCommand(String archiveCommandString) throws DuckException {
        if (!archiveCommandString.contains(" ")) {
            throw new DuckException(ARCHIVE_REMOVE_COMMAND_EMPTY_INPUT);
        }
        String archiveCommandBody = archiveCommandString.split(" ", 2)[1].trim();

        for (String archiveAllString : ARCHIVE_ALL_STRINGS) {
            if (archiveCommandBody.equals(archiveAllString)) {
                return new ArchiveRemoveAllCommand();
            }
        }

        try {
            ArrayList<Integer> indexesToUnarchive = ParserUtil.parseUniqueIntegerList(archiveCommandBody);
            return new ArchiveRemoveCommand(indexesToUnarchive);
        } catch (NumberFormatException e) {
            throw new DuckException(ARCHIVE_REMOVE_COMMAND_HELP);
        }
    }
}
