public class Isbn {
    private String value;
    private static final String valoreDefault="NON_SPECIFICATO";



    private Isbn(String value){
        this.value = value;
    }



    //Isbn mette a disposizione un contratto preciso: se mi passi un valore vuoto o null
    // ti restituisco una mia istanza con valore NON_SPECIFICATO
    //se invece provi a inserire davvero un valore, deve essere corretto, altrimenti lancia eccezione.

    public static Isbn factory(String value) {

        if(value==null || value.isEmpty())
            return new Isbn(valoreDefault);


        String isbnSenzaTrattini = (value.replace("-", ""));
        if (isbnSenzaTrattini.length() != 13) throw new IllegalArgumentException("L'isbn deve contenere 13 caratteri (trattini esclusi)");
        try {
            Long.parseLong(isbnSenzaTrattini);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("L'isbn deve contentere solo cifre (trattini esclusi)"); // Non contiene solo cifre
        }

        return new Isbn(value);
    }






    public String toString(){
        return value;
    }






}
