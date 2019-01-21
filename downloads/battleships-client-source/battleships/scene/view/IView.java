package battleships.scene.view;

import battleships.engine.events.IEventResponder;

/**
 *
 * @author Louis Bennette
 */
public interface IView extends IEventResponder{
    public void update();
    public void draw();
    
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
}
