package sheepdog.g9;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    public double x;
    public double y;
    public int sid;

    public Point() { x = 0; y = 0; }

    public Point(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public Point(sheepdog.sim.Point p) {
        x = p.x;
        y = p.y;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    public double dot(Point p) {
        return x * p.x + y * p.y;
    }

    public Point scale(double d) {
        x = x * d;
        y = y * d;
        return this;
    }

    public double distance(Point p) {
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y));
    }

    public boolean equals(Point o) {
        return o.x == x && o.y == y;
    }

    public sheepdog.sim.Point toSimPoint() {
        return new sheepdog.sim.Point(x, y);
    }

    public String toString() {
        return String.format("x = %f, y = %f", x, y);
    }

    public int compareTo(Point p) {
        /*
           double sin_param = param.y / param.distance(PlayerUtils.GATE);
           double sin_this = this.y / this.distance(PlayerUtils.GATE);
           int compareQuantity = ((Fruit) compareFruit).getQuantity(); 

        // sort in clockwise order, descending order
        if (sin_param - sin_this > 0) return 1;
        if (sin_param - sin_this < 0) return -1;
        return 0;
         */

        double tan = (y - 50.0) / (x - 50.0);
        double ptan = (p.y - 50.0) / (p.x - 50.0);

        // sort in clockwise order, descending order
        if (tan - ptan > 0) return 1;
        if (tan - ptan < 0) return -1;
        return 0;
    }

    public static Comparator<Point> PointDistanceComparator 
        = new Comparator<Point>() {

            public int compare(Point p1, Point p2) {
                double d1 = p1.distance(PlayerUtils.GATE);
                double d2 = p2.distance(PlayerUtils.GATE);
                return (d1 - d2 > 0) ? 1:-1;
            }
        };
}

