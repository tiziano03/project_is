import java.util.List;

public class ComandoInserimento implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;



    public ComandoInserimento(Libreria libreria, VistaLibreria vistaLibreria){
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;

    }



    @Override
    public void execute(ParsedInput parsedInput) {
        if(!parsedInput.getArgomentiNominali().isEmpty() || !(parsedInput.getArgomentiPosizionali().size()==6)){
            vistaLibreria.mostraMessaggio("Perfavore, rispetta la sintassi:"+"\n"+
                    "<aggiungi> isbn \"titolo\" \"autore\" genere statoLettura valutazione"+"\n"+
                    "per maggiori informazioni digita:<aiuto aggiungi>");
            return;
        }


        List<String> lista=parsedInput.getArgomentiPosizionali();
        String isbn=lista.get(0);
        String titolo=lista.get(1);
        String autore=lista.get(2);
        Genere genere=Genere.getGenere(lista.get(3));
        StatoLettura statoLettura=StatoLettura.getStatoLettura(lista.get(4));
        Valutazione valutazione=Valutazione.getValutazione(lista.get(5));

        if(valutazione==null || genere==null || statoLettura==null){
            vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                    "valori ammissibili per il genere: narrativa, saggistica, tecnico, biografia, infanzia, generici"+"\n"+
                    "valori ammissibili per statoLettura: letto, da_leggere, lettura_in_corso"+"\n"+
                    "valori ammissibili per valutazione: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle, non_disponibile");
            return;
        }


        if(libreria.esiste(isbn)){
            vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                    "Esiste già un libro con isbn:"+isbn+"."+"\n"+
                    "Se vuoi sostituirlo, prima rimuovi quello già esistente");
            return;
        }

        Libro libro=new Libro(isbn,titolo,autore,valutazione,genere,statoLettura);


        libreria.aggiungiLibro(libro);

        vistaLibreria.mostraMessaggio("Inserimento avvenuto con successo");


    }
}
