import java.util.Scanner;

public class Sky {
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        System.out.println("____________________________________________");
        System.out.println("Hello! I'm Sky");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________");

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("____________________________________________");
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________");
                    break;
                }

                if (input.equals("list")) {
                    printList(tasks, taskCount);
                    continue;
                }

                if (input.startsWith("mark ")) {
                    int index = parseIndex(input, "mark");
                    tasks[index].markAsDone();
                    printMarkMessage("Nice! I've marked this task as done:", tasks[index]);
                    continue;
                }

                if (input.startsWith("unmark ")) {
                    int index = parseIndex(input, "unmark");
                    tasks[index].markAsNotDone();
                    printMarkMessage("OK, I've marked this task as not done yet:", tasks[index]);
                    continue;
                }

                if (input.startsWith("todo")) {
                    String desc = input.substring(4).trim();
                    if (desc.isEmpty()) {
                        throw new SkyException("The description of a todo cannot be empty.");
                    }
                    tasks[taskCount++] = new Todo(desc);
                    printAddMessage(tasks, taskCount);
                    continue;
                }

                if (input.startsWith("deadline")) {
                    String[] parts = input.substring(8).trim().split(" /by ", 2);
                    if (parts.length < 2 || parts[0].isBlank()) {
                        throw new SkyException("A deadline must have a description and /by <time>.");
                    }
                    tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                    printAddMessage(tasks, taskCount);
                    continue;
                }

                if (input.startsWith("event")) {
                    String[] parts = input.substring(5).trim().split(" /from | /to ");
                    if (parts.length < 3 || parts[0].isBlank()) {
                        throw new SkyException("An event must have /from <start> /to <end>.");
                    }
                    tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
                    printAddMessage(tasks, taskCount);
                    continue;
                }

                throw new SkyException("I don't understand that command.");

            } catch (SkyException e) {
                System.out.println("____________________________________________");
                System.out.println("Oops! " + e.getMessage());
                System.out.println("____________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________");
                System.out.println("Something went wrong. Please try again.");
                System.out.println("____________________________________________");
            }
        }

        scanner.close();
    }

    // ---------------- helper methods ----------------

    private static int parseIndex(String input, String command) throws SkyException {
        try {
            int index = Integer.parseInt(input.substring(command.length()).trim()) - 1;
            if (index < 0) {
                throw new SkyException("Task index must be positive.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SkyException("Please provide a valid task number.");
        }
    }

    private static void printList(Task[] tasks, int taskCount) {
        System.out.println("____________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    " + (i + 1) + "." + tasks[i]);
        }
        System.out.println("____________________________________________");
    }

    private static void printAddMessage(Task[] tasks, int taskCount) {
        System.out.println("____________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________");
    }

    private static void printMarkMessage(String message, Task task) {
        System.out.println("____________________________________________");
        System.out.println(message);
        System.out.println("    " + task);
        System.out.println("____________________________________________");
    }
}
