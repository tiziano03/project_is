import java.util.*;

public class CliRunner {
    private final VistaLibreriaRC vista;
    private final ControllerLibreria controller;
    private boolean flag;
    private String input;
    private Libreria libreria;

    private static final Set<String> CAMPI_MODIFICABILI = Set.of(
            "titolo",
            "autore",
            "genere",
            "statolettura",
            "valutazione"
    );

    private static final Set<String> CAMPI_ORDINABILI = Set.of(
        "titolo",
        "autore",
        "valutazione"
    );


    public CliRunner(Libreria libreria, VistaLibreriaRC vista, ControllerLibreria controller) {
        this.vista = vista;
        this.controller = controller;
        flag = true;
        this.libreria = libreria;
    }


    public void run() {
        vista.mostraMessaggio("Benvenuto nel gestore di libreria personale!" + "\n");
        mostraComandi();
        while (flag) {
            input = vista.prendiInput().toUpperCase();
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
                    vista.mostraMessaggio("Input non riconosciuto! Riprova.");
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
        sb.append("P)resenta comandi"+"\n");
        sb.append("E)sci" + "\n");
        vista.mostraMessaggio(sb.toString());
    }


    public void aggiungi() {
        Map<String, String> mappa = new HashMap<>();
        if (!chiediValore(mappa, "titolo", "Inserisci il titolo del libro")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }
        if (!chiediValore(mappa, "autore", "Inserisci l'autore del libro")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }
        if (!chiediValore(mappa, "isbn", "Inserisci l'isbn del libro")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }


        if (!chiediValoreEnum(mappa, "genere", "Inserisci il genere del libro", Genere.class)){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }


        if (!chiediValoreEnum(mappa, "statoLettura", "Inserisci lo stato della lettura del libro", StatoLettura.class)){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }


        if (!chiediValoreEnum(mappa, "valutazione", "Inserisci una valutazione personale del libro", Valutazione.class)){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }

        ParsedInput pi= new ParsedInput("aggiungi", mappa);

        try {

            controller.processa(pi);

        }catch(UnsupportedOperationException | IllegalArgumentException | ValoreNonValidoException e){
            vista.mostraMessaggio("Errore interno");
            return;


        } catch(IsbnDuplicatoException e){
            vista.mostraMessaggio("Attenzione! E' già presente un libro con questo isbn. Vuoi sostituirlo?"+"\n"+
                    "digita \"sostituisci\" per sosituire, digita \"annulla\" per annullare l'operazione");
            String input=vista.prendiInput();
            if(input.equals("annulla")){
                vista.mostraMessaggio("Operazione annullata");
                return;
            }
            while(input!="sosituisci"){
                vista.mostraMessaggio("Input scorretto. Digita \"sostituisci\" per sostituire, digita \"annulla\" per annullare l'operazione");
                input=vista.prendiInput();
                if(input.equals("annulla")){
                    vista.mostraMessaggio("Operazione annullata");
                    return;
                }
            }
            ParsedInput pii= new ParsedInput("rimuovi", (Map)new HashMap<>().put("isbn", mappa.get("isbn")));
            controller.processa(pii);
            controller.processa(pi);
            return;
        }
        vista.mostraMessaggio("Aggiunta del libro avvenuta con successo!");
    }




    public void rimuovi() {
        Map<String, String> mappa = new HashMap<>();
        if (!chiediValore(mappa, "isbn", "Inserisci l'isbn del libro che vuoi rimuovere")) return;
        ParsedInput pi = new ParsedInput("rimuovi", mappa);

        try {
            controller.processa(pi);

        }catch(IllegalArgumentException | UnsupportedOperationException e){
            vista.mostraMessaggio("Errore interno");
            return;
        }catch(IsbnNonTrovatoException e){
            vista.mostraMessaggio("Attenzione! Non è stato trovato alcun libro di isbn "+mappa.get("isbn"));
            return;
        }

        vista.mostraMessaggio("Rimozione del libro avvenuta con successo");

    }




    public void modifica() {
        Map<String, String> mappa = new HashMap<>();

        vista.mostraMessaggio("Inserisci il codice isbn del libro da modificare (oppure digita \"annulla\" per annullare l'operazione)");
        String isbn = vista.prendiInput();
        if(isbn.equals("annulla")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }
        String campo = chiediCampo("Inserisci il campo che desideri modificare", CAMPI_MODIFICABILI);

        if(campo==null){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }
        String nuovoValore;

        switch (campo) {
            case "titolo":
            case "autore":
                // Per campi di testo semplici
                nuovoValore = chiediNuovoValoreSemplice("Inserisci il nuovo " + campo);
                break;
            case "genere":
                // Per campi basati su enum, usiamo un helper specifico
                nuovoValore = chiediNuovoValoreEnum("Scegli il nuovo genere:", Genere.class);
                break;
            case "statolettura":
                nuovoValore = chiediNuovoValoreEnum("Scegli il nuovo stato di lettura:", StatoLettura.class);
                break;
            case "valutazione":
                nuovoValore = chiediNuovoValoreEnum("Scegli la nuova valutazione:", Valutazione.class);
                break;
            default:
                // Questo caso non dovrebbe mai accadere se chiediCampo funziona bene, ma è una buona pratica
                vista.mostraMessaggio("Errore interno: campo non riconosciuto.");
                return;
        }


        if (nuovoValore == null) {
            vista.mostraMessaggio("Operazione annullata");
            return;
        }

        // 4. Assembla il ParsedInput (la "comanda" per il controller)
        mappa.put("isbn", isbn);
        mappa.put("campo", campo);
        mappa.put("valore", nuovoValore);

        ParsedInput richiesta = new ParsedInput("modifica", mappa);

        // 5. Invia la richiesta al controller e gestisci la risposta
        try {
            controller.processa(richiesta);
            // Nota: il messaggio di successo finale ("Libro modificato!") dovrebbe arrivare
            // dall'Observer Pattern quando la vista si aggiorna.
        } catch (CampoNonValidoException | ValoreNonValidoException | UnsupportedOperationException | IllegalArgumentException e) { // Idealmente qui cattureresti eccezioni specifiche (es. LibroNonTrovatoException)
            vista.mostraMessaggio("Errore interno!");
            return;
        } catch (IsbnNonTrovatoException e){
            vista.mostraMessaggio("Attenzione, non esiste alcun libro di isbn "+mappa.get("isbn"));
        }


        vista.mostraMessaggio("Modifica del libro avvenuta con successo!");
    }





    public void ordina() {
        Map<String, String> mappa = new HashMap<>();
        String campo=chiediCampo("Inserisci il campo che rappresenta il criterio in base al quale desideri ordinare la libreria", CAMPI_ORDINABILI);
        if(campo==null){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }


        mappa.put("campo", campo);

        ParsedInput pi=new ParsedInput("ordina", mappa);


        try {


            controller.processa(pi);

        }catch(IllegalArgumentException | UnsupportedOperationException | CampoNonValidoException e){
            vista.mostraMessaggio("Errore interno!");
            return;
        }

        vista.mostraMessaggio("Libreria ordinata con successo");

    }



    public void filtra(){
        Map<String, String> mappa = new HashMap<>();
        String campo = chiediCampo("Inserisci il campo per cui desideri filtrare la libreria", CAMPI_MODIFICABILI);
        String nuovoValore;

        switch (campo) {
            case "titolo":
                nuovoValore = chiediNuovoValoreSemplice("Inserisci il titolo per cui desideri filtrare la libreria");
                break;
            case "autore":
                // Per campi di testo semplici
                nuovoValore = chiediNuovoValoreSemplice("Inserisci l'autore per cui desideri filtrare la libreria");
                break;
            case "genere":
                // Per campi basati su enum, usiamo un helper specifico
                nuovoValore = chiediNuovoValoreEnum("Inserisci il genere per cui desideri filtrare la libreria", Genere.class);
                break;
            case "statolettura":
                nuovoValore = chiediNuovoValoreEnum("Inserisci lo stato di lettura per cui desideri filtrare la libreria", StatoLettura.class);
                break;
            case "valutazione":
                nuovoValore = chiediNuovoValoreEnum("Inserisci la valutazione per cui desideri filtrare la libreria", Valutazione.class);
                break;
            default:
                // Questo caso non dovrebbe mai accadere se chiediCampo funziona bene, ma è una buona pratica
                vista.mostraMessaggio("Errore interno: campo non riconosciuto.");
                return;
        }


        if (nuovoValore == null) {
            vista.mostraMessaggio("Operazione annullata");
            return;
        }

        // 4. Assembla il ParsedInput (la "comanda" per il controller)
        mappa.put("campo", campo);
        mappa.put("valore", nuovoValore);

        ParsedInput richiesta = new ParsedInput("filtra", mappa);

        // 5. Invia la richiesta al controller e gestisci la risposta
        try {
            controller.processa(richiesta);
            // Nota: il messaggio di successo finale ("Libro modificato!") dovrebbe arrivare
            // dall'Observer Pattern quando la vista si aggiorna.
        } catch (IllegalArgumentException | UnsupportedOperationException | CampoNonValidoException | ValoreNonValidoException e) { // Idealmente qui cattureresti eccezioni specifiche (es. LibroNonTrovatoException)
            vista.mostraMessaggio("Errore interno");
            return;
        }

        vista.mostraMessaggio("Libreria ordinata con successo");

    }



    public void cerca(){
        Map<String, String> mappa = new HashMap<>();
        vista.mostraMessaggio("Digita una o più parole contenute nel titolo o nel nome dell'autore di un libro per reperirlo velocemente"+
                "\n (oppure digita  \"annulla\" per annullare l'operazione");

        String termine=vista.prendiInput();

        if (termine.equals("annulla")) {
            vista.mostraMessaggio("Operazione annullata");
            return;
        }


        mappa.put("termine", termine);

        ParsedInput pi=new ParsedInput("cerca", mappa);

        try {
            controller.processa(pi);


        }catch(IllegalArgumentException | UnsupportedOperationException e) {
            vista.mostraMessaggio("Errore interno");
            return;

        }catch(PersistenceRuntimeException e){
            vista.mostraMessaggio("Errore durante il salvataggio: "+e.getMessage());
            return;
        }

        vista.mostraMessaggio("Operazione di ricerca effettuata con successo");
    }



    public void salva(){
        vista.mostraMessaggio("Sei sicuro di voler procedere con il salvataggio?"+"\n"
        + "La libreria memorizzata nel file attuale verrà sovrascritta."+"\n"+
                "Digita \"conferma\" per confermare, oppure digita \"annulla\" per annullare");
        String input=vista.prendiInput();
        if(input.equals("annulla")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }

        while(!input.equals("conferma")){
            vista.mostraMessaggio("Input scorretto. Digita \"conferma\" per confermare, oppure \"annulla\" per annullare.");
            input = vista.prendiInput();
            if(input.equals("annulla")){
                vista.mostraMessaggio("Operazione annullata");
                return;
            }

            ParsedInput pi=new ParsedInput("salva");

            try{

                controller.processa(pi);


        }catch(IllegalArgumentException | UnsupportedOperationException e) {
                vista.mostraMessaggio("Errore interno");
                return;
            }catch(PersistenceRuntimeException e){
                vista.mostraMessaggio("Errore durante il salvataggio: "+e.getMessage());
                return;
            }

            vista.mostraMessaggio("Operazione di salvataggio effettuata con successo");

        }



    }




    public void carica(){
        vista.mostraMessaggio("Sei sicuro di voler procedere con il caricamento?"+"\n"+
                "Digita \"conferma\" per confermare, oppure \"annulla\" per annullare");
        String input=vista.prendiInput();
        if(input.equals("annulla")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }
        while(!input.equals("conferma")){
            vista.mostraMessaggio("Input scorretto. Digita \"conferma\" per confermare, oppure \"annulla\" per annullare");
            input = vista.prendiInput();
            if(input.equals("annulla")){
                vista.mostraMessaggio("Operazione annullata");
                return;
            }
        }

        ParsedInput pi=new ParsedInput("carica");

        try {
            controller.processa(pi);
        }catch(IllegalArgumentException | UnsupportedOperationException e) {
            vista.mostraMessaggio("Errore interno");
            return;
        }catch(PersistenceRuntimeException e){
            vista.mostraMessaggio("Errore durante il salvataggio: "+e.getMessage());
            return;
        }

        vista.mostraMessaggio("Caricamento della libreria avvenuto con successo");

        }




    public void esci(){
        vista.mostraMessaggio("Sei sicuro di voler chiudere l'applicazione?"+"\n"+
                "Se non hai effettuato il salvataggio le tue ultime modifiche andranno perse!"+"\n"+
                "Digita \"conferma\" per confermare, oppure \"annulla\" per annullare l'operazione");

        String input= vista.prendiInput();
        if(input.equals("annulla")){
            vista.mostraMessaggio("Operazione annullata");
            return;
        }

        while(!input.equals("conferma")){
            vista.mostraMessaggio("Input scorretto. Digita \"conferma\" per confermare, oppure \"annulla\" per annullare");
            input= vista.prendiInput();
            if(input.equals("annulla")){
                vista.mostraMessaggio("Operazione annullata");
                return;
            }
        }

        this.flag=false;

        vista.mostraMessaggio("Bye!");


    }







    // --- METODI HELPER ---

    private String chiediCampo(String messaggioPrompt, Set<String> set) {
        vista.mostraMessaggio(messaggioPrompt+ " (oppure digita \"annulla\" per annullare l'operazione)");
        vista.mostraMessaggio("Campi disponibili: " + set.toString());

        while (true) {
            String inputUtente = vista.prendiInput().toLowerCase();
            if (inputUtente.equals("annulla")) {
                return null;
            }
            if (set.contains(inputUtente)) {
                return inputUtente;
            } else {
                vista.mostraMessaggio("\"" + inputUtente + "\" non è un campo valido. Riprova.");
            }
        }
    }



    private String chiediNuovoValoreSemplice(String messaggioPrompt) {
        vista.mostraMessaggio(messaggioPrompt + " (o digita \"annulla\" per annullare)");
        String input = vista.prendiInput();
        if (input.equalsIgnoreCase("annulla")){
            return null;
    }
        return input;
    }




    private <T extends Enum<T>> String chiediNuovoValoreEnum(String messaggioPrompt, Class<T> enumClass) {
        vista.mostraMessaggio(messaggioPrompt+ " (o digita \"annulla\" per annullare)");
        vista.mostraMessaggio("Valori ammissibili: " + Arrays.toString(enumClass.getEnumConstants()));

        while (true) {
            String input = vista.prendiInput();
            if (input.equalsIgnoreCase("annulla")) {
                return null;
            }
            // Controlliamo se l'input è un valore valido per l'enum dato
            for (T enumValue : enumClass.getEnumConstants()) {
                if (enumValue.name().equalsIgnoreCase(input)) {
                    return input; // Restituisce la stringa del valore valido
                }
            }
            vista.mostraMessaggio("\"" + input + "\" non è un valore ammissibile. Riprova.");
        }
    }


    private boolean chiediValore(Map<String, String> mappa, String nomeCampo, String messaggio) {
        vista.mostraMessaggio(messaggio + " (oppure digita \"annulla\" per annullare)");
        String input = vista.prendiInput();
        if (input.equalsIgnoreCase("annulla")) {
            return false;
        }
        mappa.put(nomeCampo, input);
        return true;
    }

    /**
     * Chiede all'utente di scegliere un valore da un enum, lo valida e lo aggiunge alla mappa.
     *
     * @return false se l'utente ha annullato, true altrimenti.
     */
    private <T extends Enum<T>> boolean chiediValoreEnum(Map<String, String> mappa, String nomeCampo, String messaggio, Class<T> enumClass) {
        vista.mostraMessaggio(messaggio + " (oppure digita \"annulla\" per annullare)");
        vista.mostraMessaggio("Valori ammissibili: " + Arrays.toString(enumClass.getEnumConstants()));

        while (true) {
            String input = vista.prendiInput();
            if (input.equalsIgnoreCase("annulla")) {
                return false;
            }

            if (isValidEnumValue(input, enumClass)) {
                mappa.put(nomeCampo, input);
                return true;
            } else {
                vista.mostraMessaggio("Attenzione: \""+input+"\" non è un valore ammissibile. Riprova.");
            }
        }
    }


    /**
     * Controlla se una stringa corrisponde a uno dei valori di un enum.
     */
    private <T extends Enum<T>> boolean isValidEnumValue(String input, Class<T> enumClass) {
        for (T enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Map<String, Command> mappaComandi = new HashMap<String, Command>();

        GestorePersistenza gestorePersistenza = new GestorePersistenza(new StrategiaPersistenzaJson());

        Libreria libreria = Libreria.getInstance();

        VistaLibreriaRC vistaLibreria = new VistaLibreriaRC();

        String path = "LibreriaPersonale";

        libreria.aggiungiAscoltatore(vistaLibreria);


        mappaComandi.put("carica", new ComandoCaricamento(gestorePersistenza, libreria, path, vistaLibreria));
        mappaComandi.put("filtra", new ComandoFiltra(libreria, vistaLibreria));
        mappaComandi.put("aggiungi", new ComandoInserimento(libreria, vistaLibreria));
        mappaComandi.put("modifica", new ComandoModifica(libreria, vistaLibreria));
        mappaComandi.put("ordina", new ComandoOrdina(libreria, vistaLibreria));
        mappaComandi.put("rimuovi", new ComandoRimozione(libreria, vistaLibreria));
        mappaComandi.put("salva", new ComandoSalvataggio(gestorePersistenza, libreria, vistaLibreria, path));
        mappaComandi.put("cerca", new ComandoCerca(vistaLibreria, libreria));


        ControllerLibreria controller = new ControllerLibreria(mappaComandi);

        CliRunner cr=new CliRunner(libreria, vistaLibreria, controller);

        cr.run();

    }

}











