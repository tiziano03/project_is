import java.util.List;

public interface VistaLibreria extends Observer{


    public void mostraMessaggio(String messaggio);


    public String prendiInput();

    public void mostraRicerca(List<Libro> libri);


    public void presentazione();

}
