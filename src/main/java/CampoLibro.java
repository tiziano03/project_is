import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum CampoLibro {

    TITOLO(true,true, true, true),
    AUTORE(true,true, true, true),
    ISBN(true, false, false, false),
    GENERE(true, false, true, false),
    STATOLETTURA(true, false, true, false),
    VALUTAZIONE(true, true, true, false);




    private final boolean modificabile;
    private final boolean ordinabile;
    private final boolean filtrabile;
    private final boolean obbligatorio;


    private CampoLibro(boolean modificabile, boolean ordinabile, boolean filtrabile, boolean obbligatorio) {
        this.modificabile = modificabile;
        this.ordinabile = ordinabile;
        this.filtrabile = filtrabile;
        this.obbligatorio = obbligatorio;
    }


    public static Set<String> getCampiOrdinabili(){
        Set<String> campiOrdinabili=new HashSet<>();
        for(CampoLibro c : CampoLibro.values()) {
            if(c.ordinabile) campiOrdinabili.add(c.name());
        }
        return campiOrdinabili;
    }


    public static Set<String> getCampiModificabili(){
        Set<String> campiModificabili=new HashSet<>();
        for(CampoLibro c : CampoLibro.values()) {
            if(c.modificabile) campiModificabili.add(c.name());
        }
        return campiModificabili;
    }



    public static Set<String> getCampiFiltrabili(){
        Set<String> campiFiltrabili=new HashSet<>();
        for(CampoLibro c : CampoLibro.values()) {
            if(c.filtrabile) campiFiltrabili.add(c.name());
        }
        return campiFiltrabili;
    }


    public static Set<String> getCampiObbligatori(){
        Set<String> campiObbligatori=new HashSet<>();
        for(CampoLibro c : CampoLibro.values()) {
            if(c.obbligatorio) campiObbligatori.add(c.name());
        }
        return campiObbligatori;
    }


    public static CampoLibro getCampoLibro(String stringa){
        if(stringa==null) throw new IllegalArgumentException();
        for(CampoLibro c:CampoLibro.values()){
            if(c.toString().equalsIgnoreCase(stringa)) return c;
        }
        return null;
    }












}