package pl.polsl.model;

import pl.polsl.exception.LotteryDataException;
import java.util.List;

/**
 * Abstract base model — provides contract for Lotto model operations.
 */
public abstract class AbstractModel {

    /**
     * Read all draws from the underlying data source.
     * @return list of Draw records
     * @throws LotteryDataException on I/O or parsing errors
     */
    public abstract List<Draw> readAllDraws() throws LotteryDataException;

    /**
     * Append a draw line to the underlying data source.
     * @param line draw line in format yyyy-MM-dd;num1,num2,...
     * @throws LotteryDataException on I/O or format errors
     */
    public abstract void addDraw(String line) throws LotteryDataException;
}
