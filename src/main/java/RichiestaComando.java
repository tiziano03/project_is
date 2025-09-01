import java.util.HashMap;
import java.util.Map;

public class RichiestaComando {
    private NomeComando nomeComando;
    private Map<NomeParametro, String> argomenti;



    public RichiestaComando(NomeComando nomeComando, Map<NomeParametro, String> argomenti) {
        this.nomeComando=nomeComando;
        this.argomenti = argomenti;
    }

    public RichiestaComando(NomeComando nomeComando) {
        this.nomeComando=nomeComando;
        argomenti=new HashMap<>();
    }




    public NomeComando getNomeComando() {
        return nomeComando;
    }





    public Map<NomeParametro, String> getArgomenti() {
        return argomenti;
    }





}
