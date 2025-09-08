package Model;
import Patterns.Observer;
import Patterns.Subject;

import java.util.*;

public class Libreria implements Subject {
    private static Libreria instance;
    private Set<Libro> libri;
    private final List<Patterns.Observer> osservatori;
    private CampoLibro campoOrdinamento;


    //singleton
    protected Libreria() {                //usiamo protected per poter estendere la classe e poter testare i comandi con stub
        this.campoOrdinamento = CampoLibro.TITOLO;
        libri = new TreeSet<Libro>(ComparatoreFactory.creaComparatorDaCampo(this.campoOrdinamento));
        osservatori = new LinkedList<>();
    }

    //singleton
    public static synchronized Libreria getInstance() {
        if (instance == null) {
            instance = new Libreria();
            return instance;
        } else return instance;
    }


    //per rendere il singleton testabile
    public void reset(){
        libri.clear();
        this.campoOrdinamento=CampoLibro.TITOLO;
    }




    //observer
    public void aggiungiAscoltatore(Patterns.Observer observer) {
        if (observer == null) throw new IllegalArgumentException();
        osservatori.add(observer);
    }


    //observer
    public void rimuoviAscoltatore(Patterns.Observer observer) {
        if (observer == null) throw new IllegalArgumentException();
        osservatori.remove(observer);
    }


    public Set<Libro> getLibri() {
        return libri;
    }


    //aggiungi
    public void aggiungiLibro(Libro l) {
        if(l==null) throw new IllegalArgumentException();
        libri.add(l);
        notifica();
    }


    //rimuovi
    public void rimuoviId(UUID id) {
        if(id==null) throw new IllegalArgumentException();
        boolean found = false;
        Iterator<Libro> it = libri.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getId().equals(id)) {
                it.remove();
                found = true;
                break;
            }
        }
        if (!found) throw new IdNonTrovatoException("Id non trovato");
        notifica();
    }


    //toString()
    public String toString() {
        return libri.toString();
    }



    //filtra
    public List<Libro> filtraPerTitolo(String titolo) {
        if(titolo==null) throw new IllegalArgumentException();
        List<Libro> result = new LinkedList<>();
        for (Libro l : libri) {
            if (l.getTitolo().toLowerCase().contains(titolo.toLowerCase())) result.add(l);
        }
        return result;
    }


    //filtra
    public List<Libro> filtraPerAutore(String autore) {
        if(autore==null) throw new IllegalArgumentException();
        List<Libro> result = new LinkedList<>();
        for (Libro l : libri) {
            if (l.getAutore().toLowerCase().contains(autore.toLowerCase())) result.add(l);
        }
        return result;
    }


    //filtra
    public List<Libro> filtraPerGenere(Genere genere) {
        if(genere==null) throw new IllegalArgumentException();
        List<Libro> result = new LinkedList<>();
        for (Libro l : libri) {
            if (l.getGenere().equals(genere)) result.add(l);
        }
        return result;
    }


    //filtra
    public List<Libro> filtraPerStatoLettura(StatoLettura statoLettura) {
        if(statoLettura==null) throw new IllegalArgumentException();
        List<Libro> result = new LinkedList<>();
        for (Libro l : libri) {
            if (l.getStatoLettura().equals(statoLettura)) result.add(l);
        }
        return result;
    }


    //filtra
    public List<Libro> filtraPerValutazione(Valutazione valutazione) {
        if(valutazione==null) throw new IllegalArgumentException();
        List<Libro> result = new LinkedList<>();
        for (Libro l : libri) {
            if (l.getValutazione().equals(valutazione)) result.add(l);
        }
        return result;
    }


    //modifica
    public void modificaIsbn(UUID id, Isbn isbn) {
        if (id == null || isbn == null) throw new IllegalArgumentException();
        Libro l = trovaLibro(id);
        if (l == null) throw new IdNonTrovatoException("Id non trovato");
        l.setIsbn(isbn);
        notifica();
    }


    //modifica
    public void modificaTitolo(UUID id, String titolo) {
        if (id == null || titolo == null) throw new IllegalArgumentException();
        Libro l = trovaLibro(id);
        if (l == null) throw new IdNonTrovatoException("Id non trovato");
        l.setTitolo(titolo);
        notifica();
    }


    //modifica
    public void modificaAutore(UUID id, String autore) {
        if (id == null || autore == null) throw new IllegalArgumentException();
        Libro l = trovaLibro(id);
        if (l == null) throw new IdNonTrovatoException("Id non trovato");
        l.setAutore(autore);
        notifica();
    }


    //modifica
    public void modificaGenere(UUID id, Genere genere) {
        if (id == null || genere == null) throw new IllegalArgumentException();
        Libro l = trovaLibro(id);
        if (l == null) throw new IdNonTrovatoException("Id non trovato");
        l.setGenere(genere);
        notifica();
    }


    //modifica
    public void modificaValutazione(UUID id, Valutazione valutazione) {
        if (id == null || valutazione == null) throw new IllegalArgumentException();
        Libro l = trovaLibro(id);
        if (l == null) throw new IdNonTrovatoException("Id non trovato");
        l.setValutazione(valutazione);
        notifica();
    }


    //modifica
    public void modificaStatoLettura(UUID id, StatoLettura statoLettura) {
        if (id == null || statoLettura == null) throw new IllegalArgumentException();
        Libro l = trovaLibro(id);
        if (l == null) throw new IdNonTrovatoException("Id non trovato");
        l.setStatoLettura(statoLettura);
        notifica();
    }


    public Libro trovaLibro(UUID id) {
        if (id == null) throw new IllegalArgumentException();
        Iterator<Libro> it = libri.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getId().equals(id)) return l;
        }
        return null;
    }

    //ordina
    public void sort(CampoLibro campoOrdinamento) {
        if (campoOrdinamento == null) throw new IllegalArgumentException();
        Set<Libro> nuoviLibri = new TreeSet<>(ComparatoreFactory.creaComparatorDaCampo(campoOrdinamento));
        nuoviLibri.addAll(libri);
        libri = nuoviLibri;
        this.campoOrdinamento = campoOrdinamento;
        notifica();
    }


    public int getDimensione() {
        return libri.size();
    }


    //memento
    public Memento getMemento() {
        return new Memento(libri, campoOrdinamento);
    }


    //memento
    public void setMemento(Memento m) {
        if(m==null) throw new IllegalArgumentException();
        libri = new TreeSet<>(ComparatoreFactory.creaComparatorDaCampo(m.campoOrdinamento));
        libri.addAll(m.stato);
        notifica();
    }


    //observer
    public void notifica() {
        List<LibroDTO> listaLibri = new LinkedList<>();
        for (Libro l : libri) listaLibri.add(new LibroDTO(l));

        for (Observer o : osservatori)
            o.update(listaLibri);


    }


    public static class Memento {
        private final Set<Libro> stato;
        private final CampoLibro campoOrdinamento;


        private Memento(Set<Libro> stato, CampoLibro campoOrdinamento) {
            this.stato = stato;
            this.campoOrdinamento = campoOrdinamento;
        }


    }










}







