package battleships.scene.components.ship;

import battleships.engine.TextureManager;

/**
 *
 * @author Louis Bennette
 */
public class Carrier extends AbstractShip{
    
    public Carrier(double x, double y, double height){
        super(x, y, 5*height, height);
        this.size = 5;
        this.name = "Carrier";
        this.setTexture(TextureManager.loadTexture("carrier"));
    }
    
    public Carrier(){
        super();
        this.size = 5;
        this.name = "Carrier";
    }
}
