package quadratic.solver;
/**
 * @author Louis Bennette
 * Written in NetBeans
 */
public class LoadQuadSolver {
    /**
     * Actually this class does not load the QuadSolver class, instead
     * it loads the QuadSolverGUI class which in turn loads QuadSolver
     * as we can clearly see in the main method.
     * @param args 
     */
    public static void main (String[] args){
        QuadSolverGUI gui = new QuadSolverGUI();
        gui.setVisible(true);
    }
}
