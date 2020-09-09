/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/

import java.security.MessageDigest;
public class Location {
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location() {
        this(0, 0);
    }

    public int hashCode() {
        return (String.valueOf(this.xCoord) + String.valueOf(this.yCoord)).hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (this.getClass() != obj.getClass()) {return false;}
        Location other = (Location) obj;
        if (this.xCoord != other.xCoord) {return false;}
        if (this.yCoord != other.yCoord) {return false;}
        return true;
    }

}
