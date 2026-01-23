import java.util.Scanner;

public class Sky {
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] tasks = new String[MAX_TASKS];
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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("    " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________");
                continue;
            }

            // otherwise, treat input as a task to add
            if (taskCount < MAX_TASKS) {
                tasks[taskCount] = input;
                taskCount++;

                System.out.println("____________________________________________");
                System.out.println("    added: " + input);
                System.out.println("____________________________________________");
            }
        }

        scanner.close();
    }
}
