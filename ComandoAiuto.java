import java.util.HashMap;
import java.util.Map;

public class ComandoAiuto implements Command {
    private final VistaLibreria vistaLibreria;
    private final String messaggioAiuto;


    public ComandoAiuto(VistaLibreria vistaLibreria, String messaggioAiuto) {
        this.vistaLibreria = vistaLibreria;
        this.messaggioAiuto = messaggioAiuto;
    }

    @Override
    public boolean execute(ParsedInput input) {
        if (!input.getArgomentiPosizionali().isEmpty() || !input.getArgomentiNominali().isEmpty()) {
            throw new SemanticException("Comando di aiuto malformato");
        }


        vistaLibreria.mostraMessaggio(messaggioAiuto);
        return true;

    }
}