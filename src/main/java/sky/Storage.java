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

    private static final String DONE_MARKER = "1";

    private static final int IDX_TYPE = 0;
    private static final int IDX_DONE = 1;
    private static final int IDX_DESC = 2;
    private static final int IDX_DATE1 = 3;
    private static final int IDX_DATE2 = 4;

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
                String line = fileScanner.nextLine();
                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            System.err.println(
                "Warning: Could not load saved tasks: "
                + e.getMessage()
            );
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
            System.err.println(
                "Warning: Could not save tasks: "
                + e.getMessage()
            );
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

        if (parts.length < 3) {
            throw new IllegalArgumentException(
                "Corrupted task data in storage file."
            );
        }

        boolean isDone = DONE_MARKER.equals(parts[IDX_DONE]);

        String type = parts[IDX_TYPE];
        String description = parts[IDX_DESC];

        assert parts.length >= 3
            : "Storage.parseTask: invalid file format";

        assert parts.length >= 3
            : "Storage.parseTask: invalid file format";

        Task task;

        switch (type) {
        case "T":
            task = new Todo(description);
            break;

        case "D":
                assert parts.length == 4
                    : "Deadline format should have 4 fields";
            if (parts.length != 4) {
                throw new IllegalArgumentException(
                    "Invalid deadline format in storage file."
                );
            }
            task = new Deadline(
                description,
                LocalDate.parse(parts[IDX_DATE1])
            );
            break;

        case "E":
                assert parts.length == 5
                    : "Event format should have 5 fields";
            if (parts.length != 5) {
                throw new IllegalArgumentException(
                    "Invalid event format in storage file."
                );
            }
            task = new Event(
                description,
                LocalDate.parse(parts[IDX_DATE1]),
                LocalDate.parse(parts[IDX_DATE2])
            );
            break;

        default:
            throw new IllegalArgumentException(
                "Invalid task type in storage file."
            );
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
