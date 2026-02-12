package sky;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to persistent storage.
 * <p>
 * Tasks are stored in a text file using a line-based format.
 */
public class Storage {

    private final File file;

    /**
     * Constructs a storage object that uses the default data file
     * at {@code data/sky.txt}.
     */
    public Storage() {
        this.file = new File("data" + File.separator + "sky.txt");
    }

    /**
     * Constructs a storage object using a specified file.
     * This constructor is intended for testing.
     *
     * @param file File to load from and save to
     */
    public Storage(File file) {
        this.file = file;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return List of tasks loaded from storage
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                tasks.add(parseTask(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load saved tasks.");
        }

        return tasks;
    }

    /**
     * Saves the given task list to the storage file.
     *
     * @param tasks Task list to be saved
     */
    public void save(TaskList tasks) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            tasks.asList().stream()
                .map(Task::toDataString)
                .forEach(writer::println);
        } catch (IOException e) {
            System.out.println("Warning: Could not save tasks.");
        }
    }

    // ---------- helper ----------

    /**
     * Parses a single line from the storage file into a task object.
     *
     * @param line Line representing a task
     * @return Parsed task
     * @throws IllegalArgumentException If the task type is invalid
     */
    private static Task parseTask(String line) {
        assert line != null : "Storage.parseTask: line should not be null";

        String[] parts = line.split(" \\| ");
        boolean isDone = parts[1].equals("1");

        assert parts.length >= 3
            : "Storage.parseTask: invalid file format";

        Task task;
        switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                break;
            case "D":
                assert parts.length == 4
                    : "Deadline format should have 4 fields";
                task = new Deadline(parts[2], LocalDate.parse(parts[3]));
                break;
            case "E":
                assert parts.length == 5
                    : "Event format should have 5 fields";
                task = new Event(parts[2], LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
                break;
            default:
                throw new IllegalArgumentException("Invalid task type in file");
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
