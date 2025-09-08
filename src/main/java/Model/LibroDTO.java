package Model;

import java.util.UUID;

public class LibroDTO {
    private final UUID id;
    private final String titolo;
    private final String autore;
    private final String isbn;
    private final Genere genere;
    private final StatoLettura statoLettura;
    private final Valutazione valutazione;


    public LibroDTO(Libro libro) {
        this.id = libro.getId();
        this.titolo = libro.getTitolo();
        this.autore = libro.getAutore();
        this.isbn = libro.getIsbn().toString();
        this.genere = libro.getGenere();
        this.statoLettura = libro.getStatoLettura();
        this.valutazione = libro.getValutazione();
    }

    public UUID getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public Genere getGenere() {
        return genere;
    }

    public StatoLettura getStatoLettura() {
        return statoLettura;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }


    //toString()
    public String toString() {
        String ts = "|titolo: " + titolo + "|" + " autore: \"" + autore + "\" |" +
                " isbn: \"" + isbn + "\" |" +
                " genere: " + genere + " |" +
                " statoLettura: " + statoLettura + " |" +
                " valutazione: " + valutazione + " |";
        return ts;
    }


    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof LibroDTO l)) return false;
        return id.equals(l.id);
    }


}



