import java.util.Comparator;

public class ComparatoreLibroValutazione implements Comparator<Libro> {


    @Override
    public int compare(Libro l1, Libro l2) {
        return l1.getValutazione().getValore()-l2.getValutazione().getValore();
    }

}
