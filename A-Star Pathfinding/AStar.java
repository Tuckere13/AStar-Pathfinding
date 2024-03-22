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
            return n1.finalCost < n2.finalCost ? -1 : n1.finalCost > n2.finalCost ? 1 : 0; ////////////// ??????????? ////////////
        });


        startNode(si, sj);
        endNode(ei, ej);

        // init H
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j<grid[i].length; j++) {
                    grid[i][j] = new Node(i, j);
                    grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j-endJ);
                    grid[i][j].solution = false;
            }
        }

        grid[startI][starJ].fiinalCost = 0;

        for (int i = 0; i< blocks.length; i++) {
            addBlockOnNode(blocks[i][0], blocks[i][1])
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

        public void updateCostIfNeeded(Node current, Node t, int cost) {
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
                }
            }
        }

        public void process() {

            openNodes.add(grid[startI][starJ]);
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
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

                    if(current.j - 1 >= 0) {
                        t = grid[current.i - 1][current.j-1];
                        updateCostIfNeeded(current, t, current.finalCost + DIANGONAL_COST);
                    }

                    if(current.j + 1 < grid[0].length) {
                        t = grid[current.i - 1][current.j+1];
                        updateCostIfNeeded(current, t, current.finalCost + DIANGONAL_COST);
                    }
                }

                if (current.j - 1 >= 0) {
                    t = grid[current.i][current.j - 1];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
                }

                if(current.j + 1 < grid[0].length) {
                    t = grid[current.i][current.j+1];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
                }

                if(current.i + 1 < grid.length) {
                    t = grid[current.i + 1][current.j];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

                    if(current.j - 1 >= 0) {
                        t = grid[current.i + 1][current.j-1];
                        updateCostIfNeeded(current, t, current.finalCost + DIANGONAL_COST);
                    }

                    if(current.j + 1 < grid[0].length) {
                        t = grid[current.i + 1][current.j + 1];
                        updateCostIfNeeded(current, t, current.finalCost + DIANGONAL_COST);
                    }
                }
            }

        }

        public void display() {
            System.out.println("Grid :");

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (i == starI && j == startJ) {
                        System.out.print("S "); // start node
                    } else if(i == endI && j == endJ) {
                        System.out.print("E "); // End node
                    } else if(grid[i][j] != null) {
                        System.out.print("0 "); // Empty node
                    } else {
                        System.out.print("W "); // Wall
                    }

                    System.out.println();
                }

                System.out.println();
            }

            public void displayScores() {
                System.out.println("\nScores for Nodes");

                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] != null){
                            System.out.printf("%-3d", grid[i][j].finalCost);
                        } else {
                            System.out.print("BL ");
                        }
                    }

                    System.out.println();
                }
                System.out.println();
            }

            public void displaySolution() {
                if (closedNodes[endI][endJ]) {
                    System.out.print("Path :");

                    Node current = grid[endI][endJ];
                    System.out.println(current);
                    grid[current.i][current.j].solution = true;

                    while (current.parent != null) {
                        System.out.print(" -> " + current.parent);
                        grid[current.parent.i][current.parent.j].solution = true;
                        current = current.parent;
                    }

                    System.out.println("\n");

                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[i].length; j++) {
                            if (i == starI && j == startJ) {
                                System.out.print("S "); // start node
                            } else if(i == endI && j == endJ) {
                                System.out.print("E "); // End node
                            } else if(grid[i][j] != null) {
                                System.out.printf("%-3s ", grid[i][j].solution ? "X" : 0); // Empty node
                            } else {
                                System.out.print("W "); // Wall
                            }
        
                        }
        
                        System.out.println();
                }
                System.out.println();
            } else {
                System.out.println("No possible Path");
            }
        }

        public static void main(String[] args) {
            Astar astar = new AStar(15, 15, 0, 0, 3, 2
                    new int[][] {
                        {0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}
                    }
            );
            astar.display();
            astar.process();
            astar.displayScores();
            astar.displaySolution();
        }

    }

}
