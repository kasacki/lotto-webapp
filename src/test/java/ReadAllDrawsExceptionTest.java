

import org.junit.jupiter.api.*;
import pl.polsl.exception.LotteryDataException;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test ReadAllDrawsExceptionTest
 */

class ReadAllDrawsExceptionTest {

    private File tempFile;
    private Model model;

    @BeforeEach
    void setUp() throws LotteryDataException, Exception {
        tempFile = Files.createTempFile("lotto", ".txt").toFile();
        model = new Model(tempFile.getAbsolutePath());
        tempFile.delete();
    }

    @Test
    void testReadAllDraws_throwsLotteryDataException() {
        LotteryDataException exception = assertThrows(LotteryDataException.class,
                () -> model.readAllDraws());
        assertTrue(exception.getMessage().contains("File is not readable"));
    }
}
