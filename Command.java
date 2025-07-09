public interface Command {


    public boolean execute(ParsedInput parsedInput) throws PersistenceException;


}
