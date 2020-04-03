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
		String tokenVal = output.getTokenType();
		String tokenTy = output.getValue();

		// print output from getToken()
		System.out.println("Token type: " + tokenTy + " Token value: " + tokenVal );
    }

    // reads the input file from the command line, line by line, and passes them to Lex()
    public static void main(String[] args) {
		Lex crystalLex = new Lex();
		boolean commentStarted = false;
		String filename = args[0];

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String tokenLine = "";
			boolean firstTime = true;
			boolean firstTimePerLine = true;

			while (tokenLine != null) {
				if (commentStarted == false && firstTime == false){
					Lex.CrystalToken token = crystalLex.getToken("\n", commentStarted);
					printOutput(token);
				}
				tokenLine = reader.readLine();
				System.out.println(tokenLine); //debugging

				StringTokenizer newTokenLine = new StringTokenizer(tokenLine);
				firstTimePerLine = true;

				while (newTokenLine.hasMoreTokens()) {
					// passes line
					if (commentStarted == false){
						if(firstTimePerLine == false){
							Lex.CrystalToken token = crystalLex.getToken(" ", commentStarted);
							printOutput(token);
						}

						Lex.CrystalToken token = crystalLex.getToken(newTokenLine.nextToken(), commentStarted);
						printOutput(token);
						
						if (token.getTokenType().equals("<Comment>")){
							commentStarted = true;
						} else if (token.getTokenType().equals("<>")) {
							System.out.println("comment found with no start");
						}
					} else {
						// comment started, anything that's not an end is not a code bit.
						Lex.CrystalToken token = crystalLex.getToken(newTokenLine.nextToken(), commentStarted);
						
						if (token.getTokenType().equals("<>")){
							commentStarted = false;
						}
					}

					firstTimePerLine = false;
				}
				firstTime = false;
			}

			if (commentStarted){
				System.out.println("Unfinished comment found.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
    }
} // End of Test()
