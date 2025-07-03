public class ComandoRimozione implements Command{
    private Libreria libreria;

    public ComandoRimozione(){
        this.libreria=Libreria.getInstance();
    }


    @Override
    public void execute(ParsedInput parsedInput) {


    }



}
