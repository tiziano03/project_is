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
    public void execute(ParsedInput parsedInput) {
        gp.carica(libreria,path);
        vistaLibreria.mostraMessaggio("Caricamento avvenuto con successo");
    }





}
