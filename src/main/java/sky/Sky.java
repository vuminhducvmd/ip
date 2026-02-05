package sky;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Core logic engine for the Sky task manager.
 * <p>
 * This class is UI-agnostic and can be used by both
 * CLI and GUI frontends.
 */
public class Sky {

    private final TaskList tasks;
    private final Storage storage;

    /**
     * Constructs a Sky instance using the given storage file.
     *
     * @param filePath Path to the storage file
     */
    public Sky(String filePath) {
        this.storage = new Storage();
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Processes a user command and returns Sky's response.
     *
     * @param input User command
     * @return Response message to be displayed
     * @throws SkyException If command is invalid
     */
    public String getResponse(String input) throws SkyException {
        CommandType commandType = Parser.parseCommandType(input);

        return switch (commandType) {
            case BYE -> "Bye. Hope to see you again soon!";
            case LIST -> formatList();
            case FIND -> handleFind(input);
            case MARK -> handleMark(input);
            case UNMARK -> handleUnmark(input);
            case DELETE -> handleDelete(input);
            case TODO -> handleTodo(input);
            case DEADLINE -> handleDeadline(input);
            case EVENT -> handleEvent(input);
            case UNKNOWN -> throw new SkyException("I don't understand that command.");
        };
    }

    // ---------- command handlers ----------

    private String formatList() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String handleFind(String input) throws SkyException {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new SkyException("Please provide a keyword to search for.");
        }
        ArrayList<Task> matches = tasks.find(keyword);

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String handleMark(String input) throws SkyException {
        int index = Parser.parseIndex(input, "mark");
        tasks.get(index).markAsDone();
        storage.save(tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }

    private String handleUnmark(String input) throws SkyException {
        int index = Parser.parseIndex(input, "unmark");
        tasks.get(index).markAsNotDone();
        storage.save(tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    private String handleDelete(String input) throws SkyException {
        int index = Parser.parseIndex(input, "delete");
        Task removed = tasks.remove(index);
        storage.save(tasks);
        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleTodo(String input) throws SkyException {
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            throw new SkyException("The description of a todo cannot be empty.");
        }
        tasks.add(new Todo(desc));
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1);
    }

    private String handleDeadline(String input) throws SkyException {
        String[] parts = input.substring(8).trim().split(" /by ", 2);
        if (parts.length < 2) {
            throw new SkyException("A deadline must have /by <time>.");
        }
        LocalDate by = Parser.parseDate(parts[1], "deadline /by");
        tasks.add(new Deadline(parts[0], by));
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1);
    }

    private String handleEvent(String input) throws SkyException {
        String[] parts = input.substring(5).trim().split(" /from | /to ");
        if (parts.length < 3) {
            throw new SkyException("An event must have /from <start> /to <end>.");
        }
        LocalDate from = Parser.parseDate(parts[1], "event /from");
        LocalDate to = Parser.parseDate(parts[2], "event /to");
        tasks.add(new Event(parts[0], from, to));
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1);
    }
}
