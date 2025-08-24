
import java.util.*;

public class Libreria implements Subject {
    private Set<Libro> libri;
    private final List<Observer> osservatori;
    private static Libreria instance;
    private CampoLibro campoOrdinamento;


    //singleton ok
    public static synchronized Libreria getInstance(){
        if(instance==null){
            instance=new Libreria();
            return instance;
        }
        else return instance;
    }




    //singleton ok
    private Libreria(){
        this.campoOrdinamento=CampoLibro.TITOLO;
        libri=new TreeSet<Libro>(ComparatoreFactory.creaComparatorDaCampo(this.campoOrdinamento));
        osservatori=new LinkedList<>();
    }




    //ok observer
    public void aggiungiAscoltatore(Observer observer) {
        osservatori.add(observer);
    }


    //ok observer
    public void rimuoviAscoltatore(Observer observer) {
        osservatori.remove(observer);
    }


    //ok aggiungi
    public void aggiungiLibro(Libro l) {
        libri.add(l);
        notifica();
    }




    //ok rimuovi
    public void rimuoviId(UUID id) {
        boolean found=false;
        Iterator<Libro> it=libri.iterator();
        while(it.hasNext()){
            Libro l=it.next();
            if(l.getId().equals(id)){
                it.remove();
                found=true;
                break;
            }
        }
        if(!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }



   /*
    public Libro getLibro(UUID id) {
        if(!libri.containsKey(id)) throw new IdNonTrovatoException("Id non trovato");
        return libri.get(id);
    }



    public void sostituisciLibro(Libro l){
        if(libri.get(l.getId())==null) throw new IdNonTrovatoException("Id non trovato");
        libri.put(l.getId(),l);
    }
*/

    //ok toString()
    public String toString(){
        return libri.toString();
    }


/*
    //dammi i libri sottoforma di lista con comparatore
    public Collection<Libro> getLibri(){
        return this.libri;
    }

 */


    //ok filtra
    public List<Libro> filtraPerTitolo(String titolo){
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri) {
            if(l.getTitolo().toLowerCase().contains(titolo.toLowerCase())) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerAutore(String autore){
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getAutore().toLowerCase().contains(autore.toLowerCase())) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerGenere (Genere genere){
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getGenere().equals(genere)) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerStatoLettura(StatoLettura statoLettura){
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getStatoLettura().equals(statoLettura)) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerValutazione(Valutazione valutazione){
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getValutazione().equals(valutazione)) result.add(l);
        }
        return result;
    }


    //modifica ok
    public void modificaIsbn(UUID id, String isbn){
        boolean found=false;
        Iterator<Libro> it=libri.iterator();
        while(it.hasNext()){
            Libro l=it.next();
            if(l.getId().equals(id)){
                l.setIsbn(isbn);
                found=true;
                break;
            }
        }
        if(!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }




    //modifica ok
    public void modificaTitolo(UUID id, String titolo) {
        boolean found = false;
        Iterator<Libro> it = libri.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getId().equals(id)) {
                l.setTitolo(titolo);
                found = true;
                break;
            }
        }
        if (!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }



    //modifica ok
    public void modificaAutore(UUID id, String autore) {
        boolean found = false;
        Iterator<Libro> it = libri.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getId().equals(id)) {
                l.setTitolo(autore);
                found = true;
                break;
            }
        }
        if (!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }




    //modifica ok
    public void modificaGenere(UUID id, Genere genere){
        boolean found=false;
        Iterator<Libro> it=libri.iterator();
        while(it.hasNext()){
            Libro l=it.next();
            if(l.getId().equals(id)){
                l.setGenere(genere);
                found=true;
                break;
            }
        }
        if(!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }





    //modifica ok
    public void modificaValutazione(UUID id, Valutazione valutazione){
        boolean found=false;
        Iterator<Libro> it=libri.iterator();
        while(it.hasNext()){
            Libro l=it.next();
            if(l.getId().equals(id)){
                l.setValutazione(valutazione);
                found=true;
                break;
            }
        }
        if(!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }





    //modifica ok
    public void modificaStatoLettura(UUID id, StatoLettura statoLettura){
        boolean found=false;
        Iterator<Libro> it=libri.iterator();
        while(it.hasNext()){
            Libro l=it.next();
            if(l.getId().equals(id)){
                l.setStatoLettura(statoLettura);
                found=true;
                break;
            }
        }
        if(!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }




    //ordina
    public void sort(CampoLibro campoOrdinamento){
        Set<Libro> nuoviLibri=new TreeSet<>(ComparatoreFactory.creaComparatorDaCampo(campoOrdinamento));
        nuoviLibri.addAll(libri);
        libri=nuoviLibri;
        this.campoOrdinamento=campoOrdinamento;
        notifica();
    }




    //ok memento
    public Memento getMemento(){
        return new Memento(libri, campoOrdinamento);
    }




    //ok memento
    public void setMemento(Memento m){
        libri=new TreeSet<>(ComparatoreFactory.creaComparatorDaCampo(m.campoOrdinamento));
        libri.addAll(m.stato);
        notifica();
    }




    //ok observer
    public void notifica(){
        for (Observer o:osservatori)
            o.update(libri);
    }






















    public static class Memento{
        private Set<Libro> stato;
        private CampoLibro campoOrdinamento;



        private Memento(Set <Libro> stato, CampoLibro campoOrdinamento){
            this.stato=stato;
            this.campoOrdinamento=campoOrdinamento;
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
