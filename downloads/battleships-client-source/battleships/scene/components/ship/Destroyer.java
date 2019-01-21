package battleships.scene.components.ship;

import battleships.engine.TextureManager;

/**
 *
 * @author Louis Bennette
 */
public class Destroyer extends AbstractShip{
    public Destroyer(double x, double y, double height){
        super(x, y, 2*height, height);
        this.size = 2;
        this.name = "Destroyer";
        this.setTexture(TextureManager.loadTexture("destroyer"));
    }
    
    public Destroyer(){
        super();
        this.size = 2;
        this.name = "Destroyer";
    }
}
