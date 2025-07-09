import java.util.Map;

public class ComandoRimozione implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;

    public ComandoRimozione(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;
    }


    @Override
    public boolean execute(ParsedInput parsedInput) {
        Map<String, String> mappa=parsedInput.getArgomentiNominali();
        String isbn= mappa.get("isbn");
        if(!(parsedInput.getArgomentiPosizionali().isEmpty()) || (mappa.size()!=1) || (isbn==null)){
            throw new SemanticException("Comando di rimozione malformato");
        }



        if(!libreria.esiste(isbn)){
            throw new SemanticException("Isbn inesistente");
        }


        libreria.rimuoviIsbn(isbn);
        vistaLibreria.mostraMessaggio("Libro rimosso con successo");
        return true;





    }



}
