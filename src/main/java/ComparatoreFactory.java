import java.util.Comparator;

public final class ComparatoreFactory {

    /**
     * Costruttore privato per prevenire l'istanziazione di questa classe di utility.
     */
    private ComparatoreFactory() {}

    /**
     * Crea un Comparator<Libro> completo, con spareggio su UUID, a partire da un CampoLibro.
     * Gestisce solo i campi che sono stati designati come "ordinabili".
     *
     * @param campo Il criterio di ordinamento desiderato (deve essere un campo ordinabile).
     * @return Il Comparator<Libro> corrispondente.
     * @throws IllegalArgumentException se il campo fornito non è supportato per l'ordinamento.
     */

    public static Comparator<Libro> creaComparatorDaCampo(CampoLibro campo) {
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
                // Se viene passato un campo non ordinabile (es. ISBN, GENERE),
                // è un errore di programmazione. Falliamo in modo esplicito.
                throw new CampoNonValidoException("Il campo " + campo.name() + " non è supportato per l'ordinamento.");
        }

        // FASE FINALE CRUCIALE:
        // Aggiunge l'UUID come criterio di spareggio secondario per garantire che due
        // libri diversi non vengano mai considerati uguali dalla TreeSet.
        return comparator.thenComparing(Libro::getId);
    }
}