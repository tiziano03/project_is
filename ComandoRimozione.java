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
        if(!parsedInput.getArgomentiPosizionali().isEmpty()) throw new IllegalArgumentException();
        if (parsedInput.getArgomentiNominali().size()!=1) throw new IllegalArgumentException();
        Map<String, String> mappa=parsedInput.getArgomentiNominali();

        String isbn=mappa.get("isbn");
        if(isbn==null) throw new IllegalArgumentException();

        if(!libreria.esiste(isbn)) vistaLibreria.mostraMessaggio("Il libro non esiste");

        libreria.rimuoviIsbn(isbn);
        vistaLibreria.mostraMessaggio("Libro rimosso con successo");






    }



}
