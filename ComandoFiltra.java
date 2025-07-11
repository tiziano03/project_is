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

        Map<String,String> mappa=input.getArgomenti();
        String campo=mappa.get("campo");
        String valore=mappa.get("valore");


        if ((mappa.size()!=2)
            || (campo==null) || (valore==null)){
            throw new IllegalArgumentException("Comando di filtro malformato");
        }

        List<Libro> libri;


        switch(campo){
            case "titolo": libri=libreria.filtraPerTitolo(valore); break;
            case "autore": libri=libreria.filtraPerAutore(valore); break;
            case "genere": {
                Genere genere=Genere.getGenere(valore);
                if(genere==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libri=libreria.filtraPerGenere(genere);
                break;
            }
            case "statoLettura": {
                StatoLettura statoLettura= StatoLettura.getStatoLettura(valore);
                if(statoLettura==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libri=libreria.filtraPerStatoLettura(statoLettura);
                break;
            }
            case "valutazione": {
                Valutazione valutazione= Valutazione.getValutazione(valore);
                if(valutazione==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libri=libreria.filtraPerValutazione(valutazione);
                break;
            }
            default: {
                throw new CampoNonValidoException("Campo non valido");

            }
        }




        StringBuilder sb=new StringBuilder();
        sb.append("Risultato della ricerca con filtro:" +"\n");
        int i=0;
        for(Libro l: libri){
            i++;
            sb.append("Libro numero ").append(i).append(":").append(l).append("\n");
        }


        vistaLibreria.mostraMessaggio(sb.toString());





        }


    }












