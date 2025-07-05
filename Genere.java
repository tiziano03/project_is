public enum Genere {
    NARRATIVA,
    SAGGISTICA,
    TECNICO,
    BIOGRAFIA,
    INFANZIA,
    GENERICI;



    public static Genere getGenere(String stringa){
        for(Genere g:Genere.values()){
            if(g.toString().equalsIgnoreCase(stringa)) return g;
        }
        return null;
    }















}
