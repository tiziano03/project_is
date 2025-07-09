import java.util.List;

public class ComandoCerca implements Command{
    private final VistaLibreria vistaLibreria;
    private final Libreria libreria;


    public ComandoCerca(VistaLibreria vistaLibreria, Libreria libreria){
        this.vistaLibreria=vistaLibreria;
        this.libreria=libreria;
    }





    @Override
    public boolean execute(ParsedInput input) {
        if(input.getArgomentiPosizionali().size()!=1 || !input.getArgomentiNominali().isEmpty()){
            throw new SemanticException("Comando di ricerca malformato");
        }

        String termine=input.getArgomentiPosizionali().get(0);

        List<Libro> libri1=libreria.filtraPerTitolo(termine);
        List <Libro> libri2=libreria.filtraPerAutore(termine);

        if(libri1.isEmpty() && libri2.isEmpty()){
            vistaLibreria.mostraMessaggio("La ricerca non ha dato risultati!");
            return true;
        }

        StringBuilder sb=new StringBuilder();
        sb.append("Risultato della ricerca:"+"\n");
        for(Libro lib:libri1){
            sb.append(lib.toString()).append("\n");
        }
        for(Libro lib:libri2){
            sb.append(lib.toString()).append("\n");
        }



        vistaLibreria.mostraMessaggio(sb.toString());
        return true;





    }






}
