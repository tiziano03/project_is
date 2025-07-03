public class ComandoSalvataggio implements Command {
    private GestorePersistenza gp;
    private Libreria libreria;

    public ComandoSalvataggio(GestorePersistenza gp) {
        this.gp=gp;
        this.libreria=Libreria.getInstance();
    }

    @Override
    public void execute(ParsedInput parsedInput) {


    }
}
