import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;





public class ControllerLibreria {
        private final Libreria libreria;
        private final Map<NomeComando, Command> mappaComandi;
//        private LinkedList<Command> history=new LinkedList<>();
//        private LinkedList<Command> redoList=new LinkedList<>();
//        private int maxHistoryLength=10;





        public ControllerLibreria(Map <NomeComando, Command> mappaComandi, Libreria libreria) {
            this.mappaComandi=mappaComandi;
            this.libreria=libreria;
        }



    public void processa(RichiestaComando input){
        NomeComando nomeComando=input.getNomeComando();
        Command comando= mappaComandi.get(nomeComando);
        if(comando==null) throw new UnsupportedOperationException();
        comando.execute(input);
    }


    public List<LibroDTO> Query(CampoLibro campo, String termine){
            List<Libro> libri;
            switch(campo){
                case TITOLO:
                    libri=libreria.filtraPerTitolo(termine);
                    break;
                case AUTORE:
                    libri=libreria.filtraPerAutore(termine);
                    break;
                case GENERE:
                    libri=libreria.filtraPerGenere(Genere.getGenere(termine));
                    break;
                case STATOLETTURA:
                    libri=libreria.filtraPerStatoLettura(StatoLettura.getStatoLettura(termine));
                    break;
                case VALUTAZIONE:
                    libri=libreria.filtraPerValutazione(Valutazione.getValutazione(termine));
                    break;
                default:
                    throw new IllegalArgumentException();
            }


            List<LibroDTO> libriDTO=new LinkedList<>();
            for(Libro libro:libri)libriDTO.add(new LibroDTO(libro));
            return libriDTO;
        }
















}