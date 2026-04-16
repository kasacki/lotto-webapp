

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test DaysSinceLastBoundaryTest
 */
class DaysSinceLastBoundaryTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "2024-01-01;1,2,3,4,5,6",
            "2024-01-02;4,6,7,5,2,3",
            "2024-01-03;8,9,10,11,12,13"
    })
    void testDaysSinceLastBoundary(String line) throws Exception {
        File file = Files.createTempFile("lotto", ".txt").toFile();
        Model model = new Model(file.getAbsolutePath());

        model.addDraw(line);

        assertTrue(model.daysSinceLast(99).isEmpty());

        file.delete();
    }
}
