package sheepdog.g9;

import java.util.*;

public class Tree extends Strategy {
    public String name = "Tree";

    public Tree ( int id, LinkedList<Strategy> strategyStack ) {
        super(id, strategyStack);
    }

    public static double estimate( Point[] dogs, Point[] sheeps ) {
        return 0;
    }

    public Point move(Point[] dogs, Point[] allsheeps) {

        Point me = dogs[id-1];
        int num_dogs = dogs.length;

        ArrayList<Point> buf = new ArrayList<Point>();

        if (Global.mode) {
            for (int i=0; i<Global.nblacks; ++i) {
                if (allsheeps[i].x >= PlayerUtils.GATE.x) {
                    Point p = new Point( allsheeps[i] );
                    p.sid = i;
                    buf.add(p);
                }
            }
        } else {
            for (int i=0; i<allsheeps.length; ++i) {
                if (allsheeps[i].x >= PlayerUtils.GATE.x) {
                    Point p = new Point( allsheeps[i] );
                    p.sid = i;
                    buf.add(p);
                }
            }
        }

        Point[] sheeps = buf.toArray(new Point[buf.size()]);

        Arrays.sort(sheeps);


        //print_array(sheeps);

        System.out.println("--------------------------------");
        System.out.println("for dog id : " + id);
        for (int i=0; i<sheeps.length; ++i) {
            System.out.println(sheeps[i].sid + ", " + sheeps[i].x + ", " + sheeps[i].y
                    + ", " );
        }
        System.out.println("--------------------------------");

        sheeps = get_sheeps_inside_sector(num_dogs, sheeps);

        Arrays.sort(sheeps, Point.PointDistanceComparator);

        print_array(sheeps);

        PointNode[] tree = PointNode.build(sheeps);

        System.out.println("tree : ");
        for (int i=0; i<tree.length; ++i) {
            System.out.println("index: "+i+", sid: "+tree[i].sid+", parent:"+tree[i].parent
                    + ", treeDistance: "+ tree[i].treeDistance);
        }

        System.out.println("tree finish");

        int index_targetSheep = PointNode.get_farthest_sheep(tree);
        int _parent = tree[index_targetSheep].parent;
        if (-1 == _parent) { // root
            return PlayerUtils.moveDogTo(me, Fetch.DEFAULTIDLE);
        }

        int sid_push_to_here = tree[_parent].sid;
        int sid_targetSheep = tree[index_targetSheep].sid;

        if (done(allsheeps))
            strategyStack.pop();

        System.out.println("sheepid : " + sid_targetSheep + " ==> pos : (" 
                + allsheeps[sid_push_to_here].x +", " + allsheeps[sid_push_to_here].y + ")");
        Fetch fetch = new Fetch(id, strategyStack, sid_targetSheep, allsheeps[sid_push_to_here]);
        return fetch.move(dogs, allsheeps);
    }

    private void print_array( Point[] arr ) {
        for (int i=0; i<arr.length; ++i) {
            System.out.println("(" + arr[i].sid + ", " +arr[i].x+", "+arr[i].y+")  ");
        }
        System.out.println();
    }

    private Point[] get_sheeps_inside_sector(int num_dogs, Point[] sheeps) {
        int partition = sheeps.length / num_dogs;

        return Arrays.copyOfRange(sheeps, partition * (id-1), 
                (id == num_dogs) ? sheeps.length : (partition * id) );
    }

    private boolean done(Point[] sheeps) {
        for (int i = 0; i < sheeps.length; i++) {
            if (sheeps[i].x > PlayerUtils.GATE.x)
                return false;
        }
        return true;
    }

    public String toString() {
        return "";
        //return String.format("%s\t%s\t dog  %d move to (%s)", name, stage.toString(), id, ret.toString());
    }
}

