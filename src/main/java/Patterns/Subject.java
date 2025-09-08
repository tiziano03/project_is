package Patterns;


public interface Subject {


    void notifica();

    void aggiungiAscoltatore(Observer observer);

    void rimuoviAscoltatore(Observer observer);


}
