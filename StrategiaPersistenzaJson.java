import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class StrategiaPersistenzaJson implements StrategiaPersistenza {

    private final Gson gson;

    public StrategiaPersistenzaJson() {
        // Inizializziamo Gson con la formattazione "pretty printing"
        // per avere un file JSON leggibile.
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void serialize(Libreria.Memento memento, String path) {
        // Il blocco try-with-resources chiude automaticamente il file writer.
        try (Writer writer = new FileWriter(path)) {
            // La magia di Gson: converte l'oggetto Java in una stringa JSON
            // e la scrive direttamente sul file.
            gson.toJson(memento, writer);
        } catch (IOException e) {
            // Gestione degli errori nel caso in cui non si possa scrivere il file.
            System.err.println("Errore critico durante il salvataggio su file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Libreria.Memento deserialize(String path) {
        // Il blocco try-with-resources chiude automaticamente il file reader.
        try (Reader reader = new FileReader(path)) {
            // La magia inversa: legge il JSON dal file e lo usa per costruire
            // un nuovo oggetto del tipo specificato (Library.Memento.class).
            return gson.fromJson(reader, Libreria.Memento.class);
        } catch (IOException e) {
            System.err.println("Impossibile caricare da file (potrebbe non esistere ancora): " + e.getMessage());
            return null; // È normale se il file non è mai stato creato.
        } catch (JsonSyntaxException e) {
            System.err.println("Errore di sintassi nel file JSON (file corrotto?): " + e.getMessage());
            return null; // Il file esiste ma non è un JSON valido.
        }
    }
}