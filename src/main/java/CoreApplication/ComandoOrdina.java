package CoreApplication;

import Model.CampoLibro;
import Model.Libreria;

import java.util.Map;

public class ComandoOrdina implements Command {
    private final Libreria libreria;


    public ComandoOrdina(Libreria libreria) {
        this.libreria = libreria;
    }


    @Override
    public void execute(RichiestaComando input) {
        Map<NomeParametro, String> mappa = input.getArgomenti();

        String campo = mappa.get(NomeParametro.CAMPO);

        if ((mappa.size() != 1) || (campo == null)) {
            throw new IllegalArgumentException("Comando di ordinamento malformato");
        }

        CampoLibro c = CampoLibro.getCampoLibro(campo);


        libreria.sort(c);

    }


}