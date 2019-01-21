package doscales;
/**
 * @author Louis Bennette
 * Written in NetBeans
 */
public class Scale {
    
    private double refA, refB;
    public Scale(double a, double b){
        refA = a;
        refB = b;
    }
    
    public void setReference(double a, double b){
        refA = a;
        refB = b;
    }
    
    public boolean sameAsRefA(double a){
        if(refA == a)
            return true;
        else
            return false;
    }
    
    public boolean sameAsRefB(double b){
        if(refB == b)
            return true;
        else
            return false;
    }
    
    public double getRefA(){
        return refA;
    }
    
    public double getRefB(){
        return refB;
    }
}
