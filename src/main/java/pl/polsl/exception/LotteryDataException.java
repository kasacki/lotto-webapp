package pl.polsl.exception;


/**
* Custom exception thrown by the model when data file operations or parsing fail.
*
* @author Author
* @version 1.0
*/
public class LotteryDataException extends Exception {
public LotteryDataException(String message) { super(message); }
public LotteryDataException(String message, Throwable cause) { super(message, cause); }
}