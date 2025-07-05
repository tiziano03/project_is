import java.util.Map;

public class ComandoModifica implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;

    public ComandoModifica(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria=libreria;
        this.vistaLibreria=vistaLibreria;
    }


    @Override
    public void execute(ParsedInput input){
        if(!input.getArgomentiPosizionali().isEmpty()) throw new IllegalArgumentException();
        if(!(input.getArgomentiNominali().size()==3)) throw new IllegalArgumentException();
        Map<String, String> mappa=input.getArgomentiNominali();

        String isbn=mappa.get("isbn");
        String campo=mappa.get("campo");
        String valore=mappa.get("valore");

        if(isbn==null || campo==null || valore==null) throw new IllegalArgumentException();


        if(!libreria.esiste(isbn)) throw new IllegalArgumentException();


        switch (campo){
            case "titolo":{
                libreria.modificaTitolo(isbn,valore);
                break;
            }
            case "autore":{
                libreria.modificaAutore(isbn,valore);
                break;
            }
            case "valutazione":{
                Valutazione valutazione=Valutazione.getValutazione(valore);
                if(valutazione==null) throw new IllegalArgumentException();
                libreria.modificaValutazione(isbn,valutazione);
                break;
            }
            case "genere":{
                Genere genere=Genere.getGenere(valore);
                if(genere==null) throw new IllegalArgumentException();
                libreria.modificaGenere(isbn,genere);
                break;
            }
            case "statoLettura":{
                StatoLettura statoLettura=StatoLettura.getStatoLettura(valore);
                if(statoLettura==null) throw new IllegalArgumentException();
                libreria.modificaStatoLettura(isbn,statoLettura);
                break;
            }
            default: throw new IllegalArgumentException();
        }



        vistaLibreria.mostraMessaggio("modifica avvenuta con successo");








    }





}
