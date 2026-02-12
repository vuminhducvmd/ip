package sky;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


/**
 * Parses user input into commands, indices, and dates.
 */
public class Parser {

    /**
     * Determines the command type from user input.
     *
     * @param input User input string
     * @return Corresponding command type
     */
    public static CommandType parseCommandType(String input) {
        assert input != null : "Parser.parseCommandType: input should not be null";

        String trimmed = input.trim();

        // Commands with exact match
        if (trimmed.equals("list")) {
            return CommandType.LIST;
        }
        if (trimmed.equals("bye")) {
            return CommandType.BYE;
        }

        // Commands with prefixes
        if (trimmed.startsWith("todo")) {
            return CommandType.TODO;
        }
        if (trimmed.startsWith("deadline")) {
            return CommandType.DEADLINE;
        }
        if (trimmed.startsWith("event")) {
            return CommandType.EVENT;
        }
        if (trimmed.startsWith("unmark")) {
            return CommandType.UNMARK;
        }
        if (trimmed.startsWith("mark")) {
            return CommandType.MARK;
        }
        if (trimmed.startsWith("delete")) {
            return CommandType.DELETE;
        }
        if (trimmed.startsWith("find")) {
            return CommandType.FIND;
        }
        if (trimmed.startsWith("update")) {
            return CommandType.UPDATE;
        }

        return CommandType.UNKNOWN;
    }

    /**
     * Parses a 0-based task index from user input.
     *
     * @param input Full user input
     * @param command Command keyword
     * @return Parsed task index (0-based)
     * @throws SkyException If the index is invalid
     */
    public static int parseIndex(String input, String command) throws SkyException {
        assert input != null : "Parser.parseIndex: input should not be null";
        assert command != null : "Parser.parseIndex: command should not be null";
        assert input.startsWith(command)
                : "Parser.parseIndex: input must start with command";

        String argument = input.substring(command.length()).trim();

        if (argument.isEmpty()) {
            throw new SkyException("Please provide a task number.");
        }

        int index;
        try {
            index = Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e) {
            throw new SkyException("Please provide a valid task number.");
        }

        if (index < 0) {
            throw new SkyException("Task number must be positive.");
        }

        return index;
    }

    /**
     * Parses a date string in yyyy-MM-dd format.
     *
     * @param value Date string to parse
     * @param fieldName Name of the date field (for error messages)
     * @return Parsed LocalDate
     * @throws SkyException If the date format is invalid
     */
    public static LocalDate parseDate(String value, String fieldName) throws SkyException {
        assert value != null : "Parser.parseDate: value should not be null";
        assert fieldName != null : "Parser.parseDate: fieldName should not be null";

        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {
            throw new SkyException(
                "Invalid " + fieldName
                + " date. Use yyyy-MM-dd (e.g., 2019-10-15)."
            );
        }
    }
}
