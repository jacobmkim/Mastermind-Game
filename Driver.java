/* EE422C Assignment #2 submission by
 * Jacob Kim
 * JMK4478
 */

package assignment2;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        int guessNumber = GameConfiguration.guessNumber;
        int pegNumber = GameConfiguration.pegNumber;
        String[] colors = GameConfiguration.colors;

        Scanner sc = new Scanner(System.in);
        Boolean testing = false;
        String test = "1";


        if(args.length > 0){
            testing = test.equals(args[0]);
        }
        new Game(testing, sc, guessNumber, colors, pegNumber);
    }
}
