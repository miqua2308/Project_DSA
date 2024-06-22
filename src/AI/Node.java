/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class represents a node used in the pathfinding algorithm. It includes properties to store node positions and costs.
*/
package AI;

public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}
