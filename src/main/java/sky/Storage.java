package sky;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    // private static final String DATA_DIR = "data";
    // private static final String FILE_PATH = DATA_DIR + File.separator + "sky.txt";
    private final File file;

    // Default constructor 
    public Storage() {
        this.file = new File("data" + File.separator + "sky.txt");
    }

    // Test constructor (JUnit use)
    public Storage(File file) {
        this.file = file;
    }

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

    public void save(TaskList tasks) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < tasks.size(); i++) {
                writer.println(tasks.get(i).toDataString());
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not save tasks.");
        }
    }

    // ---------- helper ----------

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        boolean isDone = parts[1].equals("1");

        Task task;
        switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                break;
            case "D":
                task = new Deadline(parts[2], LocalDate.parse(parts[3]));
                break;
            case "E":
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
