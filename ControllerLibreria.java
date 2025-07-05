import java.util.HashMap;
import java.util.Map;

public class ControllerLibreria {
        private final Map<String, Command> mappaComandi;
        private final GestorePersistenza gp;
        private final Libreria libreria;
        private final VistaLibreria vista;
        private final InputParser ip;
        private final String path;
        boolean flag= true;




        public ControllerLibreria(Libreria libreria, VistaLibreria vista, GestorePersistenza gp){
            this.gp=gp;
            this.vista=vista;
            this.libreria=Libreria.getInstance();
            this.ip=new InputParser();
            this.mappaComandi=new HashMap<>();
            this.path="LibreriaPersonale";

            mappaComandi.put("aiuto", new ComandoAiuto(vista));
            mappaComandi.put("carica", new ComandoCaricamento(gp,libreria,path,vista));
            mappaComandi.put("esci", new ComandoEsci(this,vista));
            mappaComandi.put("filtra" , new ComandoFiltra(libreria,vista));
            mappaComandi.put("aggiungi",new ComandoInserimento(libreria,vista));
            mappaComandi.put("modifica", new ComandoModifica(libreria,vista));
            mappaComandi.put("ordina",new ComandoOrdina(libreria,vista));
            mappaComandi.put("rimuovi", new ComandoRimozione(libreria,vista));
            mappaComandi.put("salva", new ComandoSalvataggio(gp,libreria, vista, path));

        }



        public void run(){
            vista.presentazione();
            while(flag){
                String input= vista.prendiInput();
                eseguiComando(input);
            }
        }




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