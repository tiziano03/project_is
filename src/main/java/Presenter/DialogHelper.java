package Presenter;

import Model.CampoLibro;

import java.util.Arrays;
import java.util.Set;

public class DialogHelper {
    private final VistaLibreriaRC vista;


    public DialogHelper(VistaLibreriaRC vista) {
        this.vista = vista;
    }


    // --- METODI HELPER ---

    public boolean haConfermato() {
        vista.mostraMessaggio("Digita \"conferma\" per confermare l'operazione," +
                " oppure digita \"annulla\" per annullarla");

        String input = vista.prendiInput();
        if (input.equalsIgnoreCase("annulla")) {
            vista.mostraMessaggio("Operazione annullata!");
            return false;
        }
        while (!input.equalsIgnoreCase("conferma")) {
            vista.mostraMessaggioErrore("Input non corretto. Riprova.");
            input = vista.prendiInput();
            if (input.equalsIgnoreCase("annulla")) {
                vista.mostraMessaggio("Operazione annullata");
                return false;
            }
        }
        return true;
    }


    public CampoLibro prendiCampo(Set<String> campiAmmissibili) {
        vista.mostraMessaggio("Input ammissibili: " + campiAmmissibili);
        String input = vista.prendiInput();
        if (input.equalsIgnoreCase("annulla")) {
            vista.mostraMessaggio("Operazione annullata");
            return null;
        }
        while (!campiAmmissibili.contains(input.toUpperCase())) {
            vista.mostraMessaggioErrore("Input non corretto. Riprova");
            input = vista.prendiInput();
            if (input.equalsIgnoreCase("annulla")) {
                vista.mostraMessaggio("Operazione annullata");
                return null;
            }
        }
        return CampoLibro.getCampoLibro(input);
    }


    public <T extends Enum<T>> String chiediValoreEnumOpzionale(Class<T> enumClass, String valoreDefault) {
        vista.mostraMessaggio("Valori ammissibili: " + Arrays.toString(enumClass.getEnumConstants()));

        while (true) {
            String input = vista.prendiInput();
            if (input.isEmpty()) input = valoreDefault;
            if (input.equalsIgnoreCase("annulla")) {
                vista.mostraMessaggio("Operazione annullata");
                return null;
            }

            if (isValidEnumValue(input, enumClass)) return input;

            vista.mostraMessaggioErrore("Attenzione: \"" + input + "\" non è un valore ammissibile. Riprova.");
        }

    }


    public <T extends Enum<T>> boolean isValidEnumValue(String input, Class<T> enumClass) {
        for (T enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(input))
                return true;
        }
        return false;
    }


    public <T extends Enum<T>> String chiediValoreEnumObbligatorio(Class<T> enumClass) {
        vista.mostraMessaggio("Valori ammissibili: " + Arrays.toString(enumClass.getEnumConstants()));

        while (true) {
            String input = vista.prendiInput();
            if (input.equalsIgnoreCase("annulla")) {
                vista.mostraMessaggio("Operazione annullata");
                return null;
            }

            if (isValidEnumValue(input, enumClass)) return input;

            vista.mostraMessaggioErrore("Attenzione: \"" + input + "\" non è un valore ammissibile. Riprova.");
        }

    }


    public String chiediValoreObbligatorio() {
        String input = vista.prendiInput();
        if (input.equalsIgnoreCase("annulla")) {
            vista.mostraMessaggio("Operazione annullata");
            return null;
        }
        while (input.isEmpty()) {
            vista.mostraMessaggioErrore("L'input non può essere vuoto, riprova.");
            input = vista.prendiInput();
            if (input.equalsIgnoreCase("annulla")) {
                vista.mostraMessaggio("Operazione annullata");
                return null;
            }
        }
        return input;
    }


    public String chiediValoreOpzionale(String valoreDefault) {
        String input = vista.prendiInput();
        if (input.equalsIgnoreCase("annulla")) {
            vista.mostraMessaggio("Operazione annullata");
            return null;
        }
        if (input.isEmpty()) return valoreDefault;
        return input;
    }

    public boolean isbnValido(String isbn) {
        if (isbn.isEmpty()) return true;
        String isbnSenzaTrattini = (isbn.replace("-", ""));
        if (isbnSenzaTrattini.length() != 13) return false;

        try {
            Long.parseLong(isbnSenzaTrattini);
        } catch (NumberFormatException e) {
            return false; // Non contiene solo cifre
        }

        return true;
    }


    public int prendiNumeroIntero() {
        while (true) {
            String input = vista.prendiInput();
            int res;
            try {
                res = Integer.parseInt(input);
                return res;
            } catch (NumberFormatException e) {

                vista.mostraMessaggioErrore("Input scorretto. Riprova");
            }

        }

    }

}
