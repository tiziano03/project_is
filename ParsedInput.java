import java.util.List;
import java.util.Map;

public class ParsedInput {
    private String nomeComando;
    private List<String> argomentiPosizionali;
    private Map<String, String> argomentiNominali;



    public ParsedInput(String nomeComando, List<String> argomentiPosizionali, Map<String, String> argomentiNominali) {
        this.nomeComando=nomeComando;
        this.argomentiPosizionali=argomentiPosizionali;
        this.argomentiNominali=argomentiNominali;
    }



    public String getNomeComando() {
        return nomeComando;
    }



    public List<String> getArgomentiPosizionali() {
        return argomentiPosizionali;
    }



    public Map<String, String> getArgomentiNominali() {
        return argomentiNominali;
    }







}
