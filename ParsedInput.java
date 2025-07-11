import java.util.HashMap;
import java.util.Map;

public class ParsedInput {
    private String nomeComando;
    private Map<String, String> argomenti;



    public ParsedInput(String nomeComando, Map<String, String> argomenti) {
        this.nomeComando=nomeComando;
        this.argomenti = argomenti;
    }

    public ParsedInput(String nomeComando) {
        this.nomeComando=nomeComando;
        argomenti=new HashMap<>();
    }




    public String getNomeComando() {
        return nomeComando;
    }





    public Map<String, String> getArgomenti() {
        return argomenti;
    }





}
