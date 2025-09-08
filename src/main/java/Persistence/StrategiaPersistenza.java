package Persistence;

import Model.Libreria;

public interface StrategiaPersistenza {

    void serialize(Libreria.Memento m, String path) throws PersistenceException;

    Libreria.Memento deserialize(String path) throws PersistenceException;


}
