import java.util.ArrayList;
import java.util.Scanner;

public class Sky {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("____________________________________________");
        System.out.println("Hello! I'm Sky");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________");

        boolean isRunning = true;

        while (isRunning) {
            String input = scanner.nextLine();

            try {
                CommandType commandType = parseCommandType(input);

                switch (commandType) {
                    case BYE ->     {
                        System.out.println("____________________________________________");
                        System.out.println("Bye. Hope to see you again soon!");
                        System.out.println("____________________________________________");
                        isRunning = false;;     
                    }

                    case LIST -> printList(tasks);

                    case MARK ->  {
                        int index = parseIndex(input, "mark");
                        tasks.get(index).markAsDone();
                        printSingleTask("Nice! I've marked this task as done:", tasks.get(index));
                    }

                    case UNMARK ->  {
                        int index = parseIndex(input, "unmark");
                        tasks.get(index).markAsNotDone();
                        printSingleTask("OK, I've marked this task as not done yet:", tasks.get(index));
                    }

                    case DELETE ->  {
                        int index = parseIndex(input, "delete");
                        Task removed = tasks.remove(index);
                        System.out.println("____________________________________________");
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("    " + removed);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________");
                    }

                    case TODO ->  {
                        String desc = input.substring(4).trim();
                        if (desc.isEmpty()) {
                            throw new SkyException("The description of a todo cannot be empty.");
                        }
                        tasks.add(new Todo(desc));
                        printAddMessage(tasks);
                    }

                    case DEADLINE ->  {
                        String[] parts = input.substring(8).trim().split(" /by ", 2);
                        if (parts.length < 2 || parts[0].isBlank()) {
                            throw new SkyException("A deadline must have a description and /by <time>.");
                        }
                        tasks.add(new Deadline(parts[0], parts[1]));
                        printAddMessage(tasks);
                    }

                    case EVENT ->  {
                        String[] parts = input.substring(5).trim().split(" /from | /to ");
                        if (parts.length < 3 || parts[0].isBlank()) {
                            throw new SkyException("An event must have /from <start> /to <end>.");
                        }
                        tasks.add(new Event(parts[0], parts[1], parts[2]));
                        printAddMessage(tasks);
                    }

                    case UNKNOWN -> throw new SkyException("I don't understand that command.");
                }

            } catch (SkyException e) {
                System.out.println("____________________________________________");
                System.out.println("Oops! " + e.getMessage());
                System.out.println("____________________________________________");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("____________________________________________");
                System.out.println("Oops! That task number does not exist.");
                System.out.println("____________________________________________");
            }
        }

        scanner.close();
    }

    // ---------- helpers ----------

    private static int parseIndex(String input, String command) throws SkyException {
        try {
            int index = Integer.parseInt(input.substring(command.length()).trim()) - 1;
            if (index < 0) {
                throw new SkyException("Task number must be positive.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SkyException("Please provide a valid task number.");
        }
    }

    private static void printList(ArrayList<Task> tasks) {
        System.out.println("____________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println("____________________________________________");
    }

    private static void printAddMessage(ArrayList<Task> tasks) {
        System.out.println("____________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________");
    }

    private static void printSingleTask(String message, Task task) {
        System.out.println("____________________________________________");
        System.out.println(message);
        System.out.println("    " + task);
        System.out.println("____________________________________________");
    }

    private static CommandType parseCommandType(String input) {
        if (input.equals("bye")) {
            return CommandType.BYE;
        }
        if (input.equals("list")) {
            return CommandType.LIST;
        }
        if (input.startsWith("todo")) {
            return CommandType.TODO;
        }
        if (input.startsWith("deadline")) {
            return CommandType.DEADLINE;
        }
        if (input.startsWith("event")) {
            return CommandType.EVENT;
        }
        if (input.startsWith("mark ")) {
            return CommandType.MARK;
        }
        if (input.startsWith("unmark ")) {
            return CommandType.UNMARK;
        }
        if (input.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        return CommandType.UNKNOWN;
    }
}
