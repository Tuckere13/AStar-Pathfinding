package pathfinding;
import java.util.*;


public class AStar {
    
    public static final int DIANGONAL_COST = 14;
    public static final int VERT_HORI_COST = 10;

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

    public AStar(int width, int height, int si, int sj, int ei, int ej, int [][] blocks) {

        grid = new Node[width][height];
        closedNodes = new boolean[width][height];
        openNodes = new PriorityQueue<>((Node n1, Node n2) -> {
            return Integer.compare(n1.finalCost, n2.finalCost); 
        });


        startNode(si, sj);
        endNode(ei, ej);

        // Heuristic
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j<grid[i].length; j++) {
                grid[i][j] = new Node(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j-endJ);
                grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

        for (int i = 0; i< blocks.length; i++) {
            addBlockOnNode(blocks[i][0], blocks[i][1]);
        }
    }

        public void addBlockOnNode(int i, int j) {
            grid[i][j] = null;
        }

        public void startNode(int i, int j) {
            startI = i;
            startJ = j;
        }

        public void endNode(int i, int j) {
            endI = i;
            endJ = j;
        }

        public void updateCost(Node current, Node t, int cost) {
            if (t == null || closedNodes[t.i][t.j]) {
                return;
            }

            int tFinalCost = t.heuristicCost + cost;
            boolean isOpen = openNodes.contains(t);

            if (!isOpen || tFinalCost < t.finalCost) {
                t.finalCost = tFinalCost;
                t.parent = current;

                if(!isOpen) {
                    openNodes.add(t);
                } else {
                    openNodes.remove(t);
                    openNodes.add(t);
                }
            }
        }

        public void calculate() {

            openNodes.add(grid[startI][startJ]);
            Node current;
        
            while(true) {
                current = openNodes.poll();

                if(current == null) {
                    break;
                }

                closedNodes[current.i][current.j] = true;

                if(current.equals(grid[endI][endJ])) {
                    return;
                }

                Node t;

                if (current.i - 1 >= 0) {
                    t = grid[current.i - 1][current.j];
                    updateCost(current, t, current.finalCost + VERT_HORI_COST);

                    if(current.j - 1 >= 0) {
                        t = grid[current.i - 1][current.j-1];
                        updateCost(current, t, current.finalCost + DIANGONAL_COST);
                    }

                    if(current.j + 1 < grid[0].length) {
                        t = grid[current.i - 1][current.j+1];
                        updateCost(current, t, current.finalCost + DIANGONAL_COST);
                    }
                }

                if (current.j - 1 >= 0) {
                    t = grid[current.i][current.j - 1];
                    updateCost(current, t, current.finalCost + VERT_HORI_COST);
                }

                if(current.j + 1 < grid[0].length) {
                    t = grid[current.i][current.j+1];
                    updateCost(current, t, current.finalCost + VERT_HORI_COST);
                }

                if(current.i + 1 < grid.length) {
                    t = grid[current.i + 1][current.j];
                    updateCost(current, t, current.finalCost + VERT_HORI_COST);

                    if(current.j - 1 >= 0) {
                        t = grid[current.i + 1][current.j-1];
                        updateCost(current, t, current.finalCost + DIANGONAL_COST);
                    }

                    if(current.j + 1 < grid[0].length) {
                        t = grid[current.i + 1][current.j + 1];
                        updateCost(current, t, current.finalCost + DIANGONAL_COST);
                    }
                }
            }

        }

            public void displaySolution() {
                if (closedNodes[endI][endJ]) {
                    System.out.print("Path: ");

                    Node current = grid[endI][endJ];
                    System.out.print(current);
                    grid[current.i][current.j].solution = true;

                    while (current.parent != null) {
                        System.out.print(" -> " + current.parent);
                        grid[current.parent.i][current.parent.j].solution = true;
                        current = current.parent;
                    }

                    System.out.println("\n");

                    System.out.print("    ");
                    for (int g = 0; g < grid.length; g++){
                        if (g+1 < 10)
                            System.out.print(g+1 + "  ");
                        else if (g+1 >= 10)
                            System.out.print(g+1 + " ");
                    }
                    System.out.println();

                    for (int i = 0; i < grid.length; i++) {
                        if (i+1 <10)
                            System.out.print(i+1 + " | ");
                        else
                            System.out.print(i+1 + "| ");
                        for (int j = 0; j < grid[i].length; j++) {
                            if (i == startI && j == startJ) {
                                System.out.print("S  "); // start node
                            } else if(i == endI && j == endJ) {
                                System.out.print("E  "); // End node
                            } else if(grid[i][j] != null) {

                                if (grid[i][j].solution) {
                                    System.out.print("/  ");
                                } else {
                                    System.out.print(".  ");
                                }

                            } else {
                                System.out.print("[W]"); // Wall
                            }
        
                        }
        
                        System.out.println();
                }
                System.out.println();
            } else {
                System.out.println("No possible Path");
            }
        }

        public void display() {
            System.out.println("Grid :");
            System.out.print("    ");
            for (int g = 0; g < grid.length; g++){
                if (g+1 < 10)
                    System.out.print(g+1 + "  ");
                else if (g+1 >= 10)
                    System.out.print(g+1 + " ");
            }
            System.out.println();
            //System.out.print(" _______________________________________");
            //System.out.println();

            for (int i = 0; i < grid.length; i++) {
                if (i+1 <10)
                    System.out.print(i+1 + " | ");
                else
                    System.out.print(i+1 + "| ");

                for (int j = 0; j < grid[i].length; j++) {
                    


                    if (i == startI && j == startJ) {
                        System.out.print("S  "); // start node
                    } else if(i == endI && j == endJ) {
                        System.out.print("E  "); // End node
                    } else if(grid[i][j] != null) {
                        System.out.print(".  "); // Empty node
                    } else {
                        System.out.print("[W]"); // Wall
                    }
                }

                System.out.println();
            }
            System.out.println();
        }

        static public int randomPoint(){
            Random rand = new Random();
            int randomPoint = rand.nextInt(14);

            return randomPoint;
        }
    

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while(true){
        
                System.out.println("Enter the x value for the start node(1-15): ");
                int startI = scanner.nextInt() - 1;
                System.out.println("Enter the y value for the start node(1-15): ");
                int startJ = scanner.nextInt() - 1;
        
                System.out.println("Enter the x value for the end node(1-15): ");
                int endI = scanner.nextInt() - 1;
                System.out.println("Enter the y value for the end node(1-15): ");
                int endJ = scanner.nextInt() - 1;

                int numBlockedCells = (15*15) / 10;

                int[][] blocks = new int[numBlockedCells][2];
                Random rand = new Random();
                for (int i = 0; i < numBlockedCells; i++) {
                    int blockI = rand.nextInt(15);
                    int blockJ = rand.nextInt(15);
                    while (true) {
                        if ((blockI == startI && blockJ == startJ) || (blockI == endI && blockJ == endJ)) {
                            blockI = rand.nextInt(15);
                            blockJ = rand.nextInt(15);
                        } else {
                            break;
                        }
                    }
                    blocks[i][0] = blockI;
                    blocks[i][1] = blockJ;
                }

                AStar astar = new AStar(15, 15, startI, startJ, endI, endJ, blocks);
                astar.display();
                astar.calculate();
                astar.displaySolution();

                System.out.println("Continue? (yes/no)");
                String continueChoice = scanner.next();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    break;
                }
            }
    }
}


    
    
    



