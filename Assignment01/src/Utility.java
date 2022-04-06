/**
 * A utility class that provides useful tools for this project.
 * Containing: Position Comparison Tool
 */
public class Utility {
    // Return true if two positions are same.
    public static boolean comparePosition(Entity e1, Entity e2) {
        if (e1 == null || e2 == null) return false;
        if ((e1.getXCoordinate() != e2.getXCoordinate()) || (e1.getYCoordinate() != e2.getYCoordinate())){
            return false;
        }
        return true;
    }
}
