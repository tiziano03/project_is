import java.util.List;

public interface VistaLibreria extends Observer{

    /* Generalizza anche la gui che è perfettamente coerente con la nostra applicazione:
    tutti gli oggetti infatti che utilizzano la vista vedono l'interfaccia VistaLibreria.
    Inoltre VistaLibreria è perfettamente coerente per generalizzare sia un'interfaccia a riga
    di comando, sia un'interfaccia grafica!
    E'stato possibile grazie alla separazione degli interessi! La VistaLibreria ha la responsabilità
    di interfacciarsi! Non ha metodi specifici che riguardano soltanto gui o soltanto cli.
    Questo ha concesso l'estensibilità dell'applicazione nel modo in cui si interfaccia all'utente.

    Per quanto riguarda la generazione di ParsedInput, la nostra gui avrebbe dei bottoni per i diversi
    comandi, cliccabili soltanto se tutti i campi necessari per quell'operazione sono stati compilati.
    Verrà automaticamente generato un ParsedInput quando avviene in click.
    Questo ci garantisce che non possono esserci errori di sintassi nell'applicativo: infatti il cliente
    non segue una sintassi, ma compila dei campi!
    Dunque seppur i comandi dipendono dalla sintassi della cli, sono riutilizzabili per la gui.



     */



    public void mostraMessaggio(String messaggio);




}
