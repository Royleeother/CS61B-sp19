package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private AStarGraph<Vertex> graph;
    private Vertex source;
    private Vertex target;
    private double timeout;
    private int explored;
    private double timer;
    private List<Vertex> solution;
    private double solutionWeight = 0;
    private SolverOutcome outcome;
    private DoubleMapPQ Fringe;
    private int[] disTo;
    //private int[] edgeTo;
    private ArrayList<Vertex> edgeTo;
    private Stopwatch sw;

    // Constructor which finds the solution, computing everything
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.graph = input;
        this.source = start;
        this.target = end;
        this.timeout = timeout;

        disTo = new int[(int)end + 1];
        //edgeTo = new int[(int)end + 1];
        edgeTo = new ArrayList<>((int)end + 1);

        disTo[0] = 0;
        for (int i = 1; i < disTo.length; i++) {
            disTo[i] = 99999999;
        }
        // initialize solution (empty list)
        solution = new ArrayList<>();

        // compute start from here!!!
        aStar(input, start);
    }

    private void aStar(AStarGraph<Vertex> G, Vertex s) {
        sw = new Stopwatch();
        Fringe = new DoubleMapPQ();
        int pior = disTo[(int)s] + (int)G.estimatedDistanceToGoal(s, this.target);
        Fringe.add(s, pior);
        while (Fringe.size() != 0) {
            Vertex v = (Vertex) Fringe.removeSmallest();
            if (v == target) {

                /*while (edgeTo[(int)v] != (int)s) {
                    solution.add(edgeTo[(int)v]);
                }*/
                // solution list
                while (edgeTo.get((int)v) != s) {
                    solution.add(edgeTo.get((int)v));
                }
                solution.add(s);

                // solution weight
                solutionWeight = disTo[(int)v];

                // outcome
                outcome = SolverOutcome.SOLVED;

                timer = sw.elapsedTime();
            } else {
                relax(v, G);
                if (sw.elapsedTime() >= this.timeout) {
                    outcome = SolverOutcome.TIMEOUT;
                    timer = sw.elapsedTime();
                }
            }
        }
        if (outcome != SolverOutcome.SOLVED || outcome != SolverOutcome.TIMEOUT) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
    }

    private void relax(Vertex v, AStarGraph<Vertex> G) {
        for (WeightedEdge<Vertex> e : G.neighbors(v)) {
            this.explored += 1;
            Vertex p = e.from();
            Vertex q = e.to();
            double w = e.weight();
            //edgeTo[(int)q] = (int)p;
            edgeTo.add((int)q, p);
            if (disTo[(int)p] + w < disTo[(int)q]) {
                disTo[(int)q] = disTo[(int)p] + (int)w;
            }
            double prio = disTo[(int)p] + G.estimatedDistanceToGoal(q, this.target);
            if (Fringe.contains(q)) {
                Fringe.changePriority(q, prio);
            } else {
                Fringe.add(q, prio);
            }
            if (sw.elapsedTime() >= this.timeout) {
                break;
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
