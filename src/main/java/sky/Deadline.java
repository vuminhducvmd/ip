package sky;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected LocalDate by;

    /**
     * Constructs a deadline task with a description and due date.
     *
     * @param description Description of the deadline task
     * @param by Due date of the task
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Updates the due date of this deadline task.
     *
     * @param newDate New due date
     */
    public void updateBy(LocalDate newDate) {
        this.by = newDate;
    }

    /**
     * Returns a user-facing string representation of this deadline task.
     *
     * @return Formatted string representing the deadline task
     */
    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description
                + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a data string representation of this deadline task
     * suitable for storage.
     *
     * @return Data string representing the deadline task
     */
    @Override
    public String toDataString() {
        // LocalDate.toString() -> yyyy-MM-dd (ISO format)
        return "D | " + (isDone ? "1" : "0")
                + " | " + description + " | " + by;
    }
}
