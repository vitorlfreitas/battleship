// Package
package griffith.ship;

// Imports
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to hold ship's attributes and methods
 *
 * @author vitorlfreitas
 */
public class Ship implements Serializable {

        // Attributes
        public String name;
        public int size;
        public char position;
        public int gotHitCounts;

        // Array to hold the coordinates allocated on the board
        public ArrayList<Coordinates> coordinates = new ArrayList<>();

        // Constructor Method
        public Ship(String name, int size, char position) {
                this.name = name;
                this.size = size;
                this.position = position;
                this.gotHitCounts = 0;
        }

        public boolean isSunk() {
                return gotHitCounts >= size;
        }

        /**
         * Method to localize the ship on the board and return a boolean value if found
         *
         * @param positionX Coordinate X on the board
         * @param positionY Coordinate Y on the board
         * @return boolean value
         */
        public boolean findShip(int positionX, int positionY) {

            for (Coordinates coordinate : coordinates)
                if (coordinate.x == positionX && coordinate.y == positionY) return true;

            // If not found
            return false;
        }
}
