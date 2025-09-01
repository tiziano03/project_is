public enum Valutazione{

     UNA_STELLA(1),


     DUE_STELLE(2),



     TRE_STELLE(3),


     QUATTRO_STELLE(4),


     CINQUE_STELLE(5),

     NON_SPECIFICATO(0);




    private int valore;



    private Valutazione(int valore){
        this.valore=valore;
    }



    public int getValore(){return valore;}





    public static Valutazione getValutazione(String stringa){
        if(stringa==null) throw new IllegalArgumentException();
        for(Valutazione v: Valutazione.values()){
            if(v.toString().equalsIgnoreCase(stringa)) return v;
        }
        return null;
    }



    public static Valutazione getDefault() {
        return NON_SPECIFICATO;
    }
}
