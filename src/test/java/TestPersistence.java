import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.util.*;

@DisplayName("Test di Integrazione della Persistenza")
public class TestPersistence {
    private GestorePersistenza gestore;
    private String path;
    private Libreria libreria;


    @BeforeEach
    void setUp() {
        path="FileTestLibreria";

        // creiamo le istanze reali dei componenti per il test di integrazione
        gestore = new GestorePersistenza(new StrategiaPersistenzaJson());

        libreria = new Libreria();
    }

    @Test
    @DisplayName("Salvataggio e caricamento preservano correttamente i dati dei libri")
    void testSalvataggioECaricamentoDati() throws PersistenceException {
        Libro l1 = new Libro(Isbn.factory(""), "Titolo A", "Autore Z"
        ,Valutazione.DUE_STELLE, Genere.NARRATIVA, StatoLettura.LETTO);
        Libro l2 = new Libro(Isbn.factory(""), "Titolo B", "Autore Y",
                Valutazione.QUATTRO_STELLE, Genere.INFANZIA, StatoLettura.LETTO);
        libreria.aggiungiLibro(l1);
        libreria.aggiungiLibro(l2);


        // Se c'è eccezione il test fallisce
        gestore.salva(libreria.getMemento(), path);

        Libreria libreriaCaricata = new Libreria();

        libreriaCaricata.setMemento(gestore.carica(path));



        Assertions.assertEquals(2, libreriaCaricata.getLibri().size(), "Il numero di libri non corrisponde.");
        Assertions.assertTrue(libreriaCaricata.getLibri().contains(l1), "Il libro 1 non è stato caricato correttamente.");
        Assertions.assertTrue(libreriaCaricata.getLibri().contains(l2), "Il libro 2 non è stato caricato correttamente.");
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
        Libreria libreriaCaricata = new Libreria();
        libreriaCaricata.setMemento(gestore.carica(path));

        Set<Libro> libriOrdinati = libreriaCaricata.getLibri();
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
        Libreria libreriaCaricata = new Libreria();
        libreriaCaricata.setMemento(gestore.carica(path));

        Set<Libro> libriOrdinati = libreriaCaricata.getLibri();
        Iterator<Libro> it=libriOrdinati.iterator();
        l1 = it.next();
        l2 = it.next();
        Assertions.assertEquals("Autore Y", l1.getAutore(), "L'ordinamento per titolo non è stato ripristinato.");
        Assertions.assertEquals("Autore Z", l2.getAutore(), "L'ordinamento per titolo non è stato ripristinato.");
    }
}



