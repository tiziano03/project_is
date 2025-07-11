import java.util.List;

public class ComandoCerca implements Command{
    private final VistaLibreria vistaLibreria;
    private final Libreria libreria;


    public ComandoCerca(VistaLibreria vistaLibreria, Libreria libreria){
        this.vistaLibreria=vistaLibreria;
        this.libreria=libreria;
    }





    @Override
    public void execute(ParsedInput input) {
        if(!(input.getArgomenti().size()==1 && input.getArgomenti().get("termine")!=null))
            throw new IllegalArgumentException("Comando di ricerca malformato");




        String termine=input.getArgomenti().get("termine");
        List<Libro> libri1=libreria.filtraPerTitolo(termine);
        List <Libro> libri2=libreria.filtraPerAutore(termine);




        StringBuilder sb=new StringBuilder();
        sb.append("Risultato della ricerca:"+"\n");
        for(Libro lib:libri1){
            sb.append(lib.toString()).append("\n");
        }
        for(Libro lib:libri2){
            sb.append(lib.toString()).append("\n");
        }


        vistaLibreria.mostraMessaggio(sb.toString());


    }






}
