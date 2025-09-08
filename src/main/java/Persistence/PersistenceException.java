package Persistence;

public class PersistenceException extends Exception {

    // Un costruttore più semplice se non abbiamo un'eccezione originale
    public PersistenceException(String message) {
        super(message);
    }

}