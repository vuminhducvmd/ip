package sky;

/**
 * Represents a generic task in the Sky task manager.
 * <p>
 * A task has a description and a completion status.
 * Subclasses define the specific type of task and how it is represented.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a task with the given description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns a status icon representing whether the task is done.
     *
     * @return "X" if the task is completed, otherwise a blank space
     */
    protected String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the description of this task.
     *
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this task.
     *
     * @param newDescription
     */
    public void setDescription(String newDescription) {
        assert newDescription != null : "Description cannot be null";
        this.description = newDescription;
    }

    /**
     * Returns a user-facing string representation of this task.
     *
     * @return Formatted string representing the task
     */
    @Override
    public abstract String toString();

    /**
     * Returns a string representation of this task suitable for storage.
     *
     * @return Data string representing the task
     */
    public abstract String toDataString();
}
