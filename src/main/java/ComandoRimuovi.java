import java.util.Map;
import java.util.UUID;

public class ComandoRimuovi implements Command{
    private final Libreria libreria;

    public ComandoRimuovi(Libreria libreria) {
        this.libreria=libreria;
    }


    @Override
    public void execute(RichiestaComando richiestaComando) {
        Map<NomeParametro, String> mappa= richiestaComando.getArgomenti();


        String uuid= mappa.get(NomeParametro.UUID);



        if((mappa.size()!=1) || (uuid==null)){
            throw new IllegalArgumentException("Comando di rimozione malformato");
        }



        UUID id= UUID.fromString(uuid);



        libreria.rimuoviId(id);


    }



}
