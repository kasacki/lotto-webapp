package pl.polsl.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.polsl.exception.LotteryDataException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Model class responsible for managing Lotto draw data.
 *
 * <p>This class provides functionality for:
 * <ul>
 *     <li>Reading and writing draw records from/to a text file</li>
 *     <li>Parsing draw lines into typed objects</li>
 *     <li>Filtering draws using a functional interface</li>
 *     <li>Calculating statistics such as last appearance of a given number</li>
 * </ul>
 *
 * <p>File format:
 * <pre>
 * yyyy-MM-dd;num1,num2,num3,...
 * </pre>
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Model {

    /** File storing the Lotto draw history. */
    private final File dataFile;

    /** Formatter for parsing and writing dates. */
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Constructs model using file path.
     *
     * @param path path to data file
     * @throws LotteryDataException if file cannot be accessed
     */
    public Model(String path) throws LotteryDataException {
        this.dataFile = new File(path);

        try {
            if (!dataFile.exists()) {
                File parent = dataFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                dataFile.createNewFile();
            }

            if (!dataFile.canRead() || !dataFile.canWrite()) {
                throw new LotteryDataException("Cannot access file: " + path);
            }

        } catch (IOException e) {
            throw new LotteryDataException("File error: " + e.getMessage(), e);
        }
    }

    
    private void validateLine(String line) throws LotteryDataException {
    if (line == null || line.isBlank()) {
        throw new LotteryDataException("Line cannot be empty");
    }

    String[] parts = line.split(";");
    if (parts.length != 2) {
        throw new LotteryDataException("Invalid format. Use: yyyy-MM-dd;num1,num2,...");
    }

    // Sprawdzenie daty
    try {
        LocalDate.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (Exception e) {
        throw new LotteryDataException("Invalid date format. Use yyyy-MM-dd");
    }

    // Sprawdzenie, że jest dokładnie 6 liczb
    String[] numbers = parts[1].split(",");
    if (numbers.length != 6) {
        throw new LotteryDataException("There must be exactly 6 numbers");
    }

    // Sprawdzenie, czy wszystkie są liczbami całkowitymi
    for (String num : numbers) {
        try {
            Integer.valueOf(num.trim());
        } catch (NumberFormatException e) {
            throw new LotteryDataException("Numbers must be integers");
        }
    }
}
    /**
     * Appends a new draw entry to the file.
     *
     * @param line draw entry in text form
     * @throws LotteryDataException for invalid format or write error
     */
   public void addDraw(String line) throws LotteryDataException {
    // Walidacja formatu linii
    validateLine(line);

    if (!dataFile.exists()) {
        throw new LotteryDataException("Cannot access data file");
    }

    Draw newDraw = parseLine(line);
    List<Draw> draws = readAllDraws();

  

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, true))) {
        bw.write(line);
        bw.newLine();
    } catch (IOException e) {
        throw new LotteryDataException("Write error: " + e.getMessage(), e);
    }
}


    /**
     * Reads all draw entries from the file.
     *
     * @return list of parsed draws
     * @throws LotteryDataException if file is not readable or reading fails
     */
    public List<Draw> readAllDraws() throws LotteryDataException {

        // Deterministic validation for unit testing
        if (!dataFile.exists() || !dataFile.canRead()) {
            throw new LotteryDataException("File is not readable");
        }

        List<Draw> draws = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            List<String> lines = br.lines().collect(Collectors.toList());

            for (String line : lines) {
                line = line.trim();
                if (!line.isEmpty()) {
                    draws.add(parseLine(line));
                }
            }

        } catch (IOException e) {
            throw new LotteryDataException("Read error: " + e.getMessage(), e);
        }

        return draws;
    }

    /**
     * Parses a single file line into Draw object.
     */
    private Draw parseLine(String line) {
        String[] parts = line.split(";");
        LocalDate date = LocalDate.parse(parts[0], dateFormat);

        List<Integer> numbers = Arrays.stream(parts[1].split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return new Draw(date, numbers);
    }

    /**
     * Calculates number of days since last appearance of given number.
     *
     * @param number searched lottery number
     * @return optional typed box with days count
     * @throws LotteryDataException if data file is not accessible
     */
    public Optional<TypedBox<Long>> daysSinceLast(int number) throws LotteryDataException {

        // Deterministic exception for testing
        if (!dataFile.exists() || !dataFile.canRead()) {
            throw new LotteryDataException("Cannot access data file");
        }

        List<Draw> filtered = filterDraws(d -> d.numbers().contains(number));

        return filtered.stream()
                .map(Draw::date)
                .max(LocalDate::compareTo)
                .map(date -> new TypedBox<>(DAYS.between(date, LocalDate.now())));
    }

    /**
     * Filters draws using provided functional interface.
     *
     * @param filter draw filter
     * @return list of matching draws
     * @throws LotteryDataException if data cannot be read
     */
    public List<Draw> filterDraws(DrawFilter filter) throws LotteryDataException {
        return readAllDraws().stream()
                .filter(filter::accepts)
                .collect(Collectors.toList());
    }
}
