package sky;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    void parseIndex_validInput_returnsCorrectIndex() throws SkyException {
        int index = Parser.parseIndex("mark 3", "mark");
        assertEquals(2, index);
    }

    @Test
    void parseIndex_invalidInput_throwsException() {
        assertThrows(SkyException.class, () ->
                Parser.parseIndex("mark x", "mark"));
    }
}
