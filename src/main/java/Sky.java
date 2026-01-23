import java.util.Scanner;

public class Sky {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

            System.out.println("____________________________________________");
            System.out.println("    " + input);
            System.out.println("____________________________________________");
        }

        scanner.close();
    }
}
