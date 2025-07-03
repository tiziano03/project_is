public class ComandoOrdina implements Command {
    private Libreria libreria;


    public ComandoOrdina() {
        this.libreria = Libreria.getInstance();
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


    }


}