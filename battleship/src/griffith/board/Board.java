// Package
package griffith.board;

// Imports
import griffith.display.Display;
import griffith.display.utils.DisplayColors;
import griffith.ship.Coordinates;
import griffith.ship.Ship;

import java.io.Serializable;
import java.util.Random;

/**
 * Class Board of the game which contains one of the main attributes and methods of the game
 *
 * @author vitorlfreitas
 */
public class Board implements Serializable {

    // Constants
    private static final int DEFAULT_HEIGHT = 10;
    private static final int DEFAULT_WIDTH = 10;

    private char[][] grid;
    private Ship[] ships;

    private int shipCount;
    private int missCount; // Counter for missed attacks
    private int shipsSunk; // Counter for ships sunk

    /**
     * Constructor Method without parameters
     */
    public Board() {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }
    /**
     * Constructor Method with parameters
     */
    public Board(int height, int width) {
        grid = new char[height][width];
        ships = new Ship[7]; // 1 Aircraft carrier, 1 Battleship, 1 Cruiser, 2 Destroyers, 2 Submarines
        shipCount = 0; // Initialize ship counter
        missCount = 0; // Initialize miss counter
        shipsSunk = 0; // Initialize ships sunk counter

        // For loop to create the grid of the board
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '~';
            }
        }
    }

    /**
     * Prints the board, if the parameter passed is true, it will show the ships on the board. Otherwise, not.
     *
     * @param showShips boolean value
     */
    public void printGrid(boolean showShips) {

        // Index at the top of the board
        char y = 'A';

        // Prints the index at the top
        System.out.print("   ");
        for (int j = 0; j < grid[0].length; j++) {
            System.out.print(j + " ");
        }
        // Breaks the line
        System.out.println();

        // Prints the board adding colors to the elements
        for (int i = 0; i < grid.length; i++) {
            System.out.print((y++) + "  "); // Adjust spacing for row labels
            for (int j = 0; j < grid[i].length; j++) {
                if (showShips) {
                    if (grid[i][j] == 'S')
                        System.out.print(DisplayColors.YELLOW + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                    else if (grid[i][j] == 'X')
                        System.out.print(DisplayColors.RED + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                    else if (grid[i][j] == 'O')
                        System.out.print(DisplayColors.WHITE + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                    else
                        System.out.print(DisplayColors.BLUE + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                } else {
                    if (grid[i][j] == 'S')
                        System.out.print(DisplayColors.BLUE + "~ " + DisplayColors.RESET_COLOR); // Hide the ship
                    else if (grid[i][j] == 'X')
                        System.out.print(DisplayColors.RED + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                    else if (grid[i][j] == 'O')
                        System.out.print(DisplayColors.WHITE + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                    else
                        System.out.print(DisplayColors.BLUE + grid[i][j] + " " + DisplayColors.RESET_COLOR);
                }
            }
            // Breaks the line
            System.out.println();
        }
    }

    /**
     * Method to place ship on the board.
     * Validates the spot to check if it is possible to place the ship and return true if the ship was placed successfully.
     *
     * @param ship obj ship
     * @param x coordinate X
     * @param y coordinate Y
     * @param orientation position on the board (H Horizontal, V Vertical)
     * @return boolean value
     */
    public boolean placeShip(Ship ship, int x, int y, char orientation) {

        // Check the orientation
        if (orientation == 'H') {

            // Validations before placing the ship on the board

            if (y + ship.size > grid[0].length) return false;

            // Check if all the spot needed by the ship is available
            for (int j = y; j < y + ship.size; j++)
                if (grid[x][j] != '~') return false;

            for (int j = y; j < y + ship.size; j++) {
                grid[x][j] = 'S';
                // Save the coordinates of all spots used on the Ship's Coordinates Array
                ship.coordinates.add(new Coordinates(x, j));

            }
        }
        // Check the orientation
        else if (orientation == 'V') {

            // Validations before placing the ship on the board

            if (x + ship.size > grid.length) return false;

            // Check if all the spot needed by the ship is available
            for (int i = x; i < x + ship.size; i++)
                if (grid[i][y] != '~') return false;

            // Updates the board
            for (int i = x; i < x + ship.size; i++) {
                grid[i][y] = 'S';
                // Save the coordinates of all spots used on the Ship's Coordinates Array
                ship.coordinates.add(new Coordinates(i, y));
            }

        }
        // Incorrect Position
        else return false;

        // Increments the Ship counter
        ships[shipCount++] = ship;

        // Return a boolean value to indicate if the ship was allocated
        return true;
    }

    /**
     * Method to Computer allocate its ships on the board.
     * Check if the spots are available
     * Loop until all the ships are placed in a valid spot
     */
    public void placeComputerShips() {

        // Initiate a new random
        Random rand = new Random();

        // Array of values for the ship information
        String[] shipNames = {"Aircraft carrier", "Battleship", "Cruiser", "Destroyer", "Destroyer", "Submarine", "Submarine"};
        int[] shipSizes = {5, 4, 3, 2, 2, 1, 1};

        // Loop to add all the ships on the board
        for (int i = 0; i < shipNames.length; i++) {

            boolean placed;
            do {
                int x = rand.nextInt(grid.length);
                int y = rand.nextInt(grid[0].length);
                char orientation = rand.nextBoolean() ? 'H' : 'V';
                placed = placeShip(new Ship(shipNames[i], shipSizes[i], orientation), x, y, orientation);

            } while (!placed);
        }
    }

    /**
     * Method to attack the board that called this method
     * @param x coordinate X
     * @param y coordinate Y
     * @return a message of the attack's status
     */
    public String attack(int x, int y) {

        // Validate the coordinates
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length)
            return DisplayColors.RED + "Invalid coordinates" + DisplayColors.RESET_COLOR;

        // Check if there's a ship on the coordinate
        if (grid[x][y] == 'S') {

            // Updates the Board
            grid[x][y] = 'X';

            // For each to check if a ship has a coordinate of the attack
            for (Ship ship : ships) {

                // Check the coordinates
                if (ship.findShip(x, y)) {

                    // Increment the hit counter
                    ship.gotHitCounts++;

                    // Check if the ship is sunk
                    if (ship.isSunk()) {
                        // Increment the ships sunk counter
                        shipsSunk++;
                        return Display.ADVISOR + "\"Amazing Captain! You sunk the " + ship.name + "! " + shipsSunk + "/" + ships.length + " ships sunk.\"";
                    }
                    // Return the status
                    return Display.ADVISOR + "\"Well Done! You hit the " + ship.name + "!\"";
                }
            }
        }
        else if (grid[x][y] == '~') {

            grid[x][y] = 'O'; // miss
            missCount++; // Increment miss counter
            return Display.ADVISOR + "\"We Missed this time! Let's Shoot Again.";

        }
        else {
            // Already attacked this spot
            return "You have already attacked this spot. Try a different spot";
        }

        // If any of the condition is true, return missed message
        return "You missed.";

    }

    /**
     * Method for update the console with the attacks resume
     * @param x coordinate X
     * @param y coordinate Y
     * @return message of the attack's status
     */
    public String enemyAttack(int x, int y) {

        // Check coordinates
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length)
            return DisplayColors.RED + "Invalid coordinates" + DisplayColors.RESET_COLOR;

        // Check if a ship is hit
        if (grid[x][y] == 'S') {

            // Updates the Board
            grid[x][y] = 'X';

            // Check if any ship has the coordinates of the attack
            for (Ship ship : ships) {

                // Check if hit
                if (ship.findShip(x, y)) {

                    // Increments the hit counter
                    ship.gotHitCounts++;

                    if (ship.isSunk()) {
                        shipsSunk++;
                        return Display.ADVISOR + "\"The Enemy sunk our " + ship.name + "! " + shipsSunk + "/" + ships.length + " ships sunk.\"";
                    }

                    return Display.ADVISOR + "\"Blast! The Enemy hit our " + ship.name + "!\"";
                }
            }
        } else if (grid[x][y] == '~') {

            grid[x][y] = 'O'; // miss
            missCount++; // Increment miss counter
            return Display.ADVISOR + "\"Close Call! He's missed this time! Let's Shoot!\"";

        } else {

            // Already attacked this spot
            return "You have already attacked this spot.";
        }

        return "You missed.";

    }

    /**
     * Method to check if the game is over
     * The game is over when of the players has all the ships sunk
     * @return boolean value
     */
    public boolean allShipsSunk() {

        // Check if there is a 'S' on the board
        for (char[] chars : grid) {
            for (char aChar : chars) {
                if (aChar == 'S') {
                    // If found return false
                    return false;
                }
            }
        }
        // If not found any
        return true;
    }

    /**
     * Return the amount of attack missed
     * @return integer
     */
    public int getMissCount() {
        return missCount;
    }

    /**
     * Return the number of ships sunk
     * @return integer
     */
    public int getShipsSunk() {
        return shipsSunk;
    }
}
