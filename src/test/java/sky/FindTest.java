package sky;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindTest {

    @Test
    void find_keywordMatches_returnsCorrectTasks() {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write code"));
        tasks.add(new Deadline("return book", java.time.LocalDate.now()));

        ArrayList<Task> matches = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains("book")) {
                matches.add(tasks.get(i));
            }
        }

        assertEquals(2, matches.size());
        assertEquals("read book", matches.get(0).getDescription());
        assertEquals("return book", matches.get(1).getDescription());
    }
}
