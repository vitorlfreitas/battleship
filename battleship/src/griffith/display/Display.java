// Package
package griffith.display;

// Imports
import griffith.display.utils.DisplayColors;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to hold methods to print messages on the console
 *
 * @author vitorlfreitas
 */
public class Display implements Serializable {

    // Variables and Constants
    public static final String[] ENEMY_NAMES = {"White Beard", "Sea Fury", "Nightmare", "Captain Toothless"};

    public static final String ADVISOR = DisplayColors.YELLOW + "The Crow: " + DisplayColors.RESET_COLOR;

    public static String enemy = ENEMY_NAMES[(int) (Math.random() * ENEMY_NAMES.length)];

    /**
     * Prints a Greeting Message
     */
    public static void greetUser() {
        System.out.println("------- WELCOME CAPTAIN -------");
    }

    /**
     * Display the menu with 4 options and request the user to enter an option
     * @return  option chosen by the user
     */
    public static int mainMenu() {

        // Variable
        int option = -1;

        // Loop to iterate at least one time
        do {
            // Handling exception
            try {
                // Display Menu
                System.out.println();
                System.out.println(DisplayColors.YELLOW + "Main Menu" + DisplayColors.RESET_COLOR);
                System.out.println("1) Start New Battle");
                System.out.println("2) Continue");
                System.out.println("3) How to Play");
                System.out.println("0) Exit");
                System.out.print("> ");

                // Get the value inputted by the user
                option = new Scanner(System.in).nextInt();

                // Checks validation
                if (option < 0 || option > 3) {
                    System.out.println("Enter the Number of the Option");
                }

            }
            // Handle Exceptions
            catch (InputMismatchException e) {
                System.err.println("Enter a Valid Option");
            }

        }
        // Loop until a valid value is entered
        while (option < 0 || option > 3);

        // Return the validated option
        return option;
    }

    /**
     * Method to print the credits and attributions of the game
     */
    public static void showCredit() {
        System.out.println("Thank you for Playing Battleship");
        System.out.println("Developed by " + DisplayColors.BLUE + "Vitor Freitas" + DisplayColors.RESET_COLOR);
        System.out.println("Technologies used: Java");
        System.out.println("13 July, 2024");
    }

    /**
     * Method to print the instruction of the game
     */
    public static void displayHowToPlay() {

        // Prints a Line
        printLine();
        System.out.println(DisplayColors.YELLOW + "INSTRUCTIONS" + DisplayColors.RESET_COLOR);

        System.out.println("\n" + DisplayColors.YELLOW + "How to Win:" + DisplayColors.RESET_COLOR);
        System.out.println("The first player to lose all their ships loses the game.");

        System.out.println("\n" + DisplayColors.YELLOW + "How to Play:" + DisplayColors.RESET_COLOR);
        System.out.println(
                """
                        During play the players take turns making a shot at the opponent's board, by choosing where \
                        to shoot, (e.g., D5). \
                        
                        The game will display if it hits a ship or missed the attack \

                        If the player has hit the last remaining square of a ship, \
                        the ship will sink and the opponent loses a ship \
                        
                        The first player to sunk all the opponent's ship is the winner""");

        System.out.println("\n" + DisplayColors.YELLOW + "The fleet:" + DisplayColors.RESET_COLOR);
        System.out.println("1 x Aircraft carrier - 5 squares");
        System.out.println("1 x Battleship - 4 squares");
        System.out.println("1 x Cruiser - 3 squares");
        System.out.println("2 x Destroyers - 2 squares each");
        System.out.println("2 x Submarines - 1 square each");

        System.out.print("\nPress " + DisplayColors.YELLOW + "\"Enter\"" + DisplayColors.RESET_COLOR + " to Return");
        // Wait for the user enter any key
        new Scanner(System.in).nextLine();

        // Prints a Line
        printLine();
    }

    /**
     * Display a message of welcoming
     */
    public static void displayIntro() {

        String captainName = "";

        try {
            printLine();
            System.out.println(DisplayColors.YELLOW + "Crew: " + DisplayColors.RESET_COLOR + "\"Welcome Aboard Captain\"\n");

            // Wait 1 seconds  to run this method
            Thread.sleep(1500);

            System.out.println(DisplayColors.YELLOW + "Crew: " + DisplayColors.RESET_COLOR + "\"My Name is Jack, but you can call me \"The Crow\"! I will be your advisor on this journey\"\n");

            // Wait 1 seconds  to run this method
            Thread.sleep(1500);

            System.out.println(ADVISOR + "\"I can't wait to navigate Captain!\"\n");

            // Wait 2 seconds  to run this method
            Thread.sleep(1500);

        }
        // Handle Exceptions
        catch (InterruptedException e) {
            System.err.println("An Interrupted Exception has been thrown: " + e.getMessage());
        }

    }

    /**
     * Display the message to let the user that they are going to place the ships
     */
    public static void displayPlacementInstructions() {

        // Handle Exceptions
        try {

            System.out.println(Display.ADVISOR + "\"Captain, we need to get ready for the combat!\"\n");

            // Wait 2 seconds  to run this method
            Thread.sleep(2000);

            System.out.println(ADVISOR + "Let's Place our Ships on the Sea\n");

        }
        // Catch Exceptions if thrown
        catch (InterruptedException e) {

            System.err.println("An Interrupted Exception has been thrown: " + e.getMessage());
        }
    }
    /**
     * Method to print a line on the console
     */
    public static void printLine() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Display message to alert the user that the combat is about to start
     */
    public static void displayBattleBeginMessage() {


        try {
            // Prints a line
            System.out.println();

            // Wait for 1 second
            Thread.sleep(1500);

            System.out.println(ADVISOR + "\"Well Done, Captain!\"");

            // Wait for 1 second
            Thread.sleep(1500);

            System.out.println();
            System.out.println(ADVISOR + "\"All the Ships are Ready for the combat!\"");


            // Wait for 1 second
            Thread.sleep(1500);

            System.out.println();
            System.out.println(ADVISOR + "\"Captain, I saw an enemy approaching, it looks like it is the " + DisplayColors.YELLOW + enemy + DisplayColors.RESET_COLOR + "\"");

            System.out.println("\nPress " + DisplayColors.YELLOW + "\"Enter\"" + DisplayColors.RESET_COLOR + " to Start the Battle");
            // Wait for the user enter any key
            new Scanner(System.in).nextLine();

            // Break line
            System.out.println();


        }
        // Handle Exception
        catch (InterruptedException e) {

            System.err.println("An Interrupted Exception has been thrown: " + e.getMessage());

        }

    }

    /**
     * Display results when the game is over
     * @param isVictory boolean value to inform if the user win or lose
     */
    public static void displayResults(boolean isVictory) {

        // Check if it was a victory
        if (isVictory) {
            System.out.println("\n--- VICTORY ---");
            System.out.println(Display.ADVISOR + "Congratulations, Captain! We have sunk all the opponent's Ships.");

        }
        else {

            System.out.println("\n--- GAME OVER ---");
            System.out.println(Display.ADVISOR + "\"We lost this fight but we haven't lost the WAR!\"");
            System.out.println(Display.ADVISOR + "\"I am sure we will win the next time\"\n");
            System.out.println(DisplayColors.RED + "You Lost, all your ships has been sunk" + DisplayColors.RESET_COLOR);
        }
    }
}
