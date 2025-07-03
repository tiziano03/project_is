import java.util.Map;

public class ComandoFiltra implements Command{
    private Libreria libreria;


    @Override
    public void execute(ParsedInput input){
        if (!input.getArgomentiPosizionali().isEmpty()) throw new IllegalArgumentException();
        if (input.getArgomentiNominali().size()>1) throw new IllegalArgumentException();

        Map<String,String> mappa=input.getArgomentiNominali();
        String titolo=mappa.get("titolo");
        String autore=mappa.get("autore");
        String genere=mappa.get("genere");
        String valutazione=mappa.get("genere");
        String statoLettura=mappa.get("statoLettura");

        if(titolo==null && autore== null && genere==null && valutazione==null && statoLettura==null)
            throw new IllegalArgumentException();









        }


    }












