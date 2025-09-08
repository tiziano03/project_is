package CoreApplication;

import Model.Libreria;
import Persistence.GestorePersistenza;
import Persistence.PersistenceException;
import Persistence.PersistenceRuntimeException;

public class ComandoSalva implements Command {
    private final GestorePersistenza gp;
    private final Libreria libreria;
    private final String path;


    public ComandoSalva(GestorePersistenza gp, Libreria libreria, String path) {
        this.gp = gp;
        this.libreria = libreria;
        this.path = path;
    }


    @Override
    public void execute(RichiestaComando input) {
        if (!input.getArgomenti().isEmpty()) {
            throw new IllegalArgumentException("Comando di salvataggio malformato");
        }
        Libreria.Memento memento = libreria.getMemento();


        try {
            gp.salva(memento, path);
        } catch (PersistenceException e) {
            throw new PersistenceRuntimeException(e.getMessage());
        }
    }

}
