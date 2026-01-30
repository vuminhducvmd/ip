package sky;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

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
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matches.add(task);
            }
        }

        return matches;
    }
}
