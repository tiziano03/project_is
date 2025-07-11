import java.util.HashMap;
import java.util.Map;

public class ControllerLibreria {
        private final Map<String, Command> mappaComandi;




        public ControllerLibreria(Map <String, Command> mappaComandi) {
            this.mappaComandi=mappaComandi;
        }



    public void processa(ParsedInput input){
        String nomeComando=input.getNomeComando();
        Command comando= mappaComandi.get(nomeComando);
        if(comando==null) throw new UnsupportedOperationException();
        comando.execute(input);
    }









    }