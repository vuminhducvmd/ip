package sky;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    private Storage storage;

    @AfterEach
    void tearDown() {
        // TempDir auto-deletes, nothing needed here
    }

    @Test
    void saveAndLoad_tasksPersistCorrectly() {
        File testFile = tempDir.resolve("test.txt").toFile();
        storage = new Storage(testFile);

        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("return book", LocalDate.of(2019, 12, 2)));

        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("[T][ ] read book", loaded.get(0).toString());
        assertEquals("[D][ ] return book (by: Dec 02 2019)", loaded.get(1).toString());
    }
}
