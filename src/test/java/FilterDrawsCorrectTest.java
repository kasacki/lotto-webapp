

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test FilterDrawsCorrectTest
 */
class FilterDrawsCorrectTest {

    @ParameterizedTest
    @ValueSource(strings = {
             "2024-01-01;1,2,3,4,5,6",
            "2024-01-02;4,6,7,5,1,3",
            "2024-01-03;8,1,10,11,12,13"
    })
    void testFilterDrawsCorrect(String line) throws Exception {
        File file = Files.createTempFile("lotto", ".txt").toFile();
        Model model = new Model(file.getAbsolutePath());

        model.addDraw(line);

        assertFalse(model.filterDraws(d -> d.numbers().contains(1)).isEmpty());

        file.delete();
    }
}
