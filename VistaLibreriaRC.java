import java.util.List;
import java.util.Scanner;

public class VistaLibreriaRC implements VistaLibreria {
    private final Scanner sc;


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



    public String prendiInput() {
        String input="";
        while(input.isEmpty()) {
            System.out.print(">");
            input = sc.nextLine();
        }
        return input;
    }






}
