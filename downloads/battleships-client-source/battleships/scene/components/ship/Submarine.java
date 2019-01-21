package battleships.scene.components.ship;

import battleships.engine.TextureManager;

/**
 *
 * @author Louis Bennette
 */
public class Submarine extends AbstractShip{
    public Submarine(double x, double y, double height){
        super(x, y, 3*height, height);
        this.size = 3;
        this.name = "Submarine";
        this.setTexture(TextureManager.loadTexture("submarine"));
    }
    
    public Submarine(){
        super();
        this.size = 3;
        this.name = "Submarine";
    }
}
