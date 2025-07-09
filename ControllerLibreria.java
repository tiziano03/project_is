import java.util.HashMap;
import java.util.Map;

public class ControllerLibreria {
        private final Map<String, Command> mappaComandi;
        private final Command comandoDefault;   //è il comando non trovato




        public ControllerLibreria(Map <String, Command> mappaComandi, Command comandoDefault) {
            this.mappaComandi=mappaComandi;
            this.comandoDefault=comandoDefault;
        }



    public boolean processa(ParsedInput input) throws PersistenceException {
        String nomeComando=input.getNomeComando();
        Command comando= mappaComandi.getOrDefault(nomeComando, comandoDefault);
        return comando.execute(input);
    }














    }