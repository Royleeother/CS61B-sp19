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
    private HashMap<Vertex, Vertex> edgeTo;
    private Stopwatch sw;

    // Constructor which finds the solution, computing everything
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.graph = input;
        this.source = start;
        this.target = end;
        this.timeout = timeout;

        disTo = new HashMap<>();
        disTo.put(start, 0.0);
        edgeTo = new HashMap<>();

        solution = new LinkedList<>();

        // compute start from here!!!
        aStar(input, start);
    }

    private void aStar(AStarGraph<Vertex> G, Vertex s) {
        sw = new Stopwatch();
        Fringe = new DoubleMapPQ();
        double pior = disTo.get(s) + G.estimatedDistanceToGoal(s, this.target);
        Fringe.add(s, pior);

        while (Fringe.size() != 0 && sw.elapsedTime() < timeout) {
            Vertex v = (Vertex) Fringe.removeSmallest();
            if (v == this.target) {
                // solution list
                while (v != null) {
                    ((LinkedList<Vertex>) solution).addFirst(v);
                    v = edgeTo.get(v);
                }

                // solution weight
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
    }

    private void relax(Vertex v, AStarGraph<Vertex> G) {
        for (WeightedEdge<Vertex> e : G.neighbors(v)) {
            Vertex p = e.from();
            Vertex q = e.to();
            double w = e.weight();

            if (!disTo.containsKey(q) || disTo.get(p) + w < disTo.get(q)) {
                disTo.put(q, disTo.get(p) + w);
                edgeTo.put(q, p);
            }

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
