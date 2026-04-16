package pl.polsl.model;

import jakarta.persistence.*;
import pl.polsl.entities.DrawEntity;
import pl.polsl.entities.LottoNumber;
import pl.polsl.exception.LotteryDataException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Model class responsible for managing Lotto draw data using JPA.
 * Configuration is loaded from persistence.xml.
 * * @author Gemini
 * @version 2.1
 */
public class DbModel extends AbstractModel {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    /**
     * Initializes the database connection using the persistence unit name.
     * All connection details are retrieved from persistence.xml.
     * * @param persistenceUnitName name from persistence.xml (e.g., "my_persistence_unit")
     */
    public DbModel(String persistenceUnitName) {
        // Brak zaszytych danych - parametry pobierane są z persistence.xml
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.em = emf.createEntityManager();
    }

    /**
     * Reads all draws from the database and maps them to Draw records.
     * * @return list of Draw objects
     * @throws LotteryDataException on database errors
     */
    @Override
    public List<Draw> readAllDraws() throws LotteryDataException {
        try {
            TypedQuery<DrawEntity> query = em.createQuery("SELECT d FROM DrawEntity d", DrawEntity.class);
            List<DrawEntity> entities = query.getResultList();

            return entities.stream().map(entity -> {
                List<Integer> nums = entity.getNumbers().stream()
                        .map(LottoNumber::getValue)
                        .sorted()
                        .collect(Collectors.toList());
                return new Draw(entity.getDrawDate(), nums);
            }).collect(Collectors.toList());
            
        } catch (PersistenceException e) {
            throw new LotteryDataException("Error while reading data: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new draw to the database using RESOURCE_LOCAL transactions.
     * * @param line string in format "yyyy-MM-dd;n1,n2,n3,n4,n5,n6"
     * @throws LotteryDataException if validation or database error occurs
     */
    @Override
    public void addDraw(String line) throws LotteryDataException {
        validateLine(line);
        
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin(); 

            String[] parts = line.split(";");
            DrawEntity drawEntity = new DrawEntity();
            drawEntity.setDrawDate(LocalDate.parse(parts[0]));
            
            List<LottoNumber> lottoNumbers = Arrays.stream(parts[1].split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .map(val -> {
                        LottoNumber nr = new LottoNumber();
                        nr.setValue(val);
                        nr.setDraw(drawEntity); 
                        return nr;
                    }).collect(Collectors.toList());
            
            drawEntity.setNumbers(lottoNumbers);
            
            em.persist(drawEntity);
            tx.commit(); 
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new LotteryDataException("Error while saving data: " + e.getMessage(), e);
        }
    }

    public Optional<TypedBox<Long>> daysSinceLast(int number) throws LotteryDataException {
        List<Draw> filtered = filterDraws(d -> d.numbers().contains(number));

        return filtered.stream()
                .map(Draw::date)
                .max(LocalDate::compareTo)
                .map(lastDate -> new TypedBox<>(DAYS.between(lastDate, LocalDate.now())));
    }

    public List<Draw> filterDraws(DrawFilter filter) throws LotteryDataException {
        return readAllDraws().stream()
                .filter(filter::accepts)
                .collect(Collectors.toList());
    }

    private void validateLine(String line) throws LotteryDataException {
        if (line == null || line.isBlank()) {
            throw new LotteryDataException("Line cannot be empty");
        }
        String[] parts = line.split(";");
        if (parts.length != 2) {
            throw new LotteryDataException("Invalid format. Use: yyyy-MM-dd;n1,n2,n3,n4,n5,n6");
        }
        try {
            LocalDate.parse(parts[0]);
            String[] numbers = parts[1].split(",");
            if (numbers.length != 6) throw new Exception();
            for (String n : numbers) Integer.parseInt(n.trim());
        } catch (Exception e) {
            throw new LotteryDataException("Validation error.");
        }
    }

    public void close() {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }
}