import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ComandoModifica implements Command{
    private final Libreria libreria;



    public ComandoModifica(Libreria libreria) {
        this.libreria=libreria;
    }












    @Override
    public void execute(RichiestaComando input){

        Map<NomeParametro, String> mappa=input.getArgomenti();

        String uuid=mappa.get(NomeParametro.UUID);
        String campo=mappa.get(NomeParametro.CAMPO);
        String valore=mappa.get(NomeParametro.VALORE);


        if(!(mappa.size()==3) || (uuid==null) || (campo==null) || (valore==null))
            throw new IllegalArgumentException("Comando di modifica malformato");


        UUID id=UUID.fromString(uuid);


        CampoLibro c=CampoLibro.getCampoLibro(campo);


        switch (c){
            case ISBN:{
                libreria.modificaIsbn(id,Isbn.factory(valore));
                break;
            }

            case TITOLO:{
                libreria.modificaTitolo(id, valore);
                break;
            }

            case AUTORE:{
                libreria.modificaAutore(id,valore);
                break;
            }

            case VALUTAZIONE:{
                Valutazione valutazione=Valutazione.getValutazione(valore);
                if(valutazione==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libreria.modificaValutazione(id,valutazione);
                break;
            }

            case GENERE:{
                Genere genere=Genere.getGenere(valore);
                if(genere==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libreria.modificaGenere(id,genere);
                break;
            }
            case STATOLETTURA:{
                StatoLettura statoLettura=StatoLettura.getStatoLettura(valore);
                if(statoLettura==null){
                    throw new ValoreNonValidoException("Valore di campo non ammissibile");
                }
                libreria.modificaStatoLettura(id, statoLettura);
                break;
            }
            default:{
                throw new CampoNonValidoException("Campo non ammissibile");
            }
        }







    }





}
