import java.util.Comparator;

public class ComparatoreLibroTitolo implements Comparator<Libro> {


    @Override
    public int compare(Libro l1, Libro l2) {
        return l1.getTitolo().compareTo(l2.getTitolo());
    }

}
