import java.util.HashMap;
import java.util.Map;

public class ControllerLibreria {
        private Map<String, Command> mappaComandi;
        private GestorePersistenza gp;
        private Libreria libreria;
        private VistaLibreria vista;
        private InputParser ip;
        private String path="LibreriaPersonale";
        boolean flag= true;




        public ControllerLibreria(GestorePersistenza gp, VistaLibreria vista){
            this.gp=gp;
            this.vista=vista;
            this.libreria=Libreria.getInstance();
            this.ip=new InputParser();
            this.mappaComandi=new HashMap<>();



            mappaComandi.put("aiuto", new ComandoAiuto());
            mappaComandi.put("carica", new ComandoCaricamento(gp,path));
            mappaComandi.put("esci", new ComandoEsci(this));
            mappaComandi.put("filtra" , new ComandoFiltra());
            mappaComandi.put("aggiungi",new ComandoInserimento());
            mappaComandi.put("modifica", new ComandoModifica());
            mappaComandi.put("ordina",new ComandoOrdina());
            mappaComandi.put("rimuovi", new ComandoRimozione());
            mappaComandi.put("salva", new ComandoSalvataggio(gp));
            mappaComandi.put("visualizza", new ComandoVisualizza());



        }


        /*
        public void run(){
            presentazione();
            while(flag){










            }












        }

        */










        private void presentazione(){}








    private void eseguiComando(String input){
        ParsedInput pi=ip.parse(input);
        Command comando= mappaComandi.get(pi.getNomeComando());
        if(comando==null) throw new RuntimeException("Comando inesistente");
        comando.execute(pi);

    }


    public void esci(){
            flag=false;
    }














    }