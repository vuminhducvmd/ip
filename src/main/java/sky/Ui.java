package sky;
import java.util.Scanner;


/**
 * Handles user interaction for the Sky task manager.
 * <p>
 * Responsible for displaying messages and reading user input.
 */
public class Ui {
    private static final String LINE =
            "____________________________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Sky");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Displays the goodbye message.
     */
    public void showBye() {
        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Reads a command entered by the user.
     *
     * @return User input string
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a separator line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays an error message.
     *
     * @param message Error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a general message.
     *
     * @param message Message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
