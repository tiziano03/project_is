import java.util.List;
import java.util.Map;

public class ComandoInserimento implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;


    public ComandoInserimento(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;

    }



    @Override
    public void execute(ParsedInput input) {
        Map<String, String> mappa= input.getArgomenti();

        String isbn=mappa.get("isbn");
        String titolo=mappa.get("titolo");
        String autore=mappa.get("autore");
        String genere=mappa.get("genere");
        String statoLettura=mappa.get("statoLettura");
        String valutazione=mappa.get("valutazione");



        if(!(mappa.size()==6) || isbn==null || titolo==null || autore==null ||
                genere==null || statoLettura==null || valutazione==null){
            throw new IllegalArgumentException("Comando di inserimento malformato");
        }



        Genere genereEnum=Genere.getGenere(genere);
        StatoLettura statoLetturaEnum=StatoLettura.getStatoLettura(statoLettura);
        Valutazione valutazioneEnum=Valutazione.getValutazione(valutazione);



        if(!(genereEnum!=null && statoLetturaEnum!=null && valutazioneEnum!=null))
            throw new ValoreNonValidoException("Valore di campo inammissibile");




        if(libreria.esiste(isbn)){
            throw new IsbnDuplicatoException("Isbn duplicato");
        }




        Libro libro=new Libro(isbn,titolo,autore,valutazioneEnum,genereEnum,statoLetturaEnum);


        libreria.aggiungiLibro(libro);

    }
}
