package models.exceptions;

/**
 * Created by andrey on 29.03.16.
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
