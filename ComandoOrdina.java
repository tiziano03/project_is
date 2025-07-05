public class ComandoOrdina implements Command {
    private final Libreria libreria;
    private final VistaLibreria vistaLibreria;


    public ComandoOrdina(Libreria libreria, VistaLibreria vistaLibreria) {
        this.libreria = libreria;
        this.vistaLibreria = vistaLibreria;
    }


    @Override
    public void execute(ParsedInput input) {
        if (!input.getArgomentiPosizionali().isEmpty()) throw new IllegalArgumentException();
        if (input.getArgomentiNominali().size() > 1) throw new IllegalArgumentException();
        String criterio = input.getArgomentiNominali().get("campo");

        if (criterio == null) throw new IllegalArgumentException();

        switch (criterio) {
            case "titolo":
                libreria.sort(new ComparatoreLibroTitolo());
                break;
            case "autore":
                libreria.sort(new ComparatoreLibroAutore());
                break;
            case "valutazione":
                libreria.sort(new ComparatoreLibroValutazione());
                break;
            default:
                throw new IllegalArgumentException();
        }


        vistaLibreria.mostraMessaggio("libreria ordinata con successo");


    }


}