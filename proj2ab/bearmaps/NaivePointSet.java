package bearmaps;

import java.util.List;

public final class NaivePointSet implements PointSet {
    private final List<Point> pointSet;

    public NaivePointSet(List<Point> points) {
        pointSet = points;
    }

    @Override
    public Point nearest(double x, double y) {
        double best = 99999;
        double X = 0;
        double Y = 0;

        for (int i = 0; i < pointSet.size(); i++) {
            double x1 = pointSet.get(i).getX();
            double y1 = pointSet.get(i).getY();
            double dis = Math.sqrt(Math.pow((x - x1), 2) + Math.pow((y - y1), 2));
            if (dis < best) {
                best = dis;
                X = x1;
                Y = y1;
            }
        }
        return new Point(X, Y);
    }
    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(2.2, 9.9);

        List l = List.of(p1, p2, p3, p4);
        NaivePointSet nn = new NaivePointSet(l);
        Point ret = nn.nearest(3.0, 4.0);
        double a = ret.getY();
        double b = ret.getX();
    }
}
