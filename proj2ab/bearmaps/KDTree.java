package bearmaps;

import java.util.List;

import static bearmaps.Point.distance;

public final class KDTree implements PointSet {
    private Node root;

    public class Node {
        private Node left, right;
        private Point point;
        private boolean ByX;

        public Node(Point point, Node left, Node right, boolean ByX) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.ByX = ByX;
        }

        /*private double distance(Point g) {
            double x_pow_2 = Math.pow(this.point.getX() - g.getX(), 2);
            double y_pow_2 = Math.pow(this.point.getY() - g.getY(), 2);
            double res = Math.sqrt(x_pow_2 + y_pow_2);
            return res;
        }*/
    }
    private void put(Point p) {
        root = put_helper(p, root, true);
    }
    private Node put_helper(Point p, Node x, boolean ByX) {
        if (x == null) x = new Node(p, null, null, ByX);
        else {
            double cmp;
            if (x.ByX) {
                cmp = p.getX() - x.point.getX();
                ByX = false;
            } else {
                cmp = p.getY() - x.point.getY();
                ByX = true;
            }
            if (cmp < 0) x.left = put_helper(p, x.left, ByX);
            else if (cmp > 0) x.right = put_helper(p, x.right, ByX);
            else {
                if (x.ByX) {
                    if (p.getY() == x.point.getY()) {
                        x.point = p;
                    } else x.right = put_helper(p, x.right, ByX);
                } else {
                    if (p.getX() == x.point.getX()) {
                        x.point = p;
                    } else x.right = put_helper(p, x.right, ByX);
                }
            }
    }
        return x;
    }

    public KDTree(List<Point> points) {
        root = null;
        for (int i = 0; i < points.size(); i++) {
            put(points.get(i));
        }
    }

    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        Node b  = new Node(new Point(999, 999), null, null, true);
        return nearest_helper(root, p, b).point;
    }
    private Node nearest_helper(Node n, Point goal, Node best) {
        Node goodSide, badSide;
        if (n == null) return best;
        else if (distance(n.point, goal) < distance(best.point, goal)) {
            best = n;
        } /*else {
            best = nearest_helper(n.left, goal, best);
            best = nearest_helper(n.right, goal, best);
        }*/
        if (goalAtLeft(goal, n)) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest_helper(goodSide, goal, best);
        if (notSoBad(goal, n, best)) {
            best = nearest_helper(badSide, goal, best);
        }
        return best;
    }
    private boolean goalAtLeft(Point g, Node n) {
        if (n.ByX) {
            double n_point = n.point.getX();
            double left = n_point - 0.001;
            double right = n_point + 0.001;
            return Math.abs(g.getX() - left) <=  Math.abs(g.getX() - right);
        } else {
            double n_point = n.point.getY();
            double left = n_point - 0.001;
            double right = n_point + 0.001;
            return Math.abs(g.getY() - left) <= Math.abs(g.getY() - right);
        }
    }
    private boolean notSoBad(Point g, Node n, Node best) {
        Point bestPoint;
        double dis;
        double dis_sofar;
        if (n.ByX) {
            bestPoint = new Point(n.point.getX(), g.getY());
        } else {
            bestPoint = new Point(g.getX(), n.point.getY());
        }
        dis = distance(bestPoint, g);
        dis_sofar = distance(bestPoint, best.point);
        return dis <= dis_sofar;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2,3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4,2);
        Point p3 = new Point(4,2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3,3);
        Point p6 = new Point(1,5);
        Point p7 = new Point(4,4);

        List l = List.of(p1, p2, p3, p4, p5, p6, p7);
        KDTree nn = new KDTree(l);
    }
}
