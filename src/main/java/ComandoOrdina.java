import java.util.Map;

public class ComandoOrdina implements Command {
    private final Libreria libreria;


    public ComandoOrdina(Libreria libreria) {
        this.libreria = libreria;
    }



    @Override
    public void execute(RichiestaComando input) {
        Map<NomeParametro, String> mappa=input.getArgomenti();

        String campo=mappa.get(NomeParametro.CAMPO);

        CampoLibro c=CampoLibro.getCampoLibro(campo);

        if ((mappa.size()!=1) || (campo==null)){
            throw new IllegalArgumentException("Comando di ordinamento malformato");
        }

        libreria.sort(c);

        /*
        if(!CampoLibro.getCampiOrdinabili().contains(campo))

        switch (c) {
            case TITOLO:
                libreria.sort(new ComparatoreLibroTitolo());
                break;
            case AUTORE:
                libreria.sort(new ComparatoreLibroAutore());
                break;
            case VALUTAZIONE:
                libreria.sort(new ComparatoreLibroValutazione());
                break;
            default:{
                throw new CampoNonValidoException("Campo non ammissibile");
            }

        }
*/

    }


}