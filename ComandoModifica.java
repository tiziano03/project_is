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

        Map<String, String> mappa=input.getArgomenti();
        String isbn=mappa.get("isbn");
        String campo=mappa.get("campo");
        String valore=mappa.get("valore");



        if(!(mappa.size()==3) || (isbn==null) || (campo==null) || (valore==null))
            throw new IllegalArgumentException("Comando di modifica malformato");




        if(!libreria.esiste(isbn)){
            throw new IsbnNonTrovatoException("Isbn inesistente");
        }



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
                if(valutazione==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libreria.modificaValutazione(isbn,valutazione);
                break;
            }
            case "genere":{
                Genere genere=Genere.getGenere(valore);
                if(genere==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libreria.modificaGenere(isbn,genere);
                break;
            }
            case "statoLettura":{
                StatoLettura statoLettura=StatoLettura.getStatoLettura(valore);
                if(statoLettura==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libreria.modificaStatoLettura(isbn, statoLettura);
                break;
            }
            default:{
                throw new CampoNonValidoException("Campo non ammissibile");
            }
        }







    }





}
