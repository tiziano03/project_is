import CoreApplication.ControllerLibreria;
import CoreApplication.NomeComando;
import CoreApplication.NomeParametro;
import CoreApplication.RichiestaComando;
import Model.*;
import Presenter.DialogHelper;
import Presenter.VistaLibreriaRC;
import Presenter.Wizard;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class VistaStub implements VistaLibreriaRC {
    private final Queue<String> inputsDaFornire = new LinkedList<>();
    private final List<String> messaggiMostrati = new ArrayList<>();
    private final List<String> erroriMostrati = new ArrayList<>();

    // Metodo per programmare una sequenza di input dell'utente
    public void preparaInput(String... inputs) {
        inputsDaFornire.addAll(Arrays.asList(inputs));
    }

    // Sovrascriviamo i metodi di I/O
    @Override
    public String prendiInput() {
        if (inputsDaFornire.isEmpty()) {
            fail("Il test ha cercato di leggere più input di quanti ne siano stati forniti.");
        }
        return inputsDaFornire.poll();
    }

    @Override
    public void mostraMessaggioQuery(String messaggio) {

    }

    @Override
    public void mostraMenu(String messaggio) {

    }


    @Override
    public void mostraMessaggio(String messaggio) {
        messaggiMostrati.add(messaggio);
    }

    @Override
    public void mostraMessaggioSuccesso(String messaggio) {

    }

    @Override
    public void mostraMessaggioErrore(String errore) {
        erroriMostrati.add(errore);
    }

    public List<String> getErroriMostrati(){
        return erroriMostrati;
    }


    // Metodo per verificare l'output nei test
    public String ultimoMessaggio() {
        return messaggiMostrati.isEmpty() ? "" : messaggiMostrati.get(messaggiMostrati.size() - 1);
    }

    @Override
    public void update(List<LibroDTO> libri) {

    }
}


class ControllerStub extends ControllerLibreria {
    RichiestaComando richiestaProcessata = null;
    List<LibroDTO> risultatiQuery = new ArrayList<>();

    public ControllerStub() { super(null, null); /* Costruttore finto */ }

    @Override
    public void processa(RichiestaComando richiesta) {
        this.richiestaProcessata = richiesta;
    }

    @Override
    public List<LibroDTO> Query(CampoLibro campo, String valore) {
        // Restituisce una lista pre-programmata per simulare una ricerca
        return risultatiQuery;
    }
}


//Inizia la classe di test principale

@DisplayName("Test dello Strato di Presentazione (UI)")
public class TestUI {

    private VistaStub vistaFinta;

    @BeforeEach
    void setUp() {
        // Una vista finta nuova per ogni test
        vistaFinta = new VistaStub();
    }

    @Nested
    @DisplayName("Test del DialogHelper (Utility di Dialogo)")
    class DialogHelperTest {

        private DialogHelper dialogHelper;

        @BeforeEach
        void setUp() {
            dialogHelper = new DialogHelper(vistaFinta);
        }

        @Test
        @DisplayName("haConfermato() restituisce true quando l'utente digita 'conferma'")
        void haConfermato_conInputConferma_restituisceTrue() {
            vistaFinta.preparaInput("conferma");


            boolean risultato = dialogHelper.haConfermato();


            assertTrue(risultato);
        }

        @Test
        @DisplayName("haConfermato() restituisce false quando l'utente digita 'annulla'")
        void haConfermato_conInputAnnulla_restituisceFalse() {
            vistaFinta.preparaInput("annulla");

            boolean risultato = dialogHelper.haConfermato();

            assertFalse(risultato);
        }

        @Test
        @DisplayName("chiediValoreObbligatorio() continua a chiedere finché non riceve un input non vuoto")
        void chiediValoreObbligatorio_gestisceInputVuoto() {

            vistaFinta.preparaInput("", "Testo Valido");


            String risultato = dialogHelper.chiediValoreObbligatorio();


            Assertions.assertEquals("Testo Valido", risultato);
            // Verifichiamo che sia stato mostrato un messaggio di errore
            Assertions.assertEquals(1, vistaFinta.getErroriMostrati().size());
            Assertions.assertTrue(vistaFinta.getErroriMostrati().get(0).contains("L'input non può essere vuoto"));
        }
    }


    @Nested
    @DisplayName("Test del Wizard (Orchestrazione)")
    class WizardTest {

        private ControllerStub controllerFinto;
        private Wizard wizard;

        @BeforeEach
        void setUp() {
            controllerFinto = new ControllerStub();
            wizard = new Wizard(vistaFinta, controllerFinto);
        }

        @Test
        @DisplayName("aggiungi() orchestra il dialogo e invia la RichiestaComando corretta")
        void aggiungi_happyPath_inviaRichiestaCorretta() {
            String titolo = "Moby Dick";
            String autore = "Herman Melville";
            String isbn = "9788804668237";
            String genere = Genere.NARRATIVA.name();
            String stato = StatoLettura.LETTO.name();
            String valutazione = Valutazione.CINQUE_STELLE.name();

            // Programmiamo l'intera conversazione dell'utente
            vistaFinta.preparaInput(titolo, autore, isbn, genere, stato, valutazione);


            wizard.aggiungi();


            assertNotNull(controllerFinto.richiestaProcessata, "Il metodo processa() del controller doveva essere chiamato.");

            RichiestaComando richiesta = controllerFinto.richiestaProcessata;
            assertEquals(NomeComando.AGGIUNGI, richiesta.getNomeComando());
            assertEquals(titolo, richiesta.getArgomenti().get(NomeParametro.TITOLO));
            assertEquals(autore, richiesta.getArgomenti().get(NomeParametro.AUTORE));
            assertEquals(isbn, richiesta.getArgomenti().get(NomeParametro.ISBN));
        }

        @Test
        @DisplayName("selezionaId() gestisce correttamente la selezione da una lista")
        void selezionaId_conRisultatiMultipli_restituisceIdCorretto() {

            Libro l1=new Libro(Isbn.factory(""),"Libro A", "Autore A",
                    Valutazione.getDefault(),Genere.getDefault(), StatoLettura.getDefault());
            Libro l2=new Libro(Isbn.factory(""),"Libro B", "Autore B",
                    Valutazione.getDefault(),Genere.getDefault(), StatoLettura.getDefault());

            LibroDTO ld1 = new LibroDTO(l1);
            LibroDTO ld2 = new LibroDTO(l2);

            UUID idAtteso=ld2.getId();

            // Programmiamo il controller finto per restituire questi DTO
            controllerFinto.risultatiQuery = Arrays.asList(ld1, ld2);

            // Programmiamo l'utente che prima cerca "Libro" e poi sceglie il numero "2"
            vistaFinta.preparaInput("Libro", "2");

            String idSelezionato = wizard.selezionaId();


            assertEquals(idAtteso.toString(), idSelezionato);
        }
    }
}