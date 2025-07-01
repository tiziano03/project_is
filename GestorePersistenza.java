public class GestorePersistenza {
    private StrategiaPersistenza strategia;

    public GestorePersistenza( StrategiaPersistenza strategia){
        this.strategia = strategia;
    }


    public void salva(Libreria l, String path){
        strategia.serialize(l.getMemento(), path);
    }


    public void carica(Libreria l, String path){
        Libreria.Memento m=strategia.deserialize(path);
        if(m!=null) l.setMemento(m);
    }




}
