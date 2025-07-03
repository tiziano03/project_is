public class ComandoEsci implements Command {
    private ControllerLibreria controllerLibreria;


    public ComandoEsci(ControllerLibreria controllerLibreria){
        this.controllerLibreria = controllerLibreria;
    }


    public void execute(ParsedInput input){
        controllerLibreria.esci();
    }




}
