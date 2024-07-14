package griffith.game;

// Imports
import griffith.board.Board;
import griffith.display.Display;
import griffith.display.utils.DisplayColors;
import griffith.ship.Ship;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static griffith.display.Display.displayBattleBeginMessage;

/**
 * Class to execute the loop logic of the game
 *
 * @author vitorlfreitas
 */
public class BattleshipGame {

    // Objects
    private static Board playerBoard;
    private static Board computerBoard;

    // Initiating a Scanner
    private static final Scanner scan = new Scanner(System.in);

    // Constant to hold the name of the file
    private static final String SAVE_FILE = "battleship_save.dat";

    /**
     * Method to start a new game
     */
    public static void start() {

        // Initiates a new Board Obj
        playerBoard = new Board();
        computerBoard = new Board();


        // Displays the Intro of the game
        Display.displayIntro();

        // Display the placement instructions
        Display.displayPlacementInstructions();

        // Print the board
        System.out.println(DisplayColors.YELLOW + "Your Board:" + DisplayColors.RESET_COLOR);
        playerBoard.printGrid(true);

        // Call the method to place all the ships
        placePlayerShips();

        // Call the method to computer places all its ships
        computerBoard.placeComputerShips();

        // Display a message to alert the combat is about to begin
        displayBattleBeginMessage();

        // Variable to control the flow
        boolean playerTurn = true;

        // Display the board to start the battle
        System.out.println(DisplayColors.YELLOW + Display.enemy + "'s Board" + DisplayColors.RESET_COLOR);
        computerBoard.printGrid(false);

        // Loop until one of the players runs out of ships
        while (!playerBoard.allShipsSunk() && !computerBoard.allShipsSunk()) {

            if (playerTurn)
                playerAttack();
            else
                computerAttack();

            // Switch to the next iteration it will be different
            playerTurn = !playerTurn;

            // Saves the game each turn
            saveGame();
        }

        // Display the outcome
        Display.displayResults(computerBoard.allShipsSunk());
    }

    /**
     * Method to place all the player ships
     */
    private static void placePlayerShips() {

        // Arrays
        String[] shipNames = {"Aircraft carrier", "Battleship", "Cruiser", "Destroyer", "Destroyer", "Submarine", "Submarine"};
        int[] shipSizes = {5, 4, 3, 2, 2, 1, 1};

        // Loop until all the ships are placed
        for (int i = 0; i < shipNames.length; i++) {

            // Variable
            boolean placed;

            // Do while
            do {
                // Handle Exceptions
                try {
                    System.out.println();
                    System.out.println("Place your " + shipNames[i] + " (" + shipSizes[i] + " squares):");

                    System.out.print("Enter Row (A-J): ");
                    char row = scan.next().toUpperCase().charAt(0);

                    System.out.print("Enter Column (0-9): ");
                    int col = scan.nextInt();

                    System.out.print("Enter Orientation (H for horizontal, V for vertical): ");
                    char position = scan.next().toUpperCase().charAt(0);

                    // Call the method to place the ship, which will return a boolean value
                    placed = playerBoard.placeShip(new Ship(shipNames[i], shipSizes[i], position), row - 'A', col, position);

                    if (!placed) {
                        System.err.println("Invalid placement. Try again.");
                    }

                }
                // Handle exceptions
                catch (InputMismatchException | StringIndexOutOfBoundsException e) {

                    System.err.println("Invalid input. Please try again.");
                    scan.nextLine(); // Clear the buffer
                    placed = false;

                }

            } while (!placed); // Loop until the ship is in a valid spot

            // Breaks the line
            System.out.println();

            // Display the Board
            System.out.println(DisplayColors.YELLOW + "Your Board:" + DisplayColors.RESET_COLOR);
            playerBoard.printGrid(true);
        }
    }

    /**
     * Method to the player attacks the computer
     */
    private static void playerAttack() {

        // Handle Exceptions
        try {

            // Variables
            boolean validAttack;
            String result = "";

            do {
                try {
                    // Request the user to enter the coordinate
                    System.out.print("\nEnter attack coordinates (e.g., A5): ");
                    String input = scan.next().toUpperCase();

                    // Get the coordinates in integer type
                    int x = input.charAt(0) - 'A'; // Get the first char
                    int y = Integer.parseInt(input.substring(1)); // Get the second char

                    // Checks if the coordinates are valid
                    if (x >= 0 && x < 10 && y >= 0 && y < 10) {

                        // Call the method to attack the enemy
                        result = computerBoard.attack(x, y);

                        // Check the outcome of the method
                        validAttack = !result.equalsIgnoreCase("You have already attacked this spot. Try a different spot");

                        // Check if the attack was a valid attack
                        if (!validAttack) System.out.println("\n" + Display.ADVISOR + "\"" + result + "\"");

                        // Wait for 1 second
                        Thread.sleep(1000);

                    }
                    // Handle Unexpected Inputs
                    else {
                        // Alerts the user
                        System.out.println("Invalid coordinates. Try again.");
                        validAttack = false;
                    }
                }
                // Handles Exceptions
                catch (InputMismatchException | StringIndexOutOfBoundsException | NumberFormatException |
                         InterruptedException e) {

                    System.out.println("Invalid input. Please try again.");
                    scan.nextLine(); // Clear the buffer
                    validAttack = false;

                }

            } while (!validAttack); // Loops until a valid attack is entered

            // Wait for 1 second
            Thread.sleep(1000);

            // Breaks the line
            System.out.println();
            System.out.println(DisplayColors.YELLOW + Display.enemy + "'s Board:" + DisplayColors.RESET_COLOR);
            computerBoard.printGrid(false);

            // Wait for 1.5 second
            Thread.sleep(1500);

            // Display the missed attacks count and ships sunk count
            System.out.println(DisplayColors.YELLOW + "You: " + DisplayColors.RESET_COLOR);
            System.out.println("Missed Attacks: " + computerBoard.getMissCount());
            System.out.println("Ships Sunk: " + computerBoard.getShipsSunk() + "/7\n");

            // Wait for 1.5 second
            Thread.sleep(1500);

            System.out.println(result);

            // Wait for 1.5 second
            Thread.sleep(1500);



        }
        // Handle Exceptions
        catch (InterruptedException e) {
            System.err.println("An Interrupted Exception has been thrown: " + e.getMessage());
        }
    }

