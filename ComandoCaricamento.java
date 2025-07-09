public class ComandoCaricamento implements Command {
    private final GestorePersistenza gp;
    private final Libreria libreria;
    private final String path;
    private final VistaLibreria vistaLibreria;

    public ComandoCaricamento(GestorePersistenza gp,Libreria libreria, String path, VistaLibreria vistaLibreria) {
        this.gp = gp;
        this.libreria = libreria;
        this.path=path;
        this.vistaLibreria = vistaLibreria;
    }


    @Override
    public boolean execute(ParsedInput parsedInput) throws PersistenceException {
        if(!parsedInput.getArgomentiNominali().isEmpty() || !parsedInput.getArgomentiPosizionali().isEmpty()){
            throw new SemanticException("Comando di caricamento malformato");
        }


        gp.carica(libreria,path);
        vistaLibreria.mostraMessaggio("Caricamento avvenuto con successo");
        return true;
    }





}
