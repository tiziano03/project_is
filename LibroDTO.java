import java.util.UUID;

public class LibroDTO {
    private final UUID id;
    private String titolo;
    private String autore;
    private String isbn;
    private Genere genere;
    private StatoLettura statoLettura;
    private Valutazione valutazione;



    public LibroDTO(Libro libro) {
        this.id = libro.getId();
        this.titolo = libro.getTitolo();
        this.autore = libro.getAutore();
        this.isbn = libro.getIsbn();
        this.genere = libro.getGenere();
        this.statoLettura = libro.getStatoLettura();
        this.valutazione = libro.getValutazione();
    }

    public UUID getId() {return id;}
    public String getTitolo() {return titolo;}
    public String getAutore() {return autore;}
    public String getIsbn() {return isbn;}
    public Genere getGenere() {return genere;}
    public StatoLettura getStatoLettura() {return statoLettura;}
    public Valutazione getValutazione() { return valutazione;}



    //toString()
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("|isbn: ").append(isbn).append("|").append(" titolo: \"").append(titolo).append("\" |")
                .append(" autore: \"").append(autore).append("\" |")
                .append(" genere: ").append(genere).append(" |")
                .append(" statoLettura: ").append(statoLettura).append(" |")
                .append(" valutazione: ").append(valutazione).append(" |");
        String ts=sb.toString();
        return ts;
    }


    public boolean equals(Object o){
        if(o==null) return false;
        if(o==this) return true;
        if(!(o instanceof LibroDTO)) return false;
        LibroDTO l=(LibroDTO)o;
        return id.equals(l.id);
    }






}



