package Persistence;

import Model.Libreria;

public class GestorePersistenza {
    private final StrategiaPersistenza strategia;

    public GestorePersistenza(StrategiaPersistenza strategia) {
        this.strategia = strategia;
    }


    public void salva(Libreria.Memento meme, String path) throws PersistenceException {
        strategia.serialize(meme, path);
    }


    public Libreria.Memento carica(String path) throws PersistenceException {
        Libreria.Memento meme = strategia.deserialize(path);
        return meme;
    }

}



