
public interface StrategiaPersistenza {

    public void serialize(Libreria.Memento m, String path) throws PersistenceException;
    public Libreria.Memento deserialize(String path) throws PersistenceException;


}
