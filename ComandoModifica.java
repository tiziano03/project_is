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
        Map<String, String> mappa=input.getArgomentiNominali();
        String isbn=mappa.get("isbn");
        String campo=mappa.get("campo");
        String valore=mappa.get("valore");

        if((!input.getArgomentiPosizionali().isEmpty()) || !(mappa.size()==3)
            || (isbn==null) || (campo==null) || (valore==null)){
            vistaLibreria.mostraMessaggio("Perfavore, rispetta la sintassi:"+"\n"+
                    "modifica --<isbn> isbn --<campo> campo --<valore> valore"+"\n"+
                    "per maggiori informazioni digita <aiuto modifica>");
            return;
        }



        if(!libreria.esiste(isbn)){
            vistaLibreria.mostraMessaggio("Attenzione:" +"\n"+
                    "stai cercando di modificare un libro non presente!");
            return;
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
                    vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                            "i valori ammissibili per valutazione sono: una_stella, due_stelle, tre_stelle, quattro_stelle, cinque_stelle, non_disponibile");
                    return;
                }
                libreria.modificaValutazione(isbn,valutazione);
                break;
            }
            case "genere":{
                Genere genere=Genere.getGenere(valore);
                if(genere==null){
                    vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                            "i valori ammissibili per genere sono: narrativa, saggistica, tecnico, biografia, infanzia, generici");
                    return;
                }
                libreria.modificaGenere(isbn,genere);
                break;
            }
            case "statoLettura":{
                StatoLettura statoLettura=StatoLettura.getStatoLettura(valore);
                if(statoLettura==null){
                    vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                            "i valori ammissibili per statoLettura sono: letto, da_leggere, lettura_in_corso");
                    return;
                }
                libreria.modificaStatoLettura(isbn,statoLettura);
                break;
            }
            default:{
                vistaLibreria.mostraMessaggio("Attenzione"+"\n"+
                        "i campi che è ammesso modificare sono: titolo, autore, genere, statoLettura, valutazione");
                return;
            }
        }



        vistaLibreria.mostraMessaggio("modifica avvenuta con successo");








    }





}
