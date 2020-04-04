import java.lang.Character;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/* CSC266 Programming Assignment 1
 *
 * Bushra Tasneem
 * Sarah Abowitz
 * Jamaya (May) Smith
 *
 *  Language Name: CrystalGem
 *
 * Program for lexical analyzer
 *
 */

/*
 * The purpose of this class is to print the output of getToken(token) to stdout.
 */
public class Lex {
    boolean commentStarted = false;

    public static void main(){
      // we may not need this? wild if true
      //
      // for each lexeme:
      //   if (commentStarted = false)
      // Token token = getToken(lexeme)
      // list.add(token.value)
      // else getToken(lexeme)
      // if commentStarted true throw LexicalException
    }

    // returns the next token in the input as an object with a token type and a token value
    public CrystalToken getToken(String token, boolean inComment) {
		// StringTokenizer inputTokens = new StringTokenizer(token);
		//
		// while (inputTokens.hasMoreTokens()) {
		//
		//   String token2 = inputTokens.nextToken();

		String determinedValue = token;
		String determinedTokenType = tokenType(token);

		if (determinedTokenType.equals("error") && inComment == false) { // default case - lexeme does not match any defined types
      System.out.println("Lexical error identified.");
			return null;
		}

		CrystalToken newToken = new CrystalToken(determinedValue, determinedTokenType);
		//    Test().printOutput(newToken);

		//  }

		return newToken;
    }

    // determines the type of token
    public String tokenType(String token) {
      String result = "";

      // Checking if token is a keyword
      String[] keywords = { "number", "string", "vector" ,"print" , "strcat",
                "true", "false" , "function" , "return" , "void",
                "main" , "if" , "else" , "while" , "null" };

      for (String k : keywords){
        if (token.equals(k)){return "<Keyword>";}
      }


      //Bushra doing identifiers, constant numbers and constant strings

      // identifiers
      char firstChar = token.charAt(0);
      if (Character.isLetter(firstChar)){
          boolean isId = true;
          for (int i=1;i<token.length(); i++){
          if (Character.isJavaIdentifierPart(token.charAt(i)) == false){
            isId = false;
          }
          if (isId){return "<Identifier>";}
          }
      }

      // constant numbers
      Character firstNum = token.charAt(0);
      String subToken = token.substring(1);
      // separately check the first character of the token
      if ( ( Character.isDigit(firstNum) ) || ( firstNum.equals("-")) ) {
          boolean negDigFirst = true;
          boolean correctDot = subToken.indexOf(".")==subToken.lastIndexOf(".");
          subToken.replace(".","");
          boolean isNum = true;
          if (correctDot==true){
        for (int i=0;i<subToken.length(); i++){
            if (Character.isDigit(subToken.charAt(i)) == false){
          isNum = false;
            }
            if (isNum){return "<Constant Number>";}
        }

          }
      }

      //initialize a boolean negdigfirst that is true if first char of token is "-" or a digit
      //count the number of dots in the token

      //initialize a boolean correctdot equals true if dotcount is 0 or 1
      // if negdigfirst & correctdot are both true, we iterate over the rest of the token
      //and check if each char is a digit

      // constant numbers should have a minimum value of -32,768 and a max val of 32767

      /* while (onlyASCII == true && loopOver == true){
            x = char at current index
        boolean isASCII = null;
            isASCII = getASCII(x);
            if (isASCII == false){
            onlyASCII = false;
            if index < arr length, index++
            else loopOver = false
      // } */

      //constant strings
      String[] ASCII = {" ", "!", "#", "$", "%", "&", "'", "(", ")", "*", "+",
            ",", "-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", ":", ";", "<", "=", ">", "?", "@", "A-Z", "a-z",
            "[", "\\", "]", "^", "_", "`", "{", "}", "|", "~"  };


      //Use .split to split token
      Character firstC = token.charAt(0);
      Character lastC = token.charAt(token.length()-1);
      if (firstC.equals('\"') && lastC.equals('\"')){
          int tokenEnd = token.length() - 1;
          String miniToken = token.substring(1,tokenEnd);
          String[] stringArr = miniToken.split("");
          //boolean isConsString = true;
          int indS = 0;
          boolean onlyASCII = true;
          boolean loopOver = false;
          boolean isASCII;
          String x = null;

          while (onlyASCII == true && loopOver == false){
        x = stringArr[indS];
        isASCII = getASCII(x);
        if (isASCII == false){
            onlyASCII = false; }
        if (indS < stringArr.length) {indS++;}
        else { loopOver = true;}
          }
          if (onlyASCII){ return "<Constant String>";}

      }

      //}

      // Checking if token is a punctuation character
      String[] punctuation = { ";" , "[" , "]" , "{" , "}" , "(" , ")" , "," };

      for (String p : punctuation){
        if (token.equals(p)){return "<Punctuation character>";}
      }

      // Sarah covering whitespace + comments
      String[] operators = {"=", "+", "-", "/", "*", "<", ">", "<=", ">=", "==",
                "!=", "!" , "&&", "||"};

      for (String o : operators){
        if (token.equals(o)){return "<Operator>";}
      }

      if (token.startsWith("/*")){
          commentStarted = true;
          return "<Comment>";
      }

      if (token.endsWith("*/")){
          commentStarted = false;
          return "<>";
      }
      else {
        Pattern pattern = Pattern.compile("[ \t\n\f\r]" );
        Matcher matcher = pattern.matcher(token.toString());
      //  boolean found = matcher.matches();

        if ( matcher.matches() == true ) {
          String keep = "<Whitespace>";
          } else {
            result = "error";
        }
      }
      return result;
	}

	public boolean getASCII(String s){
		String[] ASCII = {" ", "!", "#", "$", "%", "&", "'", "(", ")", "*", "+",
				",", "-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", ":", ";", "<", "=", ">", "?", "@", "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M","N", "O", "P", "Q", "R", "S", "T", "U", "V" ,
				"W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r",
				"s", "t", "u", "v", "w", "x", "y", "z", "[", "\\", "]",
				"^", "_", "`", "{", "}", "|", "~" };
		for(String a: ASCII){
			if ( s.equals(a) ){
				return true;
			}
		}
		return false;
	}

	// token class of CrystalGem
	public class CrystalToken {
	    private String value;
	    private String tokenType;

	    public CrystalToken(String v, String t){
        value = v;
        tokenType = t;
      }

	    public String getValue(){ return value; }

	    public String getTokenType(){ return tokenType; }
	}

} // End of class Lex()
