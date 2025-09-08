package Model;

import java.util.UUID;

public class Libro {
    private final UUID id;
    private Isbn isbn;
    private String titolo;
    private String autore;
    private Valutazione valutazione;
    private StatoLettura statoLettura;
    private Genere genere;


    //costruttore
    public Libro(Isbn isbn, String titolo, String autore, Valutazione valutazione, Genere genere, StatoLettura statoLettura) {
        if (isbn == null || titolo == null || autore == null
                || genere == null || valutazione == null || statoLettura == null) throw new IllegalArgumentException();


        this.id = UUID.randomUUID();
        this.isbn = isbn;
        this.titolo = titolo;
        this.autore = autore;
        this.valutazione = valutazione;
        this.genere = genere;
        this.statoLettura = statoLettura;
    }


    //getters and setters
    public Isbn getIsbn() {
        return this.isbn;
    }

    public void setIsbn(Isbn isbn) {
        if (isbn == null) throw new IllegalArgumentException();
        this.isbn = isbn;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        if (titolo == null) throw new IllegalArgumentException();
        this.titolo = titolo;
    }

    public String getAutore() {
        return this.autore;
    }

    public void setAutore(String autore) {
        if (autore == null) throw new IllegalArgumentException();
        this.autore = autore;
    }

    public Valutazione getValutazione() {
        return this.valutazione;
    }

    public void setValutazione(Valutazione valutazione) {
        if (valutazione == null) throw new IllegalArgumentException();
        this.valutazione = valutazione;
    }

    public Genere getGenere() {
        return this.genere;
    }

    public void setGenere(Genere genere) {
        if (genere == null) throw new IllegalArgumentException();
        this.genere = genere;
    }

    public StatoLettura getStatoLettura() {
        return this.statoLettura;
    }

    public void setStatoLettura(StatoLettura statoLettura) {
        if (statoLettura == null) throw new IllegalArgumentException();
        this.statoLettura = statoLettura;
    }

    public UUID getId() {
        return this.id;
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
        if (!(o instanceof Libro l)) return false;
        return id.equals(l.id);
    }


}



