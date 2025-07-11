import java.util.Map;

public class ComandoRimozione implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;

    public ComandoRimozione(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;
    }


    @Override
    public void execute(ParsedInput parsedInput) {
        Map<String, String> mappa=parsedInput.getArgomenti();
        String isbn= mappa.get("isbn");



        if((mappa.size()!=1) || (isbn==null)){
            throw new IllegalArgumentException("Comando di rimozione malformato");
        }



        if(!libreria.esiste(isbn)){
            throw new IsbnNonTrovatoException("Isbn inesistente");
        }



        libreria.rimuoviIsbn(isbn);


    }



}
