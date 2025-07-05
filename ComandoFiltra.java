import java.util.List;
import java.util.Map;

public class ComandoFiltra implements Command{
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;


    public ComandoFiltra(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria = libreria;
        this.vistaLibreria = vistaLibreria;
    }


    @Override
    public void execute(ParsedInput input){
        if (!input.getArgomentiPosizionali().isEmpty()) throw new IllegalArgumentException();
        if (input.getArgomentiNominali().size()!=2) throw new IllegalArgumentException();

        Map<String,String> mappa=input.getArgomentiNominali();
        String campo=mappa.get("campo");
        String valore=mappa.get("valore");

        if(campo==null || valore==null) throw new IllegalArgumentException();

        List<Libro> libri;


        switch(campo){
            case "titolo": libri=libreria.filtraPerTitolo(valore); break;
            case "autore": libri=libreria.filtraPerAutore(valore); break;
            case "genere": {
                Genere genere=Genere.getGenere(valore);
                if(genere==null) throw new IllegalArgumentException();
                libri=libreria.filtraPerGenere(genere);
                break;
            }
            case "statoLettura": {
                StatoLettura statoLettura= StatoLettura.getStatoLettura(valore);
                if(statoLettura==null) throw new IllegalArgumentException();
                libri=libreria.filtraPerStatoLettura(statoLettura);
                break;
            }
            case "valutazione": {
                Valutazione valutazione= Valutazione.getValutazione(valore);
                if(valutazione==null) throw new IllegalArgumentException();
                libri=libreria.filtraPerValutazione(valutazione);
                break;
            }
            default: throw new IllegalArgumentException();
        }


        if(libri.isEmpty()) vistaLibreria.mostraMessaggio("La ricerca con filtro non ha avuto risultati");


        vistaLibreria.mostraRicerca(libri);











        }


    }












