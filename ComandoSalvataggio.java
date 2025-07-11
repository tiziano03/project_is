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
    public void execute(ParsedInput input){
        if(!input.getArgomenti().isEmpty()){
            throw new IllegalArgumentException("Comando di salvataggio malformato");
        }

        try {
            gp.salva(libreria, path);
        }catch(PersistenceException e){
            throw new PersistenceRuntimeException(e.getMessage());
            }


    }





}
