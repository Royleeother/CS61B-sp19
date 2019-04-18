package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private AStarGraph<Vertex> graph;
    private Vertex source;
    private Vertex target;
    private double timeout;
    private int explored = 0;
    private double timer;
    private List<Vertex> solution;
    private double solutionWeight = 0;
    private SolverOutcome outcome;
    private DoubleMapPQ Fringe;
    private Map<Vertex, Double> disTo;
    //private int[] disTo;
    //private LinkedList<Vertex> disTo;
    //private int[] edgeTo;
    //private ArrayList<Vertex> edgeTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private Stopwatch sw;

    // Constructor which finds the solution, computing everything
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.graph = input;
        this.source = start;
        this.target = end;
        this.timeout = timeout;

        disTo = new HashMap<>();
        //disTo = new int[(int)end + 1];
        //disTo = new LinkedList<>();
        //edgeTo = new int[(int)end + 1];
        //edgeTo = new ArrayList<>((int)end + 1);
        edgeTo = new HashMap<>();

        /*disTo[0] = 0;
        //disTo.add(source);
        for (int i = 1; i < disTo.length; i++) {
            disTo[i] = 99999999;
        }*/
        disTo.put(start, 0.0);
        // initialize solution (empty list)
        //solution = new ArrayList<>();
        solution = new LinkedList<>();


        // compute start from here!!!
        aStar(input, start);
    }

    private void aStar(AStarGraph<Vertex> G, Vertex s) {
        sw = new Stopwatch();
        Fringe = new DoubleMapPQ();
        //int pior = disTo[(int)s] + (int)G.estimatedDistanceToGoal(s, this.target);
        double pior = disTo.get(s) + G.estimatedDistanceToGoal(s, this.target);
        Fringe.add(s, pior);

        while (Fringe.size() != 0 && sw.elapsedTime() < timeout) {
            Vertex v = (Vertex) Fringe.removeSmallest();
            if (v == this.target) {

                /*while (edgeTo[(int)v] != (int)s) {
                    solution.add(edgeTo[(int)v]);
                }*/
                /*while (edgeTo.get(v) != s) {
                    solution.add(edgeTo.get(v));
                    v = edgeTo.get(v);
                }
                solution.add(s);*/
                // solution list
                while (v != null) {
                    ((LinkedList<Vertex>) solution).addFirst(v);
                    //solution.add(v);
                    v = edgeTo.get(v);
                }

                // solution weight
                //solutionWeight = disTo[(int)this.target];
                solutionWeight = disTo.get(this.target);

                // outcome
                outcome = SolverOutcome.SOLVED;

                timer = sw.elapsedTime();

                break;
            } else {
                relax(v, G);
                if (sw.elapsedTime() >= this.timeout) {
                    outcome = SolverOutcome.TIMEOUT;
                    timer = sw.elapsedTime();
                }
            }
            this.explored += 1;
        }
        // do some decision
        if (sw.elapsedTime() >= this.timeout) {
            outcome= SolverOutcome.TIMEOUT;
        } else if (solution.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        /*if (outcome != SolverOutcome.SOLVED || outcome != SolverOutcome.TIMEOUT) {
            outcome = SolverOutcome.UNSOLVABLE;
        }*/
    }

    private void relax(Vertex v, AStarGraph<Vertex> G) {
        for (WeightedEdge<Vertex> e : G.neighbors(v)) {
            Vertex p = e.from();
            Vertex q = e.to();
            double w = e.weight();
            //edgeTo[(int)q] = (int)p;
            //edgeTo.add((int)q, p);
            //edgeTo.put(q, p);  if set this move will repeat same edge
            /*if (disTo[(int)p] + w < disTo[(int)q] && !edgeTo.containsKey(q)) {
                disTo[(int)q] = disTo[(int)p] + (int)w;
                edgeTo.put(q, p);
            }*/
            if (!disTo.containsKey(q) || disTo.get(p) + w < disTo.get(q)) {
                disTo.put(q, disTo.get(p) + w);
                edgeTo.put(q, p);
            }
            //double prio = disTo[(int)p] + G.estimatedDistanceToGoal(q, this.target);
            double prio = disTo.get(p) + G.estimatedDistanceToGoal(q, this.target);
            if (Fringe.contains(q)) {
                Fringe.changePriority(q, prio);
            } else {
                Fringe.add(q, prio);
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }
    @Override
    // return a list of vertices corresponding to a solution
    public List<Vertex> solution() {
        return solution;
    }
    @Override
    public double solutionWeight() {
        return solutionWeight;
    }
    @Override
    public int numStatesExplored() {
        return explored;
    }
    @Override
    public double explorationTime() {
        return timer;
    }
}
