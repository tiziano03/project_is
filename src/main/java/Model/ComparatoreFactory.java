package Model;

import java.util.Comparator;

public final class ComparatoreFactory {

    //classe utility
    private ComparatoreFactory() {
    }


    //restituisce un comparatore che compara i libri in base al campo del libro.
    //se due libri sono uguali secondo il comparatore, sarebbe un problema in quanto il treeset
    //non contiene oggetti che sono uguali secondo compareTo, quindi si sceglie di
    //fare lo spareggio, basandosi sull'UUID.


    public static Comparator<Libro> creaComparatorDaCampo(CampoLibro campo) {
        if(campo==null) throw new IllegalArgumentException();
        Comparator<Libro> comparator;

        // Lo switch gestisce solo i casi di ordinamento validi
        switch (campo) {
            case TITOLO:
                comparator = Comparator.comparing(Libro::getTitolo, String.CASE_INSENSITIVE_ORDER);
                break;

            case AUTORE:
                comparator = Comparator.comparing(Libro::getAutore, String.CASE_INSENSITIVE_ORDER);
                break;

            case VALUTAZIONE:
                // Ordiniamo dalla valutazione più alta alla più bassa (5 stelle prima di 1 stella)
                comparator = Comparator.comparing(Libro::getValutazione).reversed();
                break;

            default:
                throw new IllegalArgumentException("Il campo " + campo.name() + " non è supportato per l'ordinamento.");
        }

        // FASE FINALE CRUCIALE:
        // Aggiunge l'UUID come criterio di spareggio secondario per garantire che due
        // libri diversi non vengano mai considerati uguali dalla TreeSet.
        return comparator.thenComparing(Libro::getId);
    }
}