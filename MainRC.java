import java.util.HashMap;
import java.util.Map;

public class MainRC {

    public static void main(String[] args) {

        Libreria libreria = Libreria.getInstance();
        VistaLibreriaRC1 vistaLibreria = new VistaLibreriaRC1();
        GestorePersistenza gestorePersistenza = new GestorePersistenza(new StrategiaPersistenzaJson());

        String path = "LibreriaPersonale";

        StringBuilder sb=new StringBuilder();
        sb.append("L'applicazione consente di gestire una collezione personali di libri, memorizzandone " +
                "isbn, titolo, autore, genere, statoLettura, valutazione. \n");
        sb.append("Comandi disponibili:\n");
        sb.append("Per aggiungere un libro: aggiungi <isbn> <titolo> <autore> <genere> <statoLettura> <valutazione> \n");
        sb.append("Per rimuovere un libro: rimuovi --isbn <isbn> \n");
        sb.append("Per modificare un libro: modifica --isbn <isbn> --campo <campo> --valore <valore> \n");
        sb.append("Per filtrare i libri della collezione che soddisfano una condizione: filtra --campo <campo> --valore <valore> \n");
        sb.append("Per ricercare un libro o un insieme di libri: cerca <termine> \n");
        sb.append("Per ordinare i libri secondo un particolare criterio: ordina --campo <campo>"+"\n");
        sb.append("Per caricare i libri da memoria persistente: carica \n");
        sb.append("Per salvare i libri su memoria persistente: salva \n");
        sb.append("Per uscire dall'applicazione: esci \n \n");
        sb.append("Valori ammissibili per il campo genere: tecnico, saggistica, narrativa, infanzia, generali, biografia \n");
        sb.append("Valori ammissibili per il campo statoLettura: letto, da_leggere, lettura_in_corso \n");
        sb.append("Valori ammissibili per il campo valutazione: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle, non_disponibile \n \n");
        sb.append("I campi per cui è concesso l'ordinamento sono: titolo, autore, valutazione \n\n");
        sb.append("Attenzione: inserisci tra virgolette i valori che contengono più parole, ad esempio, per aggiungere il libro \"Il signore degli anelli\", digita: \n" );
        sb.append("aggiungi 9788845292613 \"Il signore degli anelli\" \"J.R.R Tolkien\" narrativa, lettura_in_corso cinque_stelle");
        sb.append("\nBuona continuazione!\n");

        String messaggioAiuto=sb.toString();






        //titolo, valutazione,
        Map<String, Command> mappaComandi = new HashMap<>();
        mappaComandi.put("aiuto", new ComandoAiuto(vistaLibreria, messaggioAiuto));
        mappaComandi.put("carica", new ComandoCaricamento(gestorePersistenza, libreria, path, vistaLibreria));
        mappaComandi.put("esci", new ComandoEsci(vistaLibreria));
        mappaComandi.put("filtra", new ComandoFiltra(libreria, vistaLibreria));
        mappaComandi.put("aggiungi", new ComandoInserimento(libreria, vistaLibreria));
        mappaComandi.put("modifica", new ComandoModifica(libreria, vistaLibreria));
        mappaComandi.put("ordina", new ComandoOrdina(libreria, vistaLibreria));
        mappaComandi.put("rimuovi", new ComandoRimozione(libreria, vistaLibreria));
        mappaComandi.put("salva", new ComandoSalvataggio(gestorePersistenza, libreria, vistaLibreria, path));
        mappaComandi.put("cerca", new ComandoCerca(vistaLibreria,libreria));




        Command defaultCommand = new ComandoNonTrovato(vistaLibreria);

        ControllerLibreria controllerLibreria = new ControllerLibreria(mappaComandi, defaultCommand);


        InputParser inputParser = new InputParser1();


        libreria.aggiungiAscoltatore(vistaLibreria);


        boolean flag = true;

        vistaLibreria.mostraMessaggio("Benvenuto/a nel gestore di libreria personale!" + "\n" +
                "Per informazioni sui comandi e sulla relativa sintassi digita: aiuto");
        while( flag ) {
            try{
            String input = vistaLibreria.prendiInput();
            ParsedInput parsedInput = inputParser.parse(input);

                flag = controllerLibreria.processa(parsedInput);
            }catch(SyntaxException e){vistaLibreria.mostraMessaggio("Input non valido!");}

            catch(PersistenceException e){
                vistaLibreria.mostraMessaggio("Errore di salvataggio/caricamento: " + e.getMessage());
            }
            catch(SemanticException e){
                vistaLibreria.mostraMessaggio("Input non valido!"+"\n"+e.getMessage());
            }



        }

    }
}