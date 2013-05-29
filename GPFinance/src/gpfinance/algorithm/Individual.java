
package gpfinance.algorithm;

import gpfinance.datatypes.FitnessData;
import gpfinance.U;
import gpfinance.datatypes.Decision;
import gpfinance.datatypes.Fitness;
import gpfinance.datatypes.Security;
import gpfinance.tree.DecisionTree;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @date 2013-06-01
 * @author Simon van Dyk, Stuart Reid
 */
public class Individual {

    private DecisionTree tree;
    private Fitness fitness = new Fitness();
    private static final String[] filePath = {"/home/simon/Varsity/AI/assignments/assignment4/GPStocks/GPFinance/data/Fitness.csv", "/home/stuart/Documents/GPStocks/GPFinance/data/Fitness.csv"};
    private static final int NUM_QUARTERS = 4;
    private static FitnessData fitnessData = new FitnessData(filePath, NUM_QUARTERS);

    public Individual() {
    }

    public Individual(char type) {
        this.tree = new DecisionTree(type);
    }

    public Individual(char type, int treesize) {
        this.tree = new DecisionTree(type, treesize);
    }

    public Individual(DecisionTree tree) {
        this.tree = tree;
    }

    public Individual(DecisionTree tree, Fitness fitness) {
        this.tree = tree;
        this.fitness = fitness;
    }

    @Override
    public Individual clone() {
        return new Individual(this.tree.clone(), this.fitness.clone());
    }

    public void measure(int generation, ArrayList<Security> securities) {
        // Decision[] evaluate() and create list of decisions
        Decision[] decisions = tree.evaluate(securities);
        //System.out.println(decisions);
        fitness.returnValue = fitnessData.calculateReturn(decisions);
        
        // Fitness calculateReturn(Decision[])
        //decisions[0] == Decision.BUY
        // Calculate and save average fitness measures over all securities
        
        //stub
        //fitness.returnValue = Math.log(tree.size()) + U.rint(4);
    }

    public double getFitness() {
        return this.fitness.getFitness();
    }

    public static Comparator<Individual> AscendingFitness = new Comparator<Individual>() {
        @Override
        public int compare(Individual o1, Individual o2) {
            Double d1 = o1.fitness.getFitness();
            Double d2 = o2.fitness.getFitness();
            return d1.compareTo(d2);
        }
    };

    public static Comparator<Individual> DescendingFitness = new Comparator<Individual>() {
        @Override
        public int compare(Individual o1, Individual o2) {
            Double d1 = o1.fitness.getFitness();
            Double d2 = o2.fitness.getFitness();
            return d2.compareTo(d1);
        }
    };

    public DecisionTree getTree() {
        return tree;
    }

    public void print() {
        U.m("f() = " + fitness.getFitness());
        tree.print();
    }

    /*
     * Mutation methods, delegate to tree. 
     */
    public void mutateGrow() {
        tree.insertRandom();
    }

    public void mutateTrunc() {
        tree.removeRandomLimitedDepth();
    }

    public void mutateGauss() {
        tree.gaussRandom();
    }

    public void mutateSwapInequality() {
        tree.swapRandomInequality();
    }

    public void mutateLeaf() {
        tree.mutateTerminalNode();
    }

    public void mutateNonLeaf() {
        tree.mutateNonterminalNode();
    }
}
