package battleships.scene.components;

import battleships.engine.Point;

/**
 *
 * @author Louis Bennette
 */
public interface IComponent{
    
    public void draw();
    public void update();
    
    public void setLocation(double x, double y);
    public void setLocation(Point p);
    
    public void setX(double x);
    public void setY(double y);
    
    public void setWidth(double width);
    public void setHeight(double height);
    
    public Point getLocation();
    
    public double getX();
    public double getY();
    
    public double getWidth();
    public double getHeight();
}
