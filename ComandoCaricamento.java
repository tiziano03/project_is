public class ComandoCaricamento implements Command {
    private GestorePersistenza gp;
    private Libreria libreria;
    private String path;

    public ComandoCaricamento(GestorePersistenza gp, String path) {
        this.gp = gp;
        this.libreria = Libreria.getInstance();
        this.path=path;
    }


    @Override
    public void execute(ParsedInput parsedInput) {
        gp.carica(libreria,path);
    }
}
