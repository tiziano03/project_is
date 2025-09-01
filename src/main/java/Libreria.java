
import java.util.*;

public class Libreria implements Subject {
    private Set<Libro> libri;
    private List<Observer> osservatori;
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
    protected Libreria(){                //usiamo protected per poter estendere la classe e poter testare i comandi con stub
        this.campoOrdinamento=CampoLibro.TITOLO;
        libri=new TreeSet<Libro>(ComparatoreFactory.creaComparatorDaCampo(this.campoOrdinamento));
        osservatori=new LinkedList<>();
    }




    //ok observer
    public void aggiungiAscoltatore(Observer observer) {
        if(observer==null) throw new IllegalArgumentException();
        osservatori.add(observer);
    }


    //ok observer
    public void rimuoviAscoltatore(Observer observer) {
        if(observer==null) throw new IllegalArgumentException();
        osservatori.remove(observer);
    }


    public Set<Libro> getLibri(){
        return libri;
    }


    //ok aggiungi
    public void aggiungiLibro(Libro l) {
//        if(l==null) throw new IllegalArgumentException();NUOVO
        libri.add(l);
        notifica();
    }




    //ok rimuovi
    public void rimuoviId(UUID id) {
//        if(id==null) throw new IllegalArgumentException();NUOVO
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
//        if(titolo==null) throw new IllegalArgumentException(); NUOVO
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri) {
            if(l.getTitolo().toLowerCase().contains(titolo.toLowerCase())) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerAutore(String autore){
//        if(autore==null) throw new IllegalArgumentException();NUOVO
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getAutore().toLowerCase().contains(autore.toLowerCase())) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerGenere (Genere genere){
//        if(genere==null) throw new IllegalArgumentException(); NUOVO
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getGenere().equals(genere)) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List <Libro> filtraPerStatoLettura(StatoLettura statoLettura){
//      if(statoLettura==null) throw new IllegalArgumentException();
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getStatoLettura().equals(statoLettura)) result.add(l);
        }
        return result;
    }



    //ok filtra
    public List<Libro> filtraPerValutazione(Valutazione valutazione){
//        if(valutazione==null) throw new IllegalArgumentException();
        List<Libro> result=new LinkedList<>();
        for(Libro l: libri){
            if(l.getValutazione().equals(valutazione)) result.add(l);
        }
        return result;
    }


    //modifica ok
    public void modificaIsbn(UUID id, Isbn isbn){
        if(id==null || isbn==null) throw new IllegalArgumentException();
        Libro l=trovaLibro(id);
        if(l==null) throw new IdNonTrovatoException("Id non trovato");
        l.setIsbn(isbn);
        notifica();
    }




    //modifica ok
    public void modificaTitolo(UUID id, String titolo) {
        if(id==null || titolo==null) throw new IllegalArgumentException();
        Libro l=trovaLibro(id);
        if(l==null) throw new IdNonTrovatoException("Id non trovato");
        l.setTitolo(titolo);
        notifica();
    }



    //modifica ok
    public void modificaAutore(UUID id, String autore) {
        if(id==null || autore==null) throw new IllegalArgumentException();
        Libro l=trovaLibro(id);
        if(l==null) throw new IdNonTrovatoException("Id non trovato");
        l.setAutore(autore);
        notifica();
    }




    //modifica ok
    public void modificaGenere(UUID id, Genere genere){
        if(id==null || genere==null) throw new IllegalArgumentException();
        Libro l=trovaLibro(id);
        if(l==null) throw new IdNonTrovatoException("Id non trovato");
        l.setGenere(genere);
        notifica();
    }





    //modifica ok
    public void modificaValutazione(UUID id, Valutazione valutazione){
        if(id==null || valutazione==null) throw new IllegalArgumentException();
        Libro l=trovaLibro(id);
        if(l==null) throw new IdNonTrovatoException("Id non trovato");
        l.setValutazione(valutazione);
        notifica();
    }





    //modifica ok
    public void modificaStatoLettura(UUID id, StatoLettura statoLettura){
        if(id==null || statoLettura==null) throw new IllegalArgumentException();
        Libro l=trovaLibro(id);
        if(l==null) throw new IdNonTrovatoException("Id non trovato");
        l.setStatoLettura(statoLettura);
        notifica();
    }




    public Libro trovaLibro(UUID id){
        if(id==null) throw new IllegalArgumentException();
        Iterator<Libro> it=libri.iterator();
        while(it.hasNext()){
            Libro l=it.next();
            if(l.getId().equals(id)) return l;
        }
        return null;
    }





    public void sort(CampoLibro campoOrdinamento){
        if(campoOrdinamento==null) throw new IllegalArgumentException();
        Set<Libro> nuoviLibri=new TreeSet<>(ComparatoreFactory.creaComparatorDaCampo(campoOrdinamento));
        nuoviLibri.addAll(libri);
        libri=nuoviLibri;
        this.campoOrdinamento=campoOrdinamento;
        notifica();
    }


    public int getDimensione(){
        return libri.size();
    }


    //ok memento
    public Memento getMemento(){
        return new Memento(libri, campoOrdinamento);
    }




    //ok memento
    public void setMemento(Memento m){
//        if(m==null) throw new IllegalArgumentException(); NUOVO
        libri=new TreeSet<>(ComparatoreFactory.creaComparatorDaCampo(m.campoOrdinamento));
        libri.addAll(m.stato);
        notifica();
    }




    //ok observer
    public void notifica(){
        List<LibroDTO> listaLibri=new LinkedList<>();
        for(Libro l:libri) listaLibri.add(new LibroDTO(l));

        for (Observer o:osservatori)
            o.update(listaLibri);


    }






















    public static class Memento{
        private final Set<Libro> stato;
        private final CampoLibro campoOrdinamento;



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
