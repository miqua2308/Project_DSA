package AI;

import Main.GamePanel;
import entity.Entity;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    private static PathFinder instance;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();

    Node startNode,goalNode,currentNode;
    boolean goalReached = false;
    int step =0;



    private PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    public static PathFinder createPathFinder(GamePanel gp) {
        if (instance == null) {
            instance = new PathFinder(gp);
        }
        return instance;
    }

    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col,row);
            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row ++;
            }

        }


    }




    public void resetNode(){
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row ++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }


    public void setNode(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
        resetNode();
        //set start and end node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            //set solid
            //check title
            int tileNum = gp.tileM.mapTileNum[col][row];
            if(gp.tileM.tile[tileNum].collision == true){
                node[col][row].solid =true;
            }

            //set Cost
            getCost(node[row][col]);
            col++;

            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }


    public void getCost(Node node){
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }


    public boolean search(){
        while (goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;
            //check current node
            currentNode.checked = true;
            openList.remove(currentNode);
            //open the up node
            if (row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            //open the left node
            if (col-1>=0){
                openNode(node[col-1][row]);
            }
            //open the down node
            if (row+1 < gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
            if (col+1<gp.maxWorldCol){
                openNode(node[col+1][row]);
            }
            //find the best node
            int bestNodeIndex=0;
            int bestNodefCost = 999;
            for (int i = 0; i <openList.size();i++){
                //first check F
                if (openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex =i;
                    bestNodefCost = openList.get(i).fCost;
                }

                //then check G
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost< openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            //if no node => end loop
            if (openList.size()==0){
                break;
            }
            //after the loop
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }


    public void openNode(Node node){
        if (node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }


    public void trackThePath(){
        Node current = goalNode;
        while (current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
