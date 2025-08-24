import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CliRunner {

    public static void main(String[] args) {
        Map<NomeComando, Command> mappaComandi = new HashMap<NomeComando, Command>();

        GestorePersistenza gestorePersistenza = new GestorePersistenza(new StrategiaPersistenzaJson());

        Libreria libreria = Libreria.getInstance();

        VistaLibreriaRC vistaLibreria = new VistaLibreriaRCA();

        String path = "LibreriaPersonale";

        libreria.aggiungiAscoltatore(vistaLibreria);


        mappaComandi.put(NomeComando.CARICA, new ComandoCarica(gestorePersistenza, libreria, path));
        mappaComandi.put(NomeComando.AGGIUNGI, new ComandoAggiungi(libreria));
        mappaComandi.put(NomeComando.MODIFICA, new ComandoModifica(libreria));
        mappaComandi.put(NomeComando.ORDINA, new ComandoOrdina(libreria));
        mappaComandi.put(NomeComando.RIMUOVI, new ComandoRimuovi(libreria));
        mappaComandi.put(NomeComando.SALVA, new ComandoSalva(gestorePersistenza, libreria, path));


        ControllerLibreria controller = new ControllerLibreria(mappaComandi, libreria);

        Wizard w=new Wizard(vistaLibreria, controller);

        w.run();

    }

}


