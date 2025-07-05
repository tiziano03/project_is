public class ComandoAiuto implements Command{
    private final VistaLibreria vistaLibreria;

    public ComandoAiuto(VistaLibreria vistaLibreria) {
        this.vistaLibreria = vistaLibreria;
    }

    @Override
    public void execute(ParsedInput input) {
        if (!input.getArgomentiNominali().isEmpty() || input.getArgomentiPosizionali().size() > 1)
            throw new IllegalArgumentException();
        if (input.getArgomentiPosizionali().isEmpty())
            aiutoGenerale();
        else {
            String argomento = input.getArgomentiPosizionali().get(0);

            switch (argomento) {
                case "filtra":
                    aiutoFiltra();
                    break;
                case "aggiungi":
                    aiutoAggiungi();
                    break;
                case "modifica":
                    aiutoModifica();
                    break;
                case "ordina":
                    aiutoOrdina();
                    break;
                case "rimuovi":
                    aiutoRimuovi();
                    break;
                default:
                    throw new IllegalArgumentException();

            }

        }
    }




    public void aiutoGenerale(){
        StringBuilder sb=new StringBuilder();
        sb.append("comandi disponibili:"+"\n");
        sb.append("per aggiungere un libro:<aggiungi> isbn 'titolo' 'autore' genere statoLettura valutazione"+"\n");
        sb.append("per rimuovere un libro:<rimuovi> --<isbn> isbn"+"\n");
        sb.append("per modificare un libro:<modifica> --<isbn> isbn --<campo> campo --<valore> valore"+"\n");
        sb.append("per effettuare ricerche con filtro:<filtra> --<campo> campo --<valore> valore"+"\n");
        sb.append("per ordinare la libreria secondo un criterio:<ordina> campo"+"\n");
        sb.append("per salvare:<salva>"+"\n");
        sb.append("per caricare:<carica>"+"\n");
        sb.append("per uscire:<esci>"+"\n");
        sb.append("per avere informazioni più dettagliate su un particolare comando:<aiuto> comando"+"\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("valori concessi per il genere: narrativa, saggistica, tecnico, biografia, infanzia, generici"+"\n");
        sb.append("valori concessi per lo statoLettura: da_leggere, lettura_in_corso, letto");
        sb.append("valori concessi per la valutazione: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle"+"\n");
        sb.append("Buona continuazione!");
        String messaggio=sb.toString();
        vistaLibreria.mostraMessaggio(messaggio);
    }




    public void aiutoAggiungi(){
        StringBuilder sb=new StringBuilder();
        sb.append("Per aggiungere un libro alla tua collezione segui la sintassi:"+"\n");
        sb.append("<aggiungi> isbn 'titolo' 'autore' genere statoLettura valutazione"+"\n");
        sb.append("esempio:"+"\n");
        sb.append("aggiungi 978-8871921501 'Design Patterns' 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides' tecnico lettura_in_corso cinque_stelle");
        String messaggio=sb.toString();
        vistaLibreria.mostraMessaggio(messaggio);
    }




    public void aiutoRimuovi(){
        StringBuilder sb=new StringBuilder();
        sb.append("Per rimuovere un libro dalla tua collezione segui la sintassi:"+"\n");
        sb.append("<rimuovi> isbn"+"\n");
        sb.append("esempio:"+"\n");
        sb.append("rimuovi 978-8871921501");
        String messaggio=sb.toString();
        vistaLibreria.mostraMessaggio(messaggio);
    }




    public void aiutoModifica(){
        StringBuilder sb=new StringBuilder();
        sb.append("Per modificare un libro dalla tua collezione segui la sintassi:"+"\n");
        sb.append("<modifica> --<isbn> isbn --<campo> campo --<valore> valore"+"\n");
        sb.append("esempio:"+"\n");
        sb.append("modifica --isbn 978-8871921501 --campo valutazione --quattro_stelle");
        String messaggio=sb.toString();
        vistaLibreria.mostraMessaggio(messaggio);
    }




    public void aiutoOrdina(){
        StringBuilder sb=new StringBuilder();
        sb.append("Per ordinare la libreria secondo un criterio segui la seguente sintassi:"+"\n");
        sb.append("<ordina> campo"+"\n");
        sb.append("esempio:"+"\n");
        sb.append("ordina valutazione"+"\n");
        sb.append("campi per cui è possibile l'ordinamento: titolo, autore, valutazione");
        String messaggio=sb.toString();
        vistaLibreria.mostraMessaggio(messaggio);
    }




    public void aiutoFiltra(){
        StringBuilder sb=new StringBuilder();
        sb.append("Per utilizzare meccanismi di ricerca con filtro segui la seguente sintassi:"+"\n");
        sb.append("filtra --<campo> campo --<valore> valore"+"\n");
        sb.append("esempio:"+"\n");
        sb.append("filtra --campo statoLettura --valore letto");
    }














}
