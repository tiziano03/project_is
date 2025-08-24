import java.util.*;

public class Wizard {
    private final VistaLibreriaRC vista;
    private final ControllerLibreria controller;
    private boolean flag;
    private DialogHelper helper;


    private static final Set<String> CAMPI_ORDINABILI = CampoLibro.getCampiOrdinabili();


    private static final Set<String> CAMPI_FILTRABILI = CampoLibro.getCampiFiltrabili();


    private static final Set<String> CAMPI_MODIFICABILI = CampoLibro.getCampiModificabili();


    public Wizard(VistaLibreriaRC vista, ControllerLibreria controller) {
        this.vista = vista;
        this.controller = controller;
        flag = true;
        this.helper = new DialogHelper(vista);
    }


    public void run() {
        vista.mostraMessaggio("Benvenuto nel gestore di libreria personale!" + "\n");
        mostraComandi();
        while (flag) {
            vista.mostraMessaggio("");
            String input = vista.prendiInput().toUpperCase();
            switch (input) {
                case "A":
                    aggiungi();
                    break;
                case "M":
                    modifica();
                    break;
                case "R":
                    rimuovi();
                    break;
                case "O":
                    ordina();
                    break;
                case "F":
                    filtra();
                    break;
                case "T":
                    cerca();
                    break;
                case "C":
                    carica();
                    break;
                case "P":
                    mostraComandi();
                    break;
                case "S":
                    salva();
                    break;
                case "E":
                    esci();
                    break;
                default:
                    vista.mostraMessaggioErrore("Input non riconosciuto! Riprova.");
                    break;

            }

        }

    }


    public void mostraComandi() {
        StringBuilder sb = new StringBuilder();
        sb.append("A)ggiungi un libro alla tua collezione" + "\n");
        sb.append("M)odifica un libro della tua collezione" + "\n");
        sb.append("R)imuovi un libro dalla tua collezione" + "\n");
        sb.append("O)rdina la tua libreria" + "\n");
        sb.append("F)iltra la libreria, mostrando i libri che soddisfano un particolare criterio" + "\n");
        sb.append("T)rova libro per titolo/autore" + "\n");
        sb.append("C)arica libreria da file" + "\n");
        sb.append("S)alva libreria su file" + "\n");
        sb.append("P)resenta comandi" + "\n");
        sb.append("E)sci" + "\n");
        vista.mostraMenu(sb.toString());
    }


    public void aggiungi() {
        Map<NomeParametro, String> mappa = new HashMap<>();
        vista.mostraMessaggio("-----Aggiunta libro-----");
        vista.mostraMessaggio("Digita annulla in qualsiasi momento per annullare l'operazione" + "\n");

        vista.mostraMessaggio("Inserisci il titolo del libro (obbligatorio)");
        String titolo = helper.chiediValoreObbligatorio();
        if (titolo == null) return;

        vista.mostraMessaggio("Inserisci l'autore del libro (obbligatorio)");
        String autore = helper.chiediValoreObbligatorio();
        if (autore == null) return;

        String isbn;
        vista.mostraMessaggio("Inserisci l'isbn del libro da 13 cifre (opzionale: digita invio per saltare)");
        while (true) {
            isbn = helper.chiediValoreOpzionale("");
            if (isbn == null) return;
            if (helper.isbnValido(isbn)) break;
            vista.mostraMessaggioErrore("Attenzione. Input scorretto, riprova.");
        }


        vista.mostraMessaggio("Inserisci il genere del libro (opzionale: digita invio per saltare)");
        String genere = helper.chiediValoreEnumOpzionale(Genere.class, Genere.getDefault().name());
        if (genere == null) return;


        vista.mostraMessaggio("Inserisci lo stato della lettura del libro (opzionale: digita invio per saltare)");
        String statoLettura = helper.chiediValoreEnumOpzionale(StatoLettura.class, StatoLettura.getDefault().name());
        if (statoLettura == null) return;

        vista.mostraMessaggio("Inserisci una valutazione personale del libro (opzionale: digita invio per saltare)");
        String valutazione = helper.chiediValoreEnumOpzionale(Valutazione.class, Valutazione.getDefault().name());
        if (valutazione == null) return;

        mappa.put(NomeParametro.TITOLO, titolo);
        mappa.put(NomeParametro.AUTORE, autore);
        mappa.put(NomeParametro.ISBN, isbn);
        mappa.put(NomeParametro.GENERE, genere);
        mappa.put(NomeParametro.STATOLETTURA, statoLettura);
        mappa.put(NomeParametro.VALUTAZIONE, valutazione);

        RichiestaComando rc = new RichiestaComando(NomeComando.AGGIUNGI, mappa);


        try {
            controller.processa(rc);
        } catch (IllegalArgumentException | UnsupportedOperationException | ValoreNonValidoException e) {
            vista.mostraMessaggioErrore("Errore interno");
            return;
        }

        vista.mostraMessaggioSuccesso("Libro aggiunto con successo");

    }


