import java.util.*;

public class AStar {
    
    public static final int DIANGONAL_COST = 14;
    public static final int V_H_COST = 10;

    private Node[][] grid;

    //Open Nodes : the set of nodes to be evaluated
    // Put Nodes with lowest cost in first
    private PriorityQueue<Node> openNodes;
    // Closed Nodes : the set of nodes already evaluated
    private boolean[][] closedNodes;
    // Start Node
    private int startI, startJ;
    //End Node
    private int endI, endJ;

    public Astar(int width, int height, int si, int sj, int ei, int ej, int [][] blocks) {

        grid = new Node[width][height];
        closedNodes = new boolean[width][height];
        openNodes = new PriorityQueue<>((Node n1, Node n2) -> {
            return n1.finalCost < n2.finalCost ? -1 : n1.finalCost > n2.finalCost ? 1 : 0;
        });


        startCell(si, sj);
        endCell(ei, ej);

        // init H
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j<grid[i].length; j++) {
                    grid[i][j] = new Node(i, j);
                    grid[i][j].heuristicCost = 
            }

        }
    }

}
