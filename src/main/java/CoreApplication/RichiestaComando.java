package CoreApplication;

import java.util.HashMap;
import java.util.Map;

public class RichiestaComando {
    private final NomeComando nomeComando;
    private final Map<NomeParametro, String> argomenti;


    public RichiestaComando(NomeComando nomeComando, Map<NomeParametro, String> argomenti) {
        if(nomeComando==null || argomenti==null) throw new IllegalArgumentException();
        this.nomeComando = nomeComando;
        this.argomenti = argomenti;
    }

    public RichiestaComando(NomeComando nomeComando) {
        if(nomeComando==null) throw new IllegalArgumentException();
        this.nomeComando = nomeComando;
        argomenti = new HashMap<>();
    }


    public NomeComando getNomeComando() {
        return nomeComando;
    }


    public Map<NomeParametro, String> getArgomenti() {
        return argomenti;
    }


}
