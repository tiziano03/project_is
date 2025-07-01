
public interface StrategiaPersistenza {

    public void serialize(Libreria.Memento m, String path);
    public Libreria.Memento deserialize(String path);


}
