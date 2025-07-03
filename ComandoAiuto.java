public class ComandoAiuto implements Command{


    @Override
    public void execute(ParsedInput input){
        if(!input.getArgomentiNominali().isEmpty() || input.getArgomentiPosizionali().size()>1)
            throw new IllegalArgumentException();
        if(input.getArgomentiPosizionali().isEmpty()) aiutoGenerale();

        String argomento= input.getArgomentiPosizionali().get(0);

        switch(argomento){
            case "filtra": aiutoFiltra(); break;
            case "aggiungi": aiutoAggiungi(); break;
            case "modifica": aiutoModifica(); break;
            case "ordina": aiutoOrdina(); break;
            case "rimuovi": aiutoRimuovi(); break;
            default: throw new IllegalArgumentException();

        }

    }



    public void aiutoFiltra(){}

    public void aiutoAggiungi(){}

    public void aiutoModifica(){}

    public void aiutoOrdina(){}

    public void aiutoRimuovi(){}




    public void aiutoGenerale(){}






}
