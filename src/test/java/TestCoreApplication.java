import org.junit.jupiter.api.*;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;


public class TestCoreApplication {



    @Nested
    @DisplayName("Test del ComandoAggiungi")
    class ComandoAggiungiTest {

        // LibreriaStub agisce come spia per registrare le chiamate ricevute
        private static class LibreriaStub extends Libreria {
            boolean aggiungiLibroChiamato = false;
            Libro libroRicevuto = null;

            @Override
            public void aggiungiLibro(Libro libro) {
                this.aggiungiLibroChiamato = true;
                this.libroRicevuto = libro;
            }
        }

        private LibreriaStub libreriaFinta;
        private ComandoAggiungi comando;

        @BeforeEach
        void setUp() {
            libreriaFinta = new LibreriaStub();
            comando = new ComandoAggiungi(libreriaFinta);
        }

        @Test
        @DisplayName("Happy Path: execute() chiama correttamente aggiungiLibro sulla Libreria")
        void execute_conDatiValidi_chiamaCorrettamenteAggiungiLibro() {

            String titoloTest = "Il Signore degli Anelli";
            String autoreTest = "J.R.R. Tolkien";
            String isbnTest="";
            String genereTest=Genere.getDefault().name();
            String statoLetturaTest=StatoLettura.getDefault().name();
            String valutazioneTest=Valutazione.getDefault().name();

            Map<NomeParametro, String> parametri = new HashMap<>();
            parametri.put(NomeParametro.TITOLO, titoloTest);
            parametri.put(NomeParametro.AUTORE, autoreTest);
            parametri.put(NomeParametro.ISBN, isbnTest);
            parametri.put(NomeParametro.GENERE, genereTest);
            parametri.put(NomeParametro.STATOLETTURA,statoLetturaTest);
            parametri.put(NomeParametro.VALUTAZIONE, valutazioneTest);


            RichiestaComando richiesta = new RichiestaComando(NomeComando.AGGIUNGI, parametri);


            comando.execute(richiesta);

            // Verifichiamo lo stato dello stub
            Assertions.assertTrue(libreriaFinta.aggiungiLibroChiamato, "Il metodo aggiungiLibro() doveva essere chiamato.");
            Assertions.assertNotNull(libreriaFinta.libroRicevuto, "Doveva essere passato un oggetto Libro non nullo.");
            Assertions.assertEquals(titoloTest, libreriaFinta.libroRicevuto.getTitolo(), "Il titolo del libro passato non è corretto.");
            Assertions.assertEquals(autoreTest, libreriaFinta.libroRicevuto.getAutore(), "L'autore del libro passato non è corretto.");
        }
    }



    @Nested
    class ComandoRimuoviTest{

        private static class LibreriaStub extends Libreria {

            boolean rimuoviLibroChiamato = false;
            UUID idRicevuto = null;

            @Override
            public void rimuoviId(UUID id) {
                this.rimuoviLibroChiamato = true;
                this.idRicevuto = id;
            }
        }

        private LibreriaStub libreriaFinta;
        private ComandoRimuovi comando;

        @BeforeEach
        void setUp() {
            libreriaFinta = new LibreriaStub();
            comando = new ComandoRimuovi(libreriaFinta);
        }

        @Test
        @DisplayName("Happy Path: execute() con un ID valido chiama correttamente rimuoviLibro")
        void execute_conIdValido_chiamaCorrettamenteRimuoviLibro() {
            UUID idDiTest = UUID.randomUUID();
            Map<NomeParametro, String> parametri = new HashMap<>();
            parametri.put(NomeParametro.UUID, idDiTest.toString());
            RichiestaComando richiesta = new RichiestaComando(NomeComando.RIMUOVI, parametri);

            comando.execute(richiesta);

            Assertions.assertTrue(libreriaFinta.rimuoviLibroChiamato, "Il metodo rimuoviLibro() doveva essere chiamato.");
            Assertions.assertEquals(idDiTest, libreriaFinta.idRicevuto, "È stato passato l'ID sbagliato a rimuoviLibro.");
        }
    }











        @Nested
        public class TestController{
            // stub per i comandi
            static class ComandoStub implements Command {
                boolean executeChiamato = false;
                RichiestaComando richiestaRicevuta = null;

                @Override
                public void execute(RichiestaComando richiesta) {
                    this.executeChiamato = true;
                    this.richiestaRicevuta = richiesta;
                }
            }

            @Test
            public void processa_conComandoValido_delegaAlComandoCorretto() {

                ComandoStub comandoModificaFinto = new ComandoStub();
                ComandoStub comandoAggiungiFinto = new ComandoStub();

                Map<NomeComando, Command> mappaComandiFinta = new HashMap<>();
                mappaComandiFinta.put(NomeComando.MODIFICA, comandoModificaFinto);
                mappaComandiFinta.put(NomeComando.AGGIUNGI, comandoAggiungiFinto);

                // il controller viene creato con i comandi finti
                ControllerLibreria controller = new ControllerLibreria(mappaComandiFinta, null); // La libreria non serve per questo test

                RichiestaComando richiestaDiModifica = new RichiestaComando(NomeComando.MODIFICA, new HashMap<>());

                controller.processa(richiestaDiModifica);


                // ha chiamato lo specialista giusto?
                Assertions.assertTrue(comandoModificaFinto.executeChiamato, "Doveva essere chiamato l'execute del comando di modifica.");

                // non ha chiamato gli specialisti sbagliati?
                Assertions.assertFalse(comandoAggiungiFinto.executeChiamato, "NON doveva essere chiamato il comando di aggiunta.");

                // ha passato correttamente la richiesta allo specialista giusto?
                Assertions.assertEquals(richiestaDiModifica, comandoModificaFinto.richiestaRicevuta);
            }

            @Test
            void processa_conComandoInesistente_lanciaEccezione() {
                ControllerLibreria controller = new ControllerLibreria(new HashMap<>(), null);
                RichiestaComando richiestaInesistente = new RichiestaComando(NomeComando.RIMUOVI, new HashMap<>());

                Assertions.assertThrows(UnsupportedOperationException.class, () -> {
                    controller.processa(richiestaInesistente);
                });
            }
        }













    }







