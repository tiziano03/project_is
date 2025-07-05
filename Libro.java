public class Libro {
    private String isbn;
    private String titolo;
    private String autore;
    private Valutazione valutazione;
    private StatoLettura statoLettura;
    private Genere genere;

    public Libro(String isbn, String titolo, String autore, Valutazione valutazione, Genere genere,StatoLettura statoLettura) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.autore = autore;
        this.valutazione = valutazione;
        this.genere = genere;
        this.statoLettura = statoLettura;
    }


    public String getIsbn(){ return this.isbn;}
    public String getTitolo(){ return this.titolo;}
    public String getAutore(){ return this.autore;}
    public Valutazione getValutazione(){return this.valutazione;}
    public Genere getGenere(){return this.genere;}
    public StatoLettura getStatoLettura(){return this.statoLettura;}
//  public String setIsbn(String isbn){ return this.isbn=isbn;}
    public void setTitolo(String titolo){ this.titolo = titolo;}
    public void setAutore(String autore){ this.autore = autore;}
    public void setValutazione(Valutazione valutazione){this.valutazione=valutazione;}
    public void setGenere(Genere genere){ this.genere = genere;}
    public void setStatoLettura(StatoLettura statoLettura){ this.statoLettura = statoLettura;}


    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("isbn: ").append(isbn).append(" titolo: \"").append(titolo).append("\"")
                .append(" autore: \"").append(autore).append("\"")
                .append(" genere: ").append(genere)
                .append(" statoLettura: ").append(statoLettura)
                .append(" valutazione: ").append(valutazione);
        String ts=sb.toString();
        return ts;
    }



}
