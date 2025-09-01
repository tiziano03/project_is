public class ComandoCarica implements Command {
    private final GestorePersistenza gp;
    private final Libreria libreria;
    private final String path;


    public ComandoCarica(GestorePersistenza gp, Libreria libreria, String path) {
        this.gp = gp;
        this.libreria = libreria;
        this.path=path;
    }


    @Override
    public void execute(RichiestaComando input) {
        if(!input.getArgomenti().isEmpty()){
            throw new IllegalArgumentException("Comando di caricamento malformato");
        }

        try {
            Libreria.Memento meme=gp.carica(path);
            libreria.setMemento(meme);

        }catch(PersistenceException e){
            throw new PersistenceRuntimeException(e.getMessage());
        }

    }





}
