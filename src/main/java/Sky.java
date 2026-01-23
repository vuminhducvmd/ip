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

            if (input.equals("bye")) {
                System.out.println("____________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________");
                break;
            }

            if (input.equals("list")) {
                System.out.println("____________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("    " + (i + 1) + "." + tasks[i]);
                }
                System.out.println("____________________________________________");
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();

                System.out.println("____________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("    " + tasks[index]);
                System.out.println("____________________________________________");
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsNotDone();

                System.out.println("____________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("    " + tasks[index]);
                System.out.println("____________________________________________");
                continue;
            }

            // todo
            if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                tasks[taskCount++] = new Todo(desc);

                System.out.println("____________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("    " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________");
                continue;
            }

            // deadline
            if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                tasks[taskCount++] = new Deadline(parts[0], parts[1]);

                System.out.println("____________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("    " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________");
                continue;
            }

            // event
            if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);

                System.out.println("____________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("    " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________");
                continue;
            }
        }

        scanner.close();
    }
}
