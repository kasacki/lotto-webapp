

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test DaysSinceLastCorrectTest
 */
class DaysSinceLastCorrectTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testDaysSinceLastCorrect(int daysAgo) throws Exception {
        File file = Files.createTempFile("lotto", ".txt").toFile();
        Model model = new Model(file.getAbsolutePath());

        LocalDate date = LocalDate.now().minusDays(daysAgo);
        model.addDraw(date + ";1,2,3,5,6,7");

        long expected = LocalDate.now().toEpochDay() - date.toEpochDay();

        assertEquals(expected, model.daysSinceLast(5).get().get());

        file.delete();
    }
}
