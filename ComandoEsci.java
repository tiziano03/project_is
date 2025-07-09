public class ComandoEsci implements Command {
    private final VistaLibreria vistaLibreria;

    public ComandoEsci(VistaLibreria vistaLibreria) {
        this.vistaLibreria=vistaLibreria;
    }


    public boolean execute(ParsedInput parsedInput){

        if(!parsedInput.getArgomentiNominali().isEmpty() || !parsedInput.getArgomentiPosizionali().isEmpty()){
            throw new SemanticException("Comando di uscita malformato");
        }

        vistaLibreria.mostraMessaggio("Bye!");
        return false; //termina l'applicazione

    }




}
