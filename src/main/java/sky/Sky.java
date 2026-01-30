package sky;
import java.time.LocalDate;


/**
 * Entry point of the Sky task manager application.
 * <p>
 * Handles the main program loop, user input processing,
 * and coordination between UI, storage, and task list.
 */
public class Sky {
    private static Ui ui;

    /**
     * Runs the Sky application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        ui = new Ui();
        ui.showWelcome();
        
        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());

        ui.showLine();
        ui.showMessage("Hello! I'm Sky");
        ui.showMessage("What can I do for you?");
        ui.showLine();

        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();

            try {
                CommandType commandType = Parser.parseCommandType(input);

                switch (commandType) {
                    case BYE ->     {
                        ui.showBye();
                        isRunning = false;;     
                    }

                    case LIST -> printList(tasks);

                    case MARK ->  {
                        int index = Parser.parseIndex(input, "mark");
                        tasks.get(index).markAsDone();
                        storage.save(tasks);

                        printSingleTask("Nice! I've marked this task as done:", tasks.get(index));
                    }

                    case UNMARK ->  {
                        int index = Parser.parseIndex(input, "unmark");
                        tasks.get(index).markAsNotDone();
                        storage.save(tasks);

                        printSingleTask("OK, I've marked this task as not done yet:", tasks.get(index));
                    }

                    case DELETE ->  {
                        int index = Parser.parseIndex(input, "delete");
                        Task removed = tasks.remove(index);
                        storage.save(tasks);

                        ui.showLine();
                        ui.showMessage("Noted. I've removed this task:");
                        ui.showMessage("    " + removed);
                        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                        ui.showLine();
                    }

                    case TODO ->  {
                        String desc = input.substring(4).trim();
                        if (desc.isEmpty()) {
                            throw new SkyException("The description of a todo cannot be empty.");
                        }
                        tasks.add(new Todo(desc));
                        storage.save(tasks);
                        printAddMessage(tasks);
                    }

                    case DEADLINE ->  {
                        String[] parts = input.substring(8).trim().split(" /by ", 2);
                        if (parts.length < 2 || parts[0].isBlank()) {
                            throw new SkyException("A deadline must have a description and /by <time>.");
                        }
                        LocalDate by = Parser.parseDate(parts[1], "deadline /by");
                        tasks.add(new Deadline(parts[0], by));
                        storage.save(tasks);
                        printAddMessage(tasks);
                    }

                    case EVENT ->  {
                        String[] parts = input.substring(5).trim().split(" /from | /to ");
                        if (parts.length < 3 || parts[0].isBlank()) {
                            throw new SkyException("An event must have /from <start> /to <end>.");
                        }
                        LocalDate from = Parser.parseDate(parts[1], "event /from");
                        LocalDate to = Parser.parseDate(parts[2], "event /to");
                        tasks.add(new Event(parts[0], from, to));
                        storage.save(tasks);
                        printAddMessage(tasks);
                    }

                    case UNKNOWN -> throw new SkyException("I don't understand that command.");
                }

            } catch (SkyException e) {
                ui.showLine();
                ui.showMessage("Oops! " + e.getMessage());
                ui.showLine();
            } catch (IndexOutOfBoundsException e) {
                ui.showLine();
                ui.showMessage("Oops! That task number does not exist.");
                ui.showLine();
            }
        }

    }

    // ---------- helpers ----------

    /**
     * Prints all tasks in the task list.
     *
     * @param tasks Task list to display
     */
    private static void printList(TaskList tasks) {
        ui.showLine();
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage("    " + (i + 1) + "." + tasks.get(i));
        }
        ui.showLine();
    }

    /**
     * Prints the confirmation message after adding a task.
     *
     * @param tasks Task list containing the newly added task
     */
    private static void printAddMessage(TaskList tasks) {
        ui.showLine();
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("    " + tasks.get(tasks.size() - 1));
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }

    /**
     * Prints a message followed by a single task.
     *
     * @param message Message to display
     * @param task Task to display
     */
    private static void printSingleTask(String message, Task task) {
        ui.showLine();
        ui.showMessage(message);
        ui.showMessage("    " + task);
        ui.showLine();
    }

}
