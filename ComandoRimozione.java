import java.util.Map;

public class ComandoRimozione implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;

    public ComandoRimozione(Libreria libreria, VistaLibreria vistaLibreria){
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;
    }


    @Override
    public void execute(ParsedInput parsedInput) {
        Map<String, String> mappa=parsedInput.getArgomentiNominali();
        String isbn= mappa.get("isbn");
        if(!(parsedInput.getArgomentiPosizionali().isEmpty()) || (mappa.size()!=1) || (isbn==null)){
            vistaLibreria.mostraMessaggio("Perfavore, rispetta la sintassi:"+"\n"+
                    "<rimuovi> --<isbn> isbn"+"\n"+
                    "per maggiori informazioni digita <aiuto rimuovi>");
            return;
        }



        if(!libreria.esiste(isbn)){
            vistaLibreria.mostraMessaggio("Non è presente un libro con isbn: "+isbn);
            return;
        }

        libreria.rimuoviIsbn(isbn);
        vistaLibreria.mostraMessaggio("Libro rimosso con successo");






    }



}
