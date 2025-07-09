public class ComandoNonTrovato implements Command{
    private final VistaLibreria vistaLibreria;


    public ComandoNonTrovato(VistaLibreria vistaLibreria) {
        this.vistaLibreria = vistaLibreria;
    }


    @Override
    public boolean execute(ParsedInput parsedInput) {
        vistaLibreria.mostraMessaggio("Attenzione! Comando non trovato.");
        return true;
    }
}