    /**
     * Method to the computer generates an attack
     */
    private static void computerAttack() {

        // Variables
        String result = "";
        Random rand = new Random();
        boolean validAttack;

        // Handle Exceptions
        try {
            do {
                try {
                    // Generates two coordinates randomly
                    int x = rand.nextInt(10);
                    int y = rand.nextInt(10);

                    // Attack the enemy and check if it is a valid attack
                    result = playerBoard.enemyAttack(x, y);

                    validAttack = !result.equals("You have already attacked this spot.");

                    // If it is valid
                    if (validAttack) {
                        System.out.println();
                        System.out.println(Display.ADVISOR + "\"Enemy attacked: " + (char) (x + 'A') + y + "\"");
                        System.out.println();

                    }
                }
                // Handle exceptions
                catch (ArrayIndexOutOfBoundsException e) {
                    // Ensure valid attack coordinates
                    validAttack = false;
                }
            } while (!validAttack);

            // Print the user's board updated with the opponent's attack
            System.out.println(DisplayColors.YELLOW + "Your Board:" + DisplayColors.RESET_COLOR);
            playerBoard.printGrid(true);

            // Wait for 1.5 second
            Thread.sleep(1500);

            // Display the missed attacks count and ships sunk count
            System.out.println(DisplayColors.YELLOW + Display.enemy + ":" + DisplayColors.RESET_COLOR);
            System.out.println("Missed Attacks: " + playerBoard.getMissCount());
            System.out.println("Ships Sunk: " + playerBoard.getShipsSunk() + "/7");
            System.out.println();

            // Waits for 1 second
            Thread.sleep(1000);

            // Print the outcome of the round
            System.out.println(result);
        }
        // Handle Exceptions
        catch (InterruptedException e) {
            System.err.println("An Interrupted Exception has been thrown: " + e.getMessage());
        }
    }

    /**
     * Method to continue a saved game
     */
    public static void continueGame() {

        // Check if there is a game saved
        if (!saveFileExists()) {
            System.out.println("No saved game found. Starting new game.");
            // If not, start a new game
            start();
            return;
        }

        // Loads the game
        loadGame();

        boolean playerTurn = true;

        // Display the current game state
        displayGameState(playerTurn);

        // Game Loop
        while (!playerBoard.allShipsSunk() && !computerBoard.allShipsSunk()) {
            if (playerTurn)
                playerAttack();
            else
                computerAttack();

            playerTurn = !playerTurn;

            // Saves the game each round
            saveGame();
        }

        // Displays the Outcome
        Display.displayResults(computerBoard.allShipsSunk());
    }

    /**
     * Display the status of the game when it was stopped
     * @param playerTurn check who is playing first
     */
    private static void displayGameState(boolean playerTurn) {

        // Breaks the line
        System.out.println();

        // Prints the User's Board
        System.out.println(DisplayColors.YELLOW + "Your Board:" + DisplayColors.RESET_COLOR);
        playerBoard.printGrid(true);

        System.out.println();

        // Prints the Opponent's Board
        System.out.println(DisplayColors.YELLOW + Display.enemy + "'s Board:" + DisplayColors.RESET_COLOR);
        computerBoard.printGrid(false);

        // Display missed attacks and ships sunk of the player
        if (playerTurn) {
            System.out.println(DisplayColors.YELLOW + "You:" + DisplayColors.RESET_COLOR);
            System.out.println("Missed Attacks: " + computerBoard.getMissCount());
            System.out.println("Ships Sunk: " + computerBoard.getShipsSunk() + "/7");

        }
        // Display missed attacks and ships sunk of the opponent
        else {
            System.out.println(DisplayColors.YELLOW + Display.enemy + ":" + DisplayColors.RESET_COLOR);
            System.out.println("Missed Attacks: " + playerBoard.getMissCount());
            System.out.println("Ships Sunk: " + playerBoard.getShipsSunk() + "/7");
        }
    }

    /**
     * Check if there's a file on the current directory
     * @return boolean value
     */
    private static boolean saveFileExists() {
        File saveFile = new File(SAVE_FILE);
        return saveFile.exists();
    }

    /**
     * Saves the process of the game
     */
    private static void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            // Writes the Objects on the file
            out.writeObject(playerBoard);
            out.writeObject(computerBoard);
        }
        // Handle Exceptions
        catch (IOException e) {
            System.err.println("Error Saving Game: " + e.getMessage()); // Prints the Error
        }
    }

    /**
     * Load the process of the previous game
     */
    private static void loadGame() {
        // Try-with-Resources Statement
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {

            /*
                Creates an ObjectInputStream
                ObjectInputStream: Deserialize objects previously serialized using ObjectOutputStream.
                FileInputStream: Obtains input bytes from a file in a file system.
                SAVE_FILE: Holds the path to the file where the game  is saved.
             */

            playerBoard = (Board) in.readObject();
            computerBoard = (Board) in.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.err.println("Error Loading Game: " + e.getMessage());

        }
    }
}
