/* EE422C Assignment #2 submission by
 * Jacob Kim
 * JMK4478
 */

package assignment2;

import java.util.Scanner;

public class Game {
    public Game (Boolean testing, Scanner scan, int guessNumber, String[] colors, int pegNumber){
        Boolean result;
        Boolean play;
        String hint = pegNumber + "B_0W";
        String input;

        play = greeting(scan);
        while (play) {

            String secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
            result = runGame(testing, secretCode, scan, guessNumber, colors, pegNumber);
            System.out.println();
            if (result) {
                System.out.println(secretCode + " -> Result: " + hint + " - You win !!\n");
            }
            else {
                System.out.println("Sorry, you are out of guesses. You lose, boo-hoo.\n");
            }
            System.out.print("Are you ready for another game (Y/N): ");
            input = scan.nextLine();
            play = input.equals("Y");
        }
    }

    public static Boolean runGame(Boolean testing, String secretCode, Scanner scan, int guessNumber, String [] colors, int pegNumber){
        Boolean win = false;
        String[] history = new String[guessNumber];
        String hint;
        int guesses = guessNumber;
        int count = 0;

        printGen(testing, secretCode);
        System.out.println("You have " + guesses + " guesses left.");
        while (guesses > 0) {
            askguess();
            String userIn = scan.nextLine();

            if(userIn.equals("HISTORY")) {
                printHistory(history, guesses);
            }
            else if(!valid(userIn, pegNumber, colors)) {
                System.out.println();
                System.out.println(userIn + " -> INVALID GUESS\n");
            }
            else {
                guesses--;
                hint = check(secretCode, userIn);
                history[count] = userIn + "       " + hint;
                count++;
                if (userIn.equals(secretCode)) {
                    return true;
                }
                else {
                    if (guesses == 0) {
                        return false;
                    }
                    else {
                        printResponse(userIn, hint);
                        System.out.println("You have " + guesses + " guesses left.");
                    }
                }
            }
        }
        return win;

    }

    public static Boolean greeting(Scanner input) {
        System.out.println("Welcome to Mastermind.  Here are the rules.");
        System.out.println();
        System.out.println("This is a text version of the classic board game Mastermind.");
        System.out.println();
        System.out.println("The  computer  will  think  of  a  secret  code.  The  code  consists  of  4" +
                "colored  pegs.  The  pegs  MUST  be  one  of  six  colors:  blue,  green," +
                " orange, purple, red, or yellow. A color may appear more than once in " +
                "the  code.  You  try  to  guess  what  colored  pegs  are  in  the  code  and " +
                "what  order  they  are  in.  After  you  make  a  valid  guess  the  result " +
                "(feedback) will be displayed.\n");
        System.out.println("The  result  consists  of  a  black  peg  for  each  peg  you  have  guessed" +
                "exactly correct (color and position) in your guess.  For each peg in " +
                "the guess that is the correct color, but is out of position, you get " +
                "a  white  peg.  For  each  peg,  which  is  fully  incorrect,  you  get  no " +
                "feedback.\n");
        System.out.println("Only the first letter of the color is displayed. B for Blue, R for " +
                "Red, and so forth. When entering guesses you only need to enter the " +
                "first character of each color as a capital letter.\n");
        System.out.print("You  have  12  guesses  to  figure  out  the  secret  code  or  you  lose  the " +
                "game.  Are you ready to play? (Y/N):");
        String answer = input.nextLine();
        return answer.equals("Y");
    }

    public static String check(String secretCode, String userIn) {
        String user = userIn;
        String code = secretCode;
        String hint = "";
        int black = 0;
        int white = 0;

        for (int i = 0; i < userIn.length(); i++) {
            if (user.charAt(i) == code.charAt(i)) {
                black++;
                user = user.substring(0, i) + '-' + user.substring(i + 1);
                code = code.substring(0, i) + '-' + code.substring(i + 1);
            }
        }
        for (int j = 0; j < userIn.length(); j++) {
            for (int k = 0; k < code.length(); k++) {
                if (user.charAt(j) == '-' || code.charAt(k) == '-') {
                    continue;
                }
                if (user.charAt(j) == code.charAt(k)) {
                    white++;
                    user = user.substring(0, j) + '-' + user.substring(j + 1);
                    code = code.substring(0, k) + '-' + code.substring(k + 1);
                    continue;
                }
            }
        }
        hint += Integer.toString(black) + "B_" + Integer.toString(white) + "W";
        return hint;
    }

    public static Boolean valid(String userIn, int pegN, String[] colors) {

        if (userIn.length() != pegN) {
            return false;
        }
        for (int i = 0; i < userIn.length(); i++) {
            if (Character.isLowerCase(userIn.charAt(i))) {
                return false;
            }
            if (!Character.isLetter(userIn.charAt(i))) {
                return false;
            }
            for (int j = 0; j < colors.length; j++) {
                if (userIn.charAt(i) == colors[j].charAt(0)) {
                    break;
                } else if (userIn.charAt(i) != colors[j].charAt(0) && j == colors.length - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printResponse(String userIn, String hint) {
        System.out.println();
        System.out.println(userIn + " -> Result: " + hint);
        System.out.println();
    }

    public static void askguess() {
        System.out.println("What is your next guess?");
        System.out.println("Type in the characters for your guess and press enter.");
        System.out.print("Enter guess: ");
    }

    public static void printGen(Boolean testingMode, String secretCode) {
        System.out.println();
        System.out.print("Generating secret code ...");
        if (testingMode) {
            System.out.print("(for this example the secret code is " + secretCode + ")");
        }
        System.out.println("\n");
    }

    public static void printHistory(String[] history, int guesses) {
        System.out.println();
        for (int i = 0; i < history.length; i++) {
            if (history[i] == null) {
                break;
            } else {
                System.out.println(history[i]);
            }
        }
        System.out.println();
        System.out.println("You have " + guesses + " guesses left.");
    }
}
