package sheepdog.g9;

import java.util.*;

public class MDTree extends Strategy {
    public String name = "MDTree";

    public MDTree (int id, LinkedList<Strategy> strategyStack) {
        super(id, strategyStack);
    }

    public static double estimate(sheepdog.sim.Point[] dogs, sheepdog.sim.Point[] sheeps) {
        return 0;
    }

    public sheepdog.sim.Point move(sheepdog.sim.Point[] dogs, sheepdog.sim.Point[] allsheeps) {
        sheepdog.sim.Point me = dogs[id-1];
        int num_dogs = dogs.length;
        Point[] sheeps; // Point implements comparable

        if (Global.mode) {
            sheeps = new Point[Global.nblacks];
            for (int i=0; i<Global.nblacks; ++i) {
                sheeps[i] = new Point();
                sheeps[i].x = allsheeps[i].x;
                sheeps[i].y = allsheeps[i].y;
            }
        } else {
            sheeps = new Point[allsheeps.length];
            for (int i=0; i<allsheeps.length; ++i) {
                sheeps[i] = new Point();
                sheeps[i].x = allsheeps[i].x;
                sheeps[i].y = allsheeps[i].y;
            }
            sheeps = allsheeps;
        }

        Arrays.sort(sheeps);

        get_sheeps_inside_sector(num_dogs, sheeps);

        Tree tree = new Tree(sheeps);

        Point targetSheep = tree.get_farthest_sheep();

        Point push_to_here = targetSheep.parent;

        //TODO call fetch to push targetSheep to push_to_here

        return new Point();
    }

    private void get_sheeps_inside_sector(int num_dogs, Point[] sheeps) {

        double partition = 2.0 * Math.PI / num_dogs;
        double up = Math.sin(Math.PI - id * partition);
        double down = Math.sin(Math.PI - (id+1) * partition);

        ArrayList<Point> buf = new ArrayList<Point>();

        for (int i=0; i<sheeps.length; ++i) {
            double sheep_sin = sheeps[i].y / sheeps[i].distance(new Point(0, 0));

            if (down <= sheep_sin && sheep_sin <= up)
                buf.add(sheep_sin);
        }

        sheeps = buf.toArray(new Point[buf.size()]);
    }

    private boolean done(sheepdog.sim.Point[] sheeps) {
        for (int i = 0; i < sheeps.length; i++) {
            if (sheeps[i].x > PlayerUtils.GATE.x)
                return false;
        }
        return true;
    }

    public String toString() {
        return String.format("%s\t%s\t dog  %d move to (%s)", name, stage.toString(), id, ret.toString());
    }
}

