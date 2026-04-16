

import org.junit.jupiter.api.*;
import pl.polsl.exception.LotteryDataException;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test .AddDrawExceptionTest 
 */
class AddDrawExceptionTest {

    private File tempFile;
    private Model model;

    @BeforeEach
    void setUp() throws LotteryDataException, Exception {
        tempFile = Files.createTempFile("lotto", ".txt").toFile();
        model = new Model(tempFile.getAbsolutePath());
        tempFile.delete(); // wymusza wyjątek
    }

    @Test
    void testAddDraw_throwsLotteryDataException() {
        LotteryDataException exception = assertThrows(LotteryDataException.class,
                () -> model.addDraw("2025-01-01;1,2,3,4,5,6"));
        assertTrue(exception.getMessage().contains("Cannot access data file"));
    }
}
