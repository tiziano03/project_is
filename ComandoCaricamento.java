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
    public void execute(ParsedInput input) {
        if(!input.getArgomenti().isEmpty()){
            throw new IllegalArgumentException("Comando di caricamento malformato");
        }

        try {
            gp.carica(libreria, path);

        }catch(PersistenceException e){
            throw new PersistenceRuntimeException(e.getMessage());
        }

    }





}
