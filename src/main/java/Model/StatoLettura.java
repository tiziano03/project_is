package Model;

public enum StatoLettura {
    DA_LEGGERE, LETTURA_IN_CORSO, LETTO, NON_SPECIFICATO;


    public static StatoLettura getStatoLettura(String stringa) {
        if (stringa == null) throw new IllegalArgumentException();
        for (StatoLettura sl : StatoLettura.values()) {
            if (sl.toString().equalsIgnoreCase(stringa)) return sl;
        }
        return null;
    }


    public static StatoLettura getDefault() {
        return NON_SPECIFICATO;
    }
}
