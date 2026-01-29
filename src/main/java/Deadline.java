import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description
                + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toDataString() {
        // LocalDate.toString() -> yyyy-MM-dd (ISO format)
        return "D | " + (isDone ? "1" : "0")
                + " | " + description + " | " + by;
    }
}
