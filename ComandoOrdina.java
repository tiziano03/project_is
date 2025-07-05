import java.util.Map;

public class ComandoOrdina implements Command {
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;


    public ComandoOrdina(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria = libreria;
        this.vistaLibreria = vistaLibreria;
    }


    @Override
    public void execute(ParsedInput input) {
        Map<String, String> mappa=input.getArgomentiNominali();
        String campo=mappa.get("campo");

        if ((!input.getArgomentiPosizionali().isEmpty()) || (mappa.size() > 1) || (campo==null)){
            vistaLibreria.mostraMessaggio("Perfavore, rispetta la sintassi:"+"\n"+
                    "<ordina> --<campo> campo"+"\n"+
                    "per maggiori informazioni digita <aiuto ordina>");
            return;
        }



        switch (campo) {
            case "titolo":
                libreria.sort(new ComparatoreLibroTitolo());
                break;
            case "autore":
                libreria.sort(new ComparatoreLibroAutore());
                break;
            case "valutazione":
                libreria.sort(new ComparatoreLibroValutazione());
                break;
            default:{
                vistaLibreria.mostraMessaggio("Attenzione:"+"\n"+
                        "i campi per cui è concesso l'ordinamento sono: titolo, autore, valutazione");
                return;
            }

        }


        vistaLibreria.mostraMessaggio("libreria ordinata con successo");


    }


}