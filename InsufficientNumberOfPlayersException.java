package game;

public class InsufficientNumberOfPlayersException extends Exception {
    public InsufficientNumberOfPlayersException(String message) {
        super(message);
    }
}
