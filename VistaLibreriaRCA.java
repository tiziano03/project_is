import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VistaLibreriaRCA implements VistaLibreriaRC{
    private final Scanner sc;
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";




    public VistaLibreriaRCA() {
        this.sc=new Scanner(System.in);
    }



    public void mostraMenu(String menuContent) {
        // --- 1. Definiamo gli stili del Light Box ---
        String LIGHT_BOX_STYLE = WHITE_BACKGROUND + BLACK_TEXT;

        // --- 2. Stampa dell'Header ---
        // Un titolo pulito e moderno, che usa il nostro colore di branding.
        System.out.println(); // Spazio per arieggiare
        mostraHeaderSezione("MENU PRINCIPALE");

        // --- 3. Stampa del Contenuto nel Light Box ---
        String[] lines = menuContent.split("\n");

        // Troviamo la riga più lunga per determinare la larghezza del nostro box.
        int larghezzaMassima = 0;
        for (String line : lines) {
            if (line.length() > larghezzaMassima) {
                larghezzaMassima = line.length();
            }
        }
        // Aggiungiamo un po' di padding per un look più arioso.
        int larghezzaBox = larghezzaMassima + 4;

        // Stampa il bordo superiore del box
        System.out.println(LIGHT_BOX_STYLE + " ".repeat(larghezzaBox) + RESET);

        // Stampa ogni riga del menu, formattata all'interno del box
        for (String line : lines) {
            // Calcoliamo il padding a destra per rendere il box rettangolare
            int paddingDestro = larghezzaBox - line.length() - 2; // 2 per il padding sinistro
            String spaziPadding = " ".repeat(Math.max(0, paddingDestro));

            // Stampa la riga: padding sinistro + testo + padding destro
            System.out.println(LIGHT_BOX_STYLE + "  " + line + spaziPadding + " " + RESET);
        }

        // Stampa il bordo inferiore del box
        System.out.println(LIGHT_BOX_STYLE + " ".repeat(larghezzaBox) + RESET);
        System.out.println(); // Spazio

    }

    // Assicuriamoci che il metodo mostraHeaderSezione esista e sia coerente
    public void mostraHeaderSezione(String titolo) {
        String separatore = "─".repeat(titolo.length()); // Carattere Unicode più sottile
        System.out.println(MAGENTA + BOLD + "› " + titolo + " ‹" + RESET);
        System.out.println(MAGENTA + "  " + separatore + RESET);
    }


    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println(messaggio);
    }


    @Override
    public void mostraMessaggioSuccesso(String messaggio) {
        // Creiamo un "badge" per la parola SUCCESSO per un look più pulito.
        String badge = GREEN_BACKGROUND + BLACK_TEXT + BOLD + " OK " + RESET;
        System.out.println(badge + " " + messaggio);
    }




    @Override
    public void mostraMessaggioErrore(String messaggio) {
        System.out.println(RED + messaggio + RESET);
    }



    @Override
    public String prendiInput() {
        System.out.print(BLUE+">"+RESET);
        return sc.nextLine();
    }




    @Override
    public void mostraMessaggioQuery(String messaggio) {
        System.out.println(YELLOW + messaggio + RESET);
    }



    public void mostraTitolo(String messaggio) {
        System.out.println(MAGENTA + BOLD + messaggio + RESET);
    }




    @Override
    public void update(Set<Libro> libri) {
        mostraTitolo("--- Stato Attuale della Libreria ---");

        if (libri == null || libri.isEmpty()) {
            System.out.println(CYAN + BOLD + "La libreria è vuota." + RESET);
        } else {
            System.out.println(CYAN + BOLD + String.format("La libreria contiene %d libro/i:", libri.size()) + RESET);
            int i=0;
            for(Libro libro: libri){
                String libroFormattato = String.format("  %d. %s", i + 1, "|Titolo: "+libro.getTitolo()+"| Autore: "+libro.getAutore()+"|");
                System.out.println(CYAN + BOLD + libroFormattato + RESET);
                i++;
            }
        }
        mostraTitolo("------------------------------------");
    }



}
