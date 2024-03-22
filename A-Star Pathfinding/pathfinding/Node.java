
package pathfinding;
import java.util.*;



//import javafx.scene.Node;


public class Node {
    
    // Coordinates
    public int i, j;
    // Parent Node for path
    public Node parent;
    // Heuristic cost
    public int heuristicCost;
    // Final cost
    public int finalCost;

    public boolean solution;

    public Node(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public String toString(){
        return "[" + i + ", " + j + "]";
    }
}