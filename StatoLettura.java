public enum StatoLettura {
    DA_LEGGERE, LETTURA_IN_CORSO, LETTO;


    public static StatoLettura getStatoLettura(String stringa){
        for(StatoLettura sl:StatoLettura.values()){
            if(sl.toString().equalsIgnoreCase(stringa)) return sl;
        }
        return null;
    }


    }
