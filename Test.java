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


  public void printOutput(CrystalToken output) {
    String tokenVal = output.determinedValue;
    String tokenTy = output.determinedTokenType;

      // print output from getToken()
    System.out.println("Token type: " + tokenTy + "Token value: " + tokenVal );

  }

  // reads the input file from the command line, line by line, and passes them to Lex()
  public static void main(String[] args) {
    boolean commentStarted = false;
    String filename = args[0];
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String tokenLine = reader.readLine();


    while (tokenLine != null) {
      tokenLine = reader.readLine();
      System.out.println(tokenLine); //debugging

      StringTokenizer newTokenLine = new StringTokenizer(tokenLine);

      while (newTokenLine.hasMoreTokens()) {
      // passes line
        if (commentStarted == false){
          CrystalToken token = Lex.getToken(newTokenLine.nextToken());
          printOutput(token);
          if (token.getTokenType().equals("<Comment>")){
            commentStarted = true;
          } else if (token.getTokenType().equals("<>")) {
            throw Lex.LexicalException("comment found with no start");
          }
        } else {
          // comment started, anything that's not an end is not a code bit.
          CrystalToken token = Lex.getToken(newTokenLine.nextToken());
          if (token.getTokenType().equals("<>")){
            commentStarted = false;
          }
        }

      if (commentStarted){
        throw Lex.LexicalException("unfinished comment found");
       }
     }

    }
  }
} // End of Test()
