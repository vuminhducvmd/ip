package sky;

/**
 * Represents a todo task that has only a description
 * and no associated date or time.
 */
public class Todo extends Task {

    /**
     * Constructs a todo task with the given description.
     *
     * @param description Description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a user-facing string representation of this todo task.
     *
     * @return Formatted string representing the todo task
     */
    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns a data string representation of this todo task
     * suitable for storage.
     *
     * @return Data string representing the todo task
     */
    @Override
    public String toDataString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
