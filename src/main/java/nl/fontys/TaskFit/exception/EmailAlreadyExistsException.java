package nl.fontys.TaskFit.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message){
    super(message);

    }
}
