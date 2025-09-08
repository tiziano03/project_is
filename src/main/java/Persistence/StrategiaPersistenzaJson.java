package Persistence;

import Model.Libreria;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class StrategiaPersistenzaJson implements StrategiaPersistenza {

    private final Gson gson;

    public StrategiaPersistenzaJson() {
        // Inizializziamo Gson con la formattazione "pretty printing"
        // per avere un file JSON leggibile.
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void serialize(Libreria.Memento memento, String path) throws PersistenceException { // Dichiariamo l'eccezione
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(memento, writer);
        } catch (IOException e) {
            //lanciamo la nostra eccezione personalizzata,
            throw new PersistenceException("Errore durante il salvataggio su file: " + path);
        }
    }

    @Override
    public Libreria.Memento deserialize(String path) throws PersistenceException {
        try (Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, Libreria.Memento.class);
        } catch (FileNotFoundException e) {
            //lanciamo le nostre eccezioni personalizzate.
            throw new PersistenceException("File di salvataggio non trovato: " + path);
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura da file: " + path);
        } catch (JsonSyntaxException e) {
            throw new PersistenceException("Il file di salvataggio è corrotto o malformato: " + path);
        }
    }
}
