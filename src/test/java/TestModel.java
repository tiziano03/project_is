import Model.*;
import org.junit.jupiter.api.*;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class TestModel {




    @Nested
    @DisplayName("Test del Modello: Libreria")
    class LibreriaTest {

        // oggetti di test riutilizzabili
        private Libro libroTolkien;
        private Libro libroOrwell;
        private Libro libroCoelho;

        private Libreria libreria;

        // garantisce che ogni test parta da uno stato pulito e isolato
        @BeforeEach
        void setUp() {
            // usiamo il costruttore 'protected' per creare un'istanza nuova ogni test

            libreria=Libreria.getInstance();
            libreria.reset();

            // inizializziamo le istanze che ci servono per il test
            libroTolkien = new Libro(Isbn.factory("9788845292613"), "Il Signore degli Anelli",
                    "J.R.R. Tolkien", Valutazione.CINQUE_STELLE, Genere.NARRATIVA, StatoLettura.LETTO);

            libroOrwell = new Libro(Isbn.factory("9788804668251"), "1984", "George Orwell",
                    Valutazione.QUATTRO_STELLE, Genere.NARRATIVA, StatoLettura.LETTO);

            libroCoelho = new Libro(Isbn.factory("9788845221347"), "L'Alchimista",
                    "Paulo Coelho", Valutazione.TRE_STELLE, Genere.SAGGISTICA, StatoLettura.DA_LEGGERE);


        }


        @Test
        public void testSingleton(){
            libreria.aggiungiLibro(libroTolkien);
            libreria.aggiungiLibro(libroOrwell);
            libreria.aggiungiLibro(libroCoelho);
            Libreria lib=Libreria.getInstance();
            Assertions.assertEquals(libreria, lib);
        }




        @Test
        @DisplayName("aggiungere un libro aumenta la dimensione della libreria")
            void aggiungiLibro_aumentaDimensione() {
                libreria.aggiungiLibro(libroTolkien);

                Assertions.assertEquals(1, libreria.getDimensione());
                Assertions.assertTrue(libreria.getLibri().contains(libroTolkien), "La libreria dovrebbe contenere il libro aggiunto.");
            }

            @Test
            @DisplayName("rimuovere un libro esistente lo elimina correttamente")
            void rimuoviId_conIdEsistente_rimuoveLibro() {
                libreria.aggiungiLibro(libroTolkien);
                libreria.aggiungiLibro(libroOrwell);
                UUID idDaRimuovere = libroTolkien.getId();


                Assertions.assertDoesNotThrow(() -> libreria.rimuoviId(idDaRimuovere));

                Assertions.assertEquals(1, libreria.getDimensione());
                Assertions.assertFalse(libreria.getLibri().contains(libroTolkien), "Il libro di Tolkien doveva essere rimosso.");
                Assertions.assertTrue(libreria.getLibri().contains(libroOrwell), "Il libro di Orwell non doveva essere rimosso.");
            }

            @Test
            @DisplayName("rimuovere un ID non esistente lancia IdNonTrovatoException")
            void rimuoviId_conIdInesistente_lanciaEccezione() {
                libreria.aggiungiLibro(libroTolkien);
                UUID idInesistente = UUID.randomUUID();

                Assertions.assertThrows(IdNonTrovatoException.class, () -> {
                    libreria.rimuoviId(idInesistente);
                });
                Assertions.assertEquals(1, libreria.getDimensione(), "La dimensione non doveva cambiare dopo un fallimento.");
            }

            @Test
            @DisplayName("trovaLibro restituisce il libro corretto se l'ID esiste")
            void trovaLibro_conIdEsistente_restituisceLibro() {
                libreria.aggiungiLibro(libroTolkien);

                Libro trovato = libreria.trovaLibro(libroTolkien.getId());

                Assertions.assertNotNull(trovato);
                Assertions.assertEquals(libroTolkien, trovato);
            }



            @Test
            @DisplayName("trovaLibro restituisce null se l'ID non esiste")
            void trovaLibro_conIdInesistente_restituisceNull() {
                UUID idInesistente = UUID.randomUUID();

                Libro trovato = libreria.trovaLibro(idInesistente);

                Assertions.assertNull(trovato);
            }


            @Test
            @DisplayName("l'ordinamento di default è per Titolo (alfabetico)")
            void TestOrdinamentoTitolo() {

                libreria.aggiungiLibro(libroTolkien);
                libreria.aggiungiLibro(libroOrwell);
                libreria.aggiungiLibro(libroCoelho);
                Set<Libro> libri= libreria.getLibri();

                Iterator<Libro> it=libri.iterator();


                Libro l1=it.next();
                Libro l2=it.next();
                Libro l3=it.next();


                Assertions.assertEquals("1984", l1.getTitolo());
                Assertions.assertEquals("Il Signore degli Anelli", l2.getTitolo());
                Assertions.assertEquals("L'Alchimista", l3.getTitolo());
            }

            @Test
            @DisplayName("sort(CampoLibro.AUTORE) cambia l'ordinamento per Autore")
            void sort_perAutore_cambiaOrdinamento() {
                libreria.aggiungiLibro(libroTolkien);
                libreria.aggiungiLibro(libroOrwell);
                libreria.aggiungiLibro(libroCoelho);

                libreria.sort(CampoLibro.AUTORE);



                Iterator <Libro> it=libreria.getLibri().iterator();

                Libro l1=it.next();
                Libro l2=it.next();
                Libro l3=it.next();

                Assertions.assertEquals("George Orwell", l1.getAutore());
                Assertions.assertEquals("J.R.R. Tolkien", l2.getAutore());
                Assertions.assertEquals("Paulo Coelho", l3.getAutore());
            }
        }












    @Test
    public void testIntegritaIsbnConLettere(){
        String isbnNonValido="1234567890asd"; //
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Isbn.factory(isbnNonValido);
        });
    }


    @Test
    public void testIntegritaIsbnLunghezzaErrata(){
        String isbnNonValido="12345";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Isbn.factory(isbnNonValido);
        });
    }




    @Test
    public void testIntegritaGenere(){
        String genereInammissibile="VALORE_INAMMISSIBILE";
        Assertions.assertNull(Genere.getGenere(genereInammissibile));
    }




    @Test
    public void testIntegritaStatoLettura(){
        String statoLetturaInammissibile="VALORE_INAMMISSIBILE";
        Assertions.assertNull(StatoLettura.getStatoLettura(statoLetturaInammissibile));
    }




    @Test
    public void testIntegritaValutazione(){
        String valutazioneInammissibile="VALORE_INAMMISSIBILE";
        Assertions.assertNull(Valutazione.getValutazione(valutazioneInammissibile));
    }



    @Test
    public void testGenereDefault(){
        Genere genereDefault= Genere.getDefault();
        Assertions.assertEquals("NON_SPECIFICATO", genereDefault.toString());
    }



    @Test
    public void testStatoLetturaDefault(){
        StatoLettura statoLettura=StatoLettura.getDefault();
        Assertions.assertEquals("NON_SPECIFICATO", statoLettura.toString());
    }


    @Test
    public void testValutazioneDefault(){
        Valutazione valutazione=Valutazione.getDefault();
        Assertions.assertEquals("NON_SPECIFICATO", valutazione.toString());
    }


    @Test
    public void testIsbnDefault(){
        String isbnDefault="";
        Isbn isbn= Isbn.factory(isbnDefault);
        Assertions.assertEquals("NON_SPECIFICATO", isbn.toString());
    }



}
