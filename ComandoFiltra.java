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
        Map<String,String> mappa=input.getArgomentiNominali();
        String campo=mappa.get("campo");
        String valore=mappa.get("valore");


        if ((!input.getArgomentiPosizionali().isEmpty()) || (mappa.size()!=2)
            || (campo==null) || (valore==null)){
            vistaLibreria.mostraMessaggio("Errore! Perfavore, rispetta la sintassi:"+"\n"+
                    "fltra --<campo> campo --<valore> valore"+"\n"
                    +"Per maggiori informazioni digita:<aiuto filtra>");
            return;
        }

        List<Libro> libri;


        switch(campo){
            case "titolo": libri=libreria.filtraPerTitolo(valore); break;
            case "autore": libri=libreria.filtraPerAutore(valore); break;
            case "genere": {
                Genere genere=Genere.getGenere(valore);
                if(genere==null){
                    vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                            "valori ammissibili per il genere: narrativa, saggistica, tecnico, biografia, infanzia, generici"+"\n"+
                            "valori ammissibili per statoLettura: letto, da_leggere, lettura_in_corso"+"\n"+
                            "valori ammissibili per valutazione: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle, non_disponibile");
                    return;
                }
                libri=libreria.filtraPerGenere(genere);
                break;
            }
            case "statoLettura": {
                StatoLettura statoLettura= StatoLettura.getStatoLettura(valore);
                if(statoLettura==null){
                    vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                                    "valori ammissibili per il genere: narrativa, saggistica, tecnico, biografia, infanzia, generici"+"\n"+
                                    "valori ammissibili per statoLettura: letto, da_leggere, lettura_in_corso"+"\n"+
                                    "valori ammissibili per valutazione: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle, non_disponibile");
                    return;
                }
                libri=libreria.filtraPerStatoLettura(statoLettura);
                break;
            }
            case "valutazione": {
                Valutazione valutazione= Valutazione.getValutazione(valore);
                if(valutazione==null){
                    vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                                    "valori ammissibili per il genere: narrativa, saggistica, tecnico, biografia, infanzia, generici"+"\n"+
                                    "valori ammissibili per statoLettura: letto, da_leggere, lettura_in_corso"+"\n"+
                                    "valori ammissibili per valutazione: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle, non_disponibile");
                    return;
                }
                libri=libreria.filtraPerValutazione(valutazione);
                break;
            }
            default: {
             vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                     "gli argomenti ammissibili per campo sono: titolo, autore, genere, statoLettura, valutazione");
             return;
            }
        }


        if(libri.isEmpty()) vistaLibreria.mostraMessaggio("La ricerca con filtro non ha avuto risultati");


        vistaLibreria.mostraRicerca(libri);











        }


    }












