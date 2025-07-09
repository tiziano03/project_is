import java.util.Map;

public class ComandoOrdina implements Command {
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;


    public ComandoOrdina(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria = libreria;
        this.vistaLibreria = vistaLibreria;
    }



    @Override
    public boolean execute(ParsedInput input) {
        Map<String, String> mappa=input.getArgomentiNominali();
        String campo=mappa.get("campo");

        if ((!input.getArgomentiPosizionali().isEmpty()) || (mappa.size()!=1) || (campo==null)){
            throw new SemanticException("Comando di ordinamento malformato");
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
                throw new SemanticException("Campo non ammissibile");
            }

        }


        vistaLibreria.mostraMessaggio("libreria ordinata con successo");

        return true;

    }


}