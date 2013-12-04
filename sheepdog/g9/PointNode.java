package sheepdog.g9;

public class PointNode extends Point {
    // assume that the tree is represented as an array.
    // parent is represented as the index of that array. -1 means root
    public int parent;
    // distance from this node to the root
    public double treeDistance;

    public PointNode(Point p) {
        x = p.x;
        y = p.y;
        parent = -1;
        treeDistance = p.distance(PlayerUtils.GATE);
    }
    
    public static PointNode[] build(Point[] sheeps) {
        PointNode[] nodes = new PointNode[sheeps.length+1];
        // root
        PointNode pn = new PointNode(PlayerUtils.GATE);
        nodes[0] = pn;
        for (int i = 0; i < sheeps.length; i++) {
            pn = new PointNode(sheeps[i]);
            for (int j = 0; j <= i; j++) {
                if (pn.treeDistance <= pn.distance(nodes[j])) {
                    pn.parent = j;
                    pn.treeDistance = pn.distance(nodes[j]);
                }
            }
            nodes[i+1] = pn;
        }
        
        return nodes;
    }

}