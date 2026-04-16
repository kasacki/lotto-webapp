

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test .AddDrawCorrectTest 
 */
class AddDrawCorrectTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "2024-01-01;1,2,3,4,5,6",
            "2024-01-02;7,8,9,10,11,12",
            "2024-01-03;13,14,15,16,17,18"
    })
    void testAddDrawCorrect(String line) throws Exception {
        File file = Files.createTempFile("lotto", ".txt").toFile();
        Model model = new Model(file.getAbsolutePath());

        assertDoesNotThrow(() -> model.addDraw(line));

        file.delete();
    }
}
