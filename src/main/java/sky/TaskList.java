package sky;
import java.util.ArrayList;
import java.util.stream.Collectors;


/**
 * Represents a list of tasks in the Sky task manager.
 * <p>
 * This class provides basic operations for adding, removing,
 * retrieving, and accessing tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a task list using an existing list of tasks.
     *
     * @param tasks List of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index Index of the task (0-based)
     * @return The task at the given index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Index of the task to remove (0-based)
     * @return The removed task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return List of tasks
     */
    public ArrayList<Task> asList() {
        return tasks;
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword.
     *
     * @param keyword Keyword to search for
     * @return List of matching tasks
     */
    public ArrayList<Task> find(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
