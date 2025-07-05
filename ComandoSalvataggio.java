public class ComandoSalvataggio implements Command {
    private final GestorePersistenza gp;
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;
    private final String path;

    public ComandoSalvataggio(GestorePersistenza gp,Libreria libreria, VistaLibreria vistaLibreria, String path) {
        this.gp=gp;
        this.libreria=libreria;
        this.path=path;
        this.vistaLibreria=vistaLibreria;
    }

    @Override
    public void execute(ParsedInput parsedInput) {
        gp.salva(libreria,path);
        vistaLibreria.mostraMessaggio("Salvataggio avvenuto con successo");

    }
}
