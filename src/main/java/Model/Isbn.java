package Model;

public class Isbn {
    private static final String valoreDefault = "NON_SPECIFICATO";
    private final String value;


    private Isbn(String value) {
        this.value = value;
    }


    //Isbn mette a disposizione un contratto preciso: se mi passi un valore vuoto
    // ti restituisco una mia istanza con valore NON_SPECIFICATO
    //se invece provi a inserire davvero un valore, deve essere corretto, altrimenti lancia eccezione.

    public static Isbn factory(String value) {
        if(value==null) throw new IllegalArgumentException();
        if (value.isEmpty())
            return new Isbn(valoreDefault);


        String isbnSenzaTrattini = (value.replace("-", ""));
        if (isbnSenzaTrattini.length() != 13)
            throw new IllegalArgumentException("L'isbn deve contenere 13 caratteri (trattini esclusi)");
        try {
            Long.parseLong(isbnSenzaTrattini);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("L'isbn deve contentere solo cifre (trattini esclusi)"); // Non contiene solo cifre
        }

        return new Isbn(value);
    }


    public String toString() {
        return value;
    }


}
