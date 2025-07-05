import java.util.List;
import java.util.Scanner;

public class VistaLibreriaRC implements VistaLibreria {
    private Scanner sc;


    public VistaLibreriaRC(){
        sc=new Scanner(System.in);
    }



    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println();
        System.out.println("*/");
        System.out.println(messaggio);
        System.out.println("*/");
        System.out.println();
    }

    @Override
    public void update(List<Libro> libri) {
        if(!libri.isEmpty()) System.out.println("Libreria");
        StringBuilder sb=new StringBuilder();
        int i=0;
        for (Libro l : libri) {
            i++;
            sb.append("Libro numero ").append(i).append(": ").append(l).append("\n");
        }
        System.out.println(sb.toString());
    }


    @Override
    public String prendiInput() {
        System.out.print(">");
        return sc.nextLine();
    }

    @Override
    public void mostraRicerca(List<Libro> libri) {
        if(libri.isEmpty()) return;
        StringBuilder sb=new StringBuilder();
        sb.append("Risultato della ricerca con filtro:" +"\n");
        int i=0;
        for(Libro l: libri){
            i++;
            sb.append("Libro numero ").append(i).append(":").append(l).append("\n");
        }
        mostraMessaggio(sb.toString());
    }


    @Override
    public void presentazione() {
        StringBuilder sb=new StringBuilder();
        sb.append("Benvenuto nel gestore di libreria personale!"+"\n");
        sb.append("Se non hai mai utilizzato l'applicazione digita <aiuto> per le istruzioni");
        mostraMessaggio(sb.toString());
    }


}
