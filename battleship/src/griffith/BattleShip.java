// Package
package griffith;

// Imports
import griffith.display.Display;
import griffith.game.BattleshipGame;

/**
 * Main class of the Battleship Game
 * This class handle the main method to run
 *
 * @author vitorlfreitas
 */
public class BattleShip {

    /**
     * Main method to run the AssignmentTwo_3152612 project
     */
    public static void main(String[] args) {

        // Variable to select an option
        int option;

        // Display first message on the console
        Display.greetUser();

        // Loop of the game until the user want to finalize the game
        do {
            // Receives the option
            option = Display.mainMenu();

            // Run the option chosen from the menu
            run(option);

        }
        // Iw will loop until the user chose to exit
        while (option != 0);

        // Display credits
        Display.showCredit();

    }

    /**
     * Method to run the game
     * @param option option chosen by the user
     */
    private static void run(int option) {
        switch (option) {
            case 1 ->
                // Start New Game
                BattleshipGame.start();

            case 2 ->
                // Continue Game
                BattleshipGame.continueGame();

            case 3 ->
                // Display Instructions
                Display.displayHowToPlay();

            default -> System.out.println("Exiting...");

        }
    }
    /*
        For this project, I used the Serializable interface to convert the objects into a byte stream,
        which can be reverted back into a copy of the object

        Thanks so much :)

     */
}
