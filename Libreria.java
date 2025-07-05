import java.util.*;

public class Libreria implements Subject {
    private List<Libro> libri=new ArrayList<>();
    private List<Observer> osservatori=new LinkedList<>();
    private static Libreria instance=new Libreria();

    public static Libreria getInstance(){
        return instance;
    }

    private Libreria(){}

    public void aggiungiAscoltatore(Observer observer) {
        osservatori.add(observer);
    }

    public void rimuoviAscoltatore(Observer observer) {
        osservatori.remove(observer);
    }


    public void aggiungiLibro(Libro l) {
        libri.add(l);
        notifica();
    }


    public void rimuoviIsbn(String isbn) {
        Iterator<Libro> it = libri.iterator();
        while (it.hasNext()) {
            Libro curr = it.next();
            if (curr.getIsbn().equals(isbn)) {
                it.remove();
                notifica();
                break;
            }
        }
    }

    public List<Libro> getLibri(){
        return libri;
    }


    public boolean esiste(String isbn){
        for(Libro l:libri)
            if(l.getIsbn().equals(isbn)) return true;
        return false;
    }


    public List<Libro> filtraPerTitolo(String titolo){
        List<Libro> result=new ArrayList<>();
        for(Libro l: libri){
            if(l.getTitolo().contains(titolo)) result.add(l);
        }
        return result;
    }


    public List<Libro> filtraPerAutore(String autore){
        List<Libro> result=new ArrayList<>();
        for(Libro l: libri){
            if(l.getAutore().contains(autore)) result.add(l);
        }
        return result;
    }


    public List<Libro> filtraPerGenere (Genere genere){
        List<Libro> result=new ArrayList<>();
        for(Libro l: libri){
            if(l.getGenere().equals(genere)) result.add(l);
        }
        return result;
    }


    public List<Libro> filtraPerStatoLettura(StatoLettura statoLettura){
        List<Libro> result=new ArrayList<>();
        for(Libro l: libri){
            if(l.getStatoLettura().equals(statoLettura)) result.add(l);
        }
        return result;
    }


    public List<Libro> filtraPerValutazione(Valutazione valutazione){
        List<Libro> result=new ArrayList<>();
        for(Libro l: libri){
            if(l.getValutazione().equals(valutazione)) result.add(l);
        }
        return result;
    }


    public void modificaTitolo(String isbn, String titolo){
        for(Libro l: libri){
            if(l.getIsbn().equals(isbn)) l.setTitolo(titolo);
        }
    }

    public void modificaAutore(String isbn, String autore){
        for(Libro l: libri){
            if(l.getAutore().equals(autore)){
                l.setAutore(autore);
                notifica();
            }
        }
    }

    public void modificaGenere(String isbn, Genere genere){
        for(Libro l: libri){
            if(l.getIsbn().equals(isbn)){
                l.setGenere(genere);
                notifica();
            }
        }
    }

    public void modificaValutazione(String isbn, Valutazione valutazione){
        for(Libro l: libri){
            if(l.getIsbn().equals(isbn)){
                l.setValutazione(valutazione);
                notifica();
            }
        }
    }

    public void modificaStatoLettura(String isbn, StatoLettura statoLettura){
        for(Libro l: libri){
            if(l.getStatoLettura().equals(statoLettura)){
                l.setStatoLettura(statoLettura);
                notifica();
            }
        }
    }





    public void sort(Comparator<Libro> strategy){
        Collections.sort(libri, strategy);
        notifica();
    }


    public Memento getMemento(){
        return new Memento(libri);
    }


    public void setMemento(Memento m){
        libri=m.stato;
        notifica();
    }


    public void notifica(){
        for (Observer o:osservatori)
            o.update(libri);

    }






















    public static class Memento{
        private List<Libro> stato;


        private Memento(List<Libro> stato){
            this.stato=stato;
        }







    }



/*


public static void main (String[] args){
        Libreria l=new Libreria();
        Libro l1=new Libro("alnfonaewif","titolo1","autore1",Valutazione.CINQUE_STELLE,Genere.BIOGRAFIA,StatoLettura.LETTO);
        Libro l2=new Libro("skmcmoapje9r","titolo2","autore2",Valutazione.TRE_STELLE,Genere.BIOGRAFIA,StatoLettura.LETTO);
        Libro l3=new Libro("vnosdfonsep","titolo3","autore3",Valutazione.UNA_STELLA,Genere.BIOGRAFIA,StatoLettura.LETTO);
        Libro l4 =new Libro("ekjfnkcmapoe","titolo4","autore4",Valutazione.DUE_STELLE,Genere.BIOGRAFIA,StatoLettura.LETTO);

        l.aggiungiLibro(l1);
        l.aggiungiLibro(l2);
        l.aggiungiLibro(l3);
        l.aggiungiLibro(l4);

        StrategiaPersistenza s=new StrategiaPersistenzaJson();
        GestorePersistenza p=new GestorePersistenza(s);

        String path="filejsonprova";

        p.salva(l,path);

        Libreria libreria=new Libreria();

        p.carica(libreria,path);

        p.salva(libreria,"filejsonprova2");








}



*/



}
