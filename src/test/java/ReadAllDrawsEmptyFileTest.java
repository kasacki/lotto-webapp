

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.model.Model;

/**
 * Test ReadAllDrawsEmptyFileTest
 */
class ReadAllDrawsEmptyFileTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void testReadAllDrawsEmptyFile(int dummy) throws Exception {
        File file = Files.createTempFile("lotto", ".txt").toFile();
        Model model = new Model(file.getAbsolutePath());

        assertEquals(0, model.readAllDraws().size());

        file.delete();
    }
}
