import Model.*;
import Persistence.GestorePersistenza;
import Persistence.PersistenceException;
import Persistence.StrategiaPersistenzaJson;
import org.junit.jupiter.api.*;

import java.util.*;

@DisplayName("Test della Persistenza")
public class TestPersistence {
    private GestorePersistenza gestore;
    private String path;
    private Libreria libreria;


    @BeforeEach
    void setUp() {
        path="FileTestLibreria";

        // creiamo le istanze reali dei componenti per il test di integrazione
        gestore = new GestorePersistenza(new StrategiaPersistenzaJson());

        libreria = Libreria.getInstance();
        libreria.reset();
    }


    @Test
    @DisplayName("Salvataggio e caricamento preservano correttamente i dati dei libri")
    void testSalvataggioECaricamentoDati() throws PersistenceException {
        Libro l1 = new Libro(Isbn.factory(""), "Titolo A", "Autore Z"
        , Valutazione.DUE_STELLE, Genere.NARRATIVA, StatoLettura.LETTO);
        Libro l2 = new Libro(Isbn.factory(""), "Titolo B", "Autore Y",
                Valutazione.QUATTRO_STELLE, Genere.INFANZIA, StatoLettura.LETTO);
        libreria.aggiungiLibro(l1);
        libreria.aggiungiLibro(l2);


        // Se c'è eccezione il test fallisce
        gestore.salva(libreria.getMemento(), path);

        libreria.reset();

        libreria.setMemento(gestore.carica(path));



        Assertions.assertEquals(2, libreria.getLibri().size(), "Il numero di libri non corrisponde.");
        Assertions.assertTrue(libreria.getLibri().contains(l1), "Il libro 1 non è stato caricato correttamente.");
        Assertions.assertTrue(libreria.getLibri().contains(l2), "Il libro 2 non è stato caricato correttamente.");
    }


    @Test
    @DisplayName("Salvataggio e caricamento preservano l'ordinamento per Titolo")
    void testConsistenzaOrdinamentoPerTitolo() throws PersistenceException {
        Libro l1 = new Libro(Isbn.factory(""), "Titolo A", "Autore Z"
                ,Valutazione.DUE_STELLE, Genere.NARRATIVA, StatoLettura.LETTO);
        Libro l2 = new Libro(Isbn.factory(""), "Titolo B", "Autore Y",
                Valutazione.QUATTRO_STELLE, Genere.INFANZIA, StatoLettura.LETTO);
        libreria.aggiungiLibro(l1);
        libreria.aggiungiLibro(l2);
        libreria.sort(CampoLibro.TITOLO);

        gestore.salva(libreria.getMemento(), path);
        libreria.reset();
        libreria.setMemento(gestore.carica(path));

        Set<Libro> libriOrdinati = libreria.getLibri();
        Iterator<Libro> it=libriOrdinati.iterator();
        l1 = it.next();
        l2 = it.next();
        Assertions.assertEquals("Titolo A", l1.getTitolo(), "L'ordinamento per titolo non è stato ripristinato.");
        Assertions.assertEquals("Titolo B", l2.getTitolo(), "L'ordinamento per titolo non è stato ripristinato.");
    }

    @Test
    @DisplayName("Salvataggio e caricamento preservano l'ordinamento per Autore")
    void testConsistenzaOrdinamentoPerAutore() throws PersistenceException {

        Libro l1 = new Libro(Isbn.factory(""), "Titolo A", "Autore Z"
                ,Valutazione.DUE_STELLE, Genere.NARRATIVA, StatoLettura.LETTO);
        Libro l2 = new Libro(Isbn.factory(""), "Titolo B", "Autore Y",
                Valutazione.QUATTRO_STELLE, Genere.INFANZIA, StatoLettura.LETTO);
        libreria.aggiungiLibro(l1);
        libreria.aggiungiLibro(l2);

        libreria.sort(CampoLibro.AUTORE);

        gestore.salva(libreria.getMemento(), path);
        libreria.reset();
        libreria.setMemento(gestore.carica(path));

        Set<Libro> libriOrdinati = libreria.getLibri();
        Iterator<Libro> it=libriOrdinati.iterator();
        l1 = it.next();
        l2 = it.next();
        Assertions.assertEquals("Autore Y", l1.getAutore(), "L'ordinamento per titolo non è stato ripristinato.");
        Assertions.assertEquals("Autore Z", l2.getAutore(), "L'ordinamento per titolo non è stato ripristinato.");
    }
}



