package quadratic.solver;
/**
 * @author Louis Bennette
 * Written in NetBeans
 */
public class QuadSolver {

    public QuadSolver() {
    }

    public double descriminent(double a, double b, double c) {
        double descr;
        descr = (b * b) - (4 * a * c);
        return descr;
    }

    public double negSqrt(double a, double b, double descr) {
        double sqrtDescr = 0;
        double x = 0; // Postive

        sqrtDescr = Math.sqrt(descr);
        x = (-b - sqrtDescr) / 2 * a;

        return x;
    }

    public double posSqrt(double a, double b, double descr) {
        double sqrtDescr = 0;
        double x = 0;

        sqrtDescr = Math.sqrt(descr);
        x = (-b + sqrtDescr) / 2 * a;

        return x;
    }
}
