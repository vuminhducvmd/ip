import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private static final String DATA_DIR = "data";
    private static final String FILE_PATH = DATA_DIR + File.separator + "sky.txt";

    public static ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return tasks; // first run, nothing to load
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                tasks.add(parseTask(line));
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Warning: Could not load saved tasks.");
        }

        return tasks;
    }

    public static void save(TaskList tasks) {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }

            PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH));
            for (int i = 0; i < tasks.size(); i++)  {
                writer.println(tasks.get(i).toDataString());
            }
            writer.close();
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
