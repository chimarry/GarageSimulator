package business_logic.dao;

public class InvalidDataActionException extends RuntimeException {

    public InvalidDataActionException(String message){
        super(message);
    }
}
