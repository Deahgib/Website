package battleships.engine;

/**
 *
 * @author Louis Bennette
 */
public class Point {
    private double x;
    private double y;
    
    public Point(){}
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public boolean equals(Point p){
        if((this.getX() == p.getX()) && (this.getY() == p.getY())){
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        return new String(this.x+", "+this.y);
    }
}
