public class ComandoEsci implements Command {
    private final ControllerLibreria controllerLibreria;
    private final VistaLibreria vistaLibreria;

    public ComandoEsci(ControllerLibreria controllerLibreria, VistaLibreria vistaLibreria) {
        this.controllerLibreria = controllerLibreria;
        this.vistaLibreria=vistaLibreria;
    }


    public void execute(ParsedInput input){
        controllerLibreria.esci();
        vistaLibreria.mostraMessaggio("Bye!");

    }




}
