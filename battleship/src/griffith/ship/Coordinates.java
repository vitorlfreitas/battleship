// Package
package griffith.ship;

// Imports
import java.io.Serializable;

/**
 * Class to create a new datatype, which is used in the ship attributes
 *
 * @author vitorlfreitas
 */
public class Coordinates implements Serializable {

    // Attributes
    public int x;
    public int y;

    // Constructor Method
    public Coordinates(int positionX, int positionY) {

        this.x = positionX;
        this.y = positionY;

    }
}
