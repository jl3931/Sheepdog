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
                if (allsheeps[i].x < PlayerUtils.x) continue;
                sheeps[i] = new Point( allsheeps[i] );
                sheeps[i].sid = i;
            }
        } else {
            sheeps = new Point[allsheeps.length];
            for (int i=0; i<allsheeps.length; ++i) {
                if (allsheeps[i].x < PlayerUtils.x) continue;
                sheeps[i] = new Point( allsheeps[i] );
                sheeps[i].sid = i;
            }
            sheeps = allsheeps;
        }

        Arrays.sort(sheeps);

        sheeps = get_sheeps_inside_sector(num_dogs, sheeps);

        PointNode[] tree = PointNode.build(sheeps);

        int sid_targetSheep = PointNode.get_farthest_sheep(tree);

        int sid_push_to_here = tree[targetSheep.parent].sid;

        //TODO call fetch to push targetSheep to push_to_here

        return new Point();
    }

    private Point[] get_sheeps_inside_sector(int num_dogs, Point[] sheeps) {
        double partition = sheeps.length / num_dogs;

        return Arrays.copyOfRange(sheeps, partition * (id-1), (id == num_dogs)?sheeps.length?(partition * (id+1)-1) );
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