    public void rimuovi() {
        vista.mostraMessaggio("-----RIMUOVI LIBRO-----");
        vista.mostraMessaggio("Digita \"annulla\" in qualsiasi momento per annullare l'operazione");
        vista.mostraMessaggio("Cerca il libro che vuoi rimuovere digitando un termine presente nel titolo o nel nome dell'autore");

        String id = selezionaId();
        if (id == null) return;

        Map<NomeParametro, String> mappa = new HashMap<>();
        mappa.put(NomeParametro.UUID, id);
        RichiestaComando rc = new RichiestaComando(NomeComando.RIMUOVI, mappa);

        try {
            controller.processa(rc);

        } catch (IllegalArgumentException | UnsupportedOperationException | IdNonTrovatoException e) {
            vista.mostraMessaggioErrore("Errore interno");
            return;
        }

        vista.mostraMessaggioSuccesso("Rimozione del libro avvenuta con successo");

    }


    public void modifica() {
        vista.mostraMessaggio("-----MODIFICA LIBRO-----");
        vista.mostraMessaggio("Digita \"annulla\" in qualsiasi momento per annullare l'operazione");
        vista.mostraMessaggio("Cerca il libro che vuoi modificare digitando un termine presente nel titolo o nel nome dell'autore");

        String id = selezionaId();
        if (id == null) return;

        vista.mostraMessaggio("Scegli il campo che desideri modificare");
        CampoLibro campo = helper.prendiCampo(CAMPI_MODIFICABILI);
        if (campo == null) return;


        String valore = null;
        switch (campo) {
            case TITOLO: {
                vista.mostraMessaggio("Inserisci il nuovo titolo");
                valore = helper.chiediValoreObbligatorio();
                break;
            }

            case AUTORE: {
                vista.mostraMessaggio("Inserisci il nuovo autore");
                valore = helper.chiediValoreObbligatorio();
                break;
            }

            case ISBN: {
                vista.mostraMessaggio("Inserisci il nuovo isbn");
                while (true) {
                    valore = helper.chiediValoreObbligatorio();
                    if (valore == null) return;
                    if (helper.isbnValido(valore)) break;
                    vista.mostraMessaggioErrore("Attenzione, isbn non valido: è composto da 13 cifre (trattini esclusi)");
                }
                break;
            }
            case GENERE: {
                vista.mostraMessaggio("Inserisci il nuovo genere");
                valore = helper.chiediValoreEnumObbligatorio(Genere.class);
                break;
            }
            case STATOLETTURA: {
                vista.mostraMessaggio("Inserisci il nuovo stato della lettura");
                valore = helper.chiediValoreEnumObbligatorio(StatoLettura.class);
                break;
            }
            case VALUTAZIONE: {
                vista.mostraMessaggio("Inserisci la tua nuova valutazione personale");
                valore = helper.chiediValoreEnumObbligatorio(Valutazione.class);
                break;
            }
        }


        if (valore == null) return;

        Map<NomeParametro, String> mappa = new HashMap<>();
        mappa.put(NomeParametro.UUID, id);
        mappa.put(NomeParametro.CAMPO, campo.toString());
        mappa.put(NomeParametro.VALORE, valore);

        RichiestaComando rc = new RichiestaComando(NomeComando.MODIFICA, mappa);


        try {

            controller.processa(rc);

        } catch (IllegalArgumentException | UnsupportedOperationException | IdNonTrovatoException |
                 ValoreNonValidoException | CampoNonValidoException e) {

            vista.mostraMessaggioErrore("Errore interno");
            return;
        }

        vista.mostraMessaggioSuccesso("Modifica avvenuta con successo!");


    }


