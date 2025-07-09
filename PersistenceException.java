public class PersistenceException extends Exception {

    // Costruttore che "incapsula" l'eccezione originale (IOException, etc.)
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Un costruttore più semplice se non abbiamo un'eccezione originale
    public PersistenceException(String message) {
        super(message);
    }
}