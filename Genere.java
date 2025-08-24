public enum Genere{
    NARRATIVA,
    SAGGISTICA,
    TECNICO,
    BIOGRAFIA,
    INFANZIA,
    GENERICI,
    NON_SPECIFICATO;



    public static Genere getGenere(String stringa){
        for(Genere g:Genere.values()){
            if(g.toString().equalsIgnoreCase(stringa)) return g;
        }
        return null;
    }




    public static Genere getDefault() {
        return NON_SPECIFICATO;
    }
}
