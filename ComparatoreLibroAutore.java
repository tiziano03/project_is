import java.util.Comparator;

public class ComparatoreLibroAutore implements Comparator<Libro> {


    @Override
    public int compare(Libro l1, Libro l2) {
        return l1.getAutore().compareTo(l2.getAutore());
    }
}
