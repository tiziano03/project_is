public class ComandoVisualizza implements Command{
    private Libreria libreria;


    public ComandoVisualizza(){
        this.libreria=Libreria.getInstance();
    }


    @Override
    public void execute(ParsedInput parsedInput) {

    }



}
