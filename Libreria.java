import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Libreria {
    private List<Libro> libri=new ArrayList<>();


    private static Libreria instance=new Libreria();

    public static Libreria getInstance(){
        return instance;
    }

    private Libreria(){}


    public void aggiungiLibro(Libro l){
        libri.add(l);
    }

    public void rimuoviLibro(Libro l){
        libri.remove(l);
    }

    public void sort(Comparator<Libro> strategy){
        Collections.sort(libri, strategy);
    }


    public Memento getMemento(){
        return new Memento(libri);
    }


    public void setMemento(Memento m){
        libri=m.stato;
    }



    public void filtraTitolo( String titolo){}

    public void filtraAutore(String autore){}

    public void filtraGenere(Genere genere){}

    public void filtraValutazione(Valutazione valutazione){}

    public void filtraStatoLettura(StatoLettura statoLettura){}



















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
