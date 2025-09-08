package Presenter;
import Patterns.Observer;


public interface VistaLibreriaRC extends Observer {



    void mostraMessaggio(String messaggio);


    void mostraMessaggioSuccesso(String messaggio);


    void mostraMessaggioErrore(String messaggio);


    String prendiInput();

    void mostraMessaggioQuery(String messaggio);


    void mostraMenu(String messaggio);


}
