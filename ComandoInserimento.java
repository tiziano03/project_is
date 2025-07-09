import java.util.List;

public class ComandoInserimento implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;


    public ComandoInserimento(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;

    }



    @Override
    public boolean execute(ParsedInput parsedInput) {

        if(!parsedInput.getArgomentiNominali().isEmpty() || !(parsedInput.getArgomentiPosizionali().size()==6)){
            throw new SemanticException("Comando di inserimento malformato");
        }


        List<String> lista=parsedInput.getArgomentiPosizionali();
        String isbn=lista.get(0);
        String titolo=lista.get(1);
        String autore=lista.get(2);
        Genere genere=Genere.getGenere(lista.get(3));
        StatoLettura statoLettura=StatoLettura.getStatoLettura(lista.get(4));
        Valutazione valutazione=Valutazione.getValutazione(lista.get(5));

        if(valutazione==null || genere==null || statoLettura==null){
            throw new SemanticException("Valore di campo non ammissibile");
        }


        if(libreria.esiste(isbn)){
            throw new SemanticException("Isbn duplicato");
        }

        Libro libro=new Libro(isbn,titolo,autore,valutazione,genere,statoLettura);


        libreria.aggiungiLibro(libro);


        vistaLibreria.mostraMessaggio("Inserimento avvenuto con successo");


        return true;

    }
}
