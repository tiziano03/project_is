import java.util.Comparator;

public class ComparatoreLibroValutazione implements Comparator<Libro> {



    @Override
    public int compare(Libro l1, Libro l2) {
        return l2.getValutazione().getValore()-l1.getValutazione().getValore();
    }

/*
    @Override
    public int compare(Libro l1, Libro l2) {
        if(l1.getValutazione().getValore()>l2.getValutazione().getValore()) return 1;
        return -1;
    }



 */
}