    public void ordina() {
        vista.mostraMessaggio("-----ORDINA LIBRERIA-----");
        vista.mostraMessaggio("Digita \"annulla\" in qualsiasi momento per annullare l'operazione");
        vista.mostraMessaggio("Digita il campo in base al quale desideri riordinare la libreria");
        CampoLibro campo = helper.prendiCampo(CAMPI_ORDINABILI);

        if (campo == null) return;

        Map<NomeParametro, String> mappa = new HashMap<>();
        mappa.put(NomeParametro.CAMPO, campo.toString());


        RichiestaComando rc = new RichiestaComando(NomeComando.ORDINA, mappa);

        try {

            controller.processa(rc);

        } catch (IllegalArgumentException | UnsupportedOperationException | CampoNonValidoException e) {
            vista.mostraMessaggioErrore("Errore interno!");
            return;
        }

        vista.mostraMessaggioSuccesso("Libreria ordinata con successo");

    }


    public void filtra() {
        vista.mostraMessaggio("-----FILTRA LIBRERIA-----");
        vista.mostraMessaggio("Digita \"annulla\" in qualsiasi momento per annullare l'operazione");
        vista.mostraMessaggio("Inserisci il campo per cui desideri filtrare la tua libreria");

        CampoLibro campo = helper.prendiCampo(CAMPI_FILTRABILI);
        if (campo == null) return;

        String valore = null;
        switch (campo) {
            case TITOLO: {
                vista.mostraMessaggio("Inserisci un termine racchiuso nel titolo dei libri che vuoi filtrare");
                valore = helper.chiediValoreObbligatorio();
                if (valore == null) return;
                break;
            }

            case AUTORE: {
                vista.mostraMessaggio("Inserisci un termine presente nel nome dell'autore dei libri che vuoi filtrare");
                valore = helper.chiediValoreObbligatorio();
                if (valore == null) return;
                break;
            }

            case GENERE: {
                vista.mostraMessaggio("Inserisci il genere dei libri che vuoi filtrare");
                valore = helper.chiediValoreEnumObbligatorio(Genere.class);
                if (valore == null) return;
                break;
            }

            case STATOLETTURA: {
                vista.mostraMessaggio("Inserisci lo stato della lettura dei libri che vuoi filtrare");
                valore = helper.chiediValoreEnumObbligatorio(StatoLettura.class);
                if (valore == null) return;
                break;
            }

            case VALUTAZIONE: {
                vista.mostraMessaggio("Inserisci la valutazione dei libri che vuoi filtrare");
                valore = helper.chiediValoreEnumObbligatorio(Valutazione.class);
                if (valore == null) return;
                break;
            }

        }

        List<LibroDTO> libri = controller.Query(campo, valore);


        if (libri.isEmpty()) {
            vista.mostraMessaggio("La ricerca con filtro non ha avuto risultati");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Risultato della ricerca con filtro:" + "\n");
        int i = 0;
        for (LibroDTO l : libri) sb.append(i++ + ")" + l + "\n");
        vista.mostraMessaggioQuery(sb.toString());
        vista.mostraMessaggioSuccesso("Ricerca con filtro effettuata con successo");
    }


    public void cerca() {
        vista.mostraMessaggio("-----CERCA LIBRO-----");
        vista.mostraMessaggio("Digita \"annulla\" in qualsiasi momento per annullare l'operazione");

        List<LibroDTO> libri = cercaHelper();
        if (libri == null) return;

        if (libri.isEmpty()) {
            vista.mostraMessaggio("L'operazione di ricerca non ha avuto risultati");
            return;
        }


        StringBuilder sb = new StringBuilder();
        sb.append("Risultato della ricerca:" + "\n");
        int i = 0;
        for (LibroDTO l : libri) sb.append(i++ + ")" + l + "\n");
        vista.mostraMessaggioQuery(sb.toString());
        vista.mostraMessaggioSuccesso("Ricerca effettuata con successo");

    }


    public void salva() {
        vista.mostraMessaggio("-----SALVA LIBRERIA-----");
        vista.mostraMessaggio("Sei sicuro di voler procedere con il salvataggio?" + "\n"
                + "La libreria memorizzata nel file attuale verrà sovrascritta.");

        if (!helper.haConfermato()) return;

        RichiestaComando pi = new RichiestaComando(NomeComando.SALVA);

        try {

            controller.processa(pi);

        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            vista.mostraMessaggioErrore("Errore interno");
            return;
        } catch (PersistenceRuntimeException e) {
            vista.mostraMessaggioErrore("Errore durante il salvataggio: " + e.getMessage());
            return;
        }

        vista.mostraMessaggioSuccesso("Operazione di salvataggio effettuata con successo");
    }


    public void carica() {
        vista.mostraMessaggio("-----CARICA LIBRERIA-----");
        vista.mostraMessaggio("Sei sicuro di voler procedere con il caricamento?");

        if (!helper.haConfermato()) return;

        RichiestaComando pi = new RichiestaComando(NomeComando.CARICA);

        try {
            controller.processa(pi);

        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            vista.mostraMessaggioErrore("Errore interno");
            return;

        } catch (PersistenceRuntimeException e) {
            vista.mostraMessaggioErrore("Errore durante il salvataggio: " + e.getMessage());
            return;
        }

        vista.mostraMessaggioSuccesso("Caricamento della libreria avvenuto con successo");

    }


    public void esci() {
        vista.mostraMessaggio("Sei sicuro di voler chiudere l'applicazione?" + "\n" +
                "Se non hai effettuato il salvataggio le tue ultime modifiche andranno perse!");

        if (!helper.haConfermato()) return;

        this.flag = false;
        vista.mostraMessaggio("Bye!");
    }


    public List<LibroDTO> cercaHelper() {
        String termine = vista.prendiInput();
        while (termine.isEmpty()) {
            vista.mostraMessaggioErrore("Input vuoto non valido. Riprova");
            termine = vista.prendiInput();
        }

        if (termine.equalsIgnoreCase("annulla")) {
            vista.mostraMessaggio("Operazione annullata");
            return null;
        }

        List<LibroDTO> libri = controller.Query(CampoLibro.TITOLO, termine);
        List<LibroDTO> libri2 = controller.Query(CampoLibro.AUTORE, termine);

        for (LibroDTO l : libri2)
            if (!libri.contains(l))
                libri.add(l);

        return libri;
    }


    public String selezionaId() {
        List<LibroDTO> libri = cercaHelper();
        if (libri == null) return null;
        if (libri.isEmpty()) {
            vista.mostraMessaggio("Non esiste nessun libro con queste caratteristiche!");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Risultato della ricerca: " + "\n");
        int i = 1;
        for (LibroDTO l : libri) sb.append(i++ + ")" + l + "\n");
        vista.mostraMessaggio(sb.toString());

        UUID id;
        if (libri.size() == 1) id = libri.get(0).getId();

        else {
            vista.mostraMessaggio("Digita un numero da 1 a " + libri.size() + " per specificare il libro da modificare" +
                    " oppure digita \"0\" per annullare l'operazione");
            int numero = helper.prendiNumeroIntero();
            while (!(numero >= 0 && numero <= libri.size())) {
                vista.mostraMessaggioErrore("Input scorretto. Riprova");
                numero = helper.prendiNumeroIntero();
            }
            if (numero == 0) {
                vista.mostraMessaggio("Operazione annullata");
                return null;
            }
            id = libri.get(numero - 1).getId();
        }
        return id.toString();
    }


}



