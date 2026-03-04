package sky;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a specific time period.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructs an event task with a description, start date, and end date.
     *
     * @param description Description of the event
     * @param from Start date of the event
     * @param to End date of the event
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Updates the start date of this event.
     *
     * @param newFrom New start date
     * @throws IllegalArgumentException If the start date is after the end date
     */
    public void updateFrom(LocalDate newFrom) {
        if (newFrom.isAfter(this.to)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        
        this.from = newFrom;
    }

    /**
     * Updates the end date of this event.
     *
     * @param newTo New end date
     */
    public void updateTo(LocalDate newTo) {
        this.to = newTo;
    }

    /**
     * Returns a user-facing string representation of this event task.
     *
     * @return Formatted string representing the event task
     */
    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description
                + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a data string representation of this event task
     * suitable for storage.
     *
     * @return Data string representing the event task
     */
    @Override
    public String toDataString() {
        return "E | " + (isDone ? "1" : "0")
                + " | " + description + " | " + from + " | " + to;
    }
}
