

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.exception.LotteryDataException;
import pl.polsl.model.Model;

/**
 * Test AddDrawBoundaryTest
 */
class AddDrawBoundaryTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "2024-01-01;1",
            "2024-01-02;2",
            "2024-01-03;3"
    })
    void testAddDrawBoundary(String line) throws Exception {
        File file = Files.createTempFile("lotto", ".txt").toFile();
        Model model = new Model(file.getAbsolutePath());

        LotteryDataException exception = assertThrows(LotteryDataException.class,
                () -> model.addDraw(line));
        assertTrue(exception.getMessage().contains("There must be exactly 6 numbers"));

        file.delete();
    }
}
