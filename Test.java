import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

/* CSC266 Programming Assignment 1
 *
 * Bushra Tasneem
 * Sarah Abowitz
 * Jamaya (May) Smith
 *
 *  Language Name: CrystalGem
 *
 * Program to test lex analyzer
 *
 */

/*
 * This purpose of this class is to test our lexical analyzer
 */
public class Test {

    public static void printOutput(Lex.CrystalToken output) {
	String tokenVal = output.getValue();
	String tokenTy = output.getTokenType();

		// print output from getToken()
		System.out.println("Token type: " + tokenTy + " Token value: " + tokenVal );
    }

    // reads the input file from the command line, line by line, and passes them to Lex()
    public static void main(String[] args) {
		Lex crystalLex = new Lex();
		boolean commentStarted = false;
		boolean stringStarted = false;
		String literalString="";
		String filename = args[0];

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String tokenLine = "";
			
			while (tokenLine != null) {
				tokenLine = reader.readLine();
			//	System.out.println(tokenLine); //debugging

				StringTokenizer newTokenLine = new StringTokenizer(tokenLine);
				while (newTokenLine.hasMoreTokens()) {
					// passes line
					if (commentStarted == false){
						
						String lexeme = newTokenLine.nextToken();
						// System.out.println(lexeme);
						Lex.CrystalToken token = crystalLex.getToken(lexeme, commentStarted, stringStarted);

						if (stringStarted){
							literalString = literalString + " "+token.getValue();
							if(token.getValue().endsWith("\"")){
								stringStarted = false;
								System.out.println("Token type: " + literalString + " Token value: <Constant String>");	
							}

						} else if (token.getTokenType().equals("<Constant String>") && token.getValue().endsWith("\"")
						     == false){
								stringStarted = true;
								literalString = token.getValue();

						} else if (token.getTokenType().equals("<Comment>")){
							commentStarted = true;
						} else if (token.getTokenType().equals("<>")) {
							System.out.println("comment found with no start");
						} else if (token.getTokenType().equals("<Singleton Comment>")){ 
							// just. do nothing. this is   n o t h i n g .
						} else { printOutput(token); }
					} else {
						// comment started, anything that's not an end is not a code bit.
						Lex.CrystalToken token = crystalLex.getToken(newTokenLine.nextToken(), commentStarted, stringStarted);

						if (token.getTokenType().equals("<>")){
							commentStarted = false;
						}
					}
				}
			}

			if (commentStarted){
				System.out.println("Unfinished comment found.");
			} else if (stringStarted){
				System.out.println("Unfinished constant string found.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

    }
} // End of Test()
