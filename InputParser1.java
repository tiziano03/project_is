import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser1 implements InputParser {


    public ParsedInput parse(String input) {
        List<String> argomentiPosizionali=new ArrayList<>();
        Map<String,String> argomentiNominali=new HashMap<>();

        List<String> tokens=tokenizza(input);
        String nomeComando=tokens.get(0);

        for(int i=1;i<tokens.size();i++) {
            String tokenCorrente=tokens.get(i);
            if(tokenCorrente.startsWith("--")) {   //il token è una chiave e si aspetta un valore
                String tokenChiave=tokenCorrente.substring(2); //rimuovo "--"
                if(i+1<tokens.size() && !tokens.get(i+1).startsWith("--")) {
                    String tokenValore=tokens.get(i+1);
                    argomentiNominali.put(tokenChiave,pulisciToken(tokenValore));
                    i++;
                }else throw new SyntaxException("L'argomento "+tokenChiave+" richiede un valore");

            }else argomentiPosizionali.add(pulisciToken(tokenCorrente));



        }



        return new ParsedInput(nomeComando,argomentiPosizionali,argomentiNominali);





    }



    public String pulisciToken(String token){
        if(token.startsWith("\"") && token.endsWith("\""))
            return token.substring(1,token.length()-1);
        return token;
    }




    public List<String> tokenizza(String input) {
        List<String> tokens=new ArrayList<>();
        String regex="\"[^\"]*\"|\\S+";; //un token è una sequenza di caratteri delimitati da apici o da spazi
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(input);
        while(matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }










}
