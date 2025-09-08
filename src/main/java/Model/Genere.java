package Model;

public enum Genere {
    NARRATIVA,
    SAGGISTICA,
    TECNICO,
    BIOGRAFIA,
    INFANZIA,
    GENERICI,
    NON_SPECIFICATO;


    public static Genere getGenere(String stringa) {
        if (stringa == null) throw new IllegalArgumentException();
        for (Genere g : Genere.values()) {
            if (g.toString().equalsIgnoreCase(stringa)) return g;
        }
        return null;
    }


    public static Genere getDefault() {
        return NON_SPECIFICATO;
    }
}
