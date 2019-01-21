package battleships.scene.components.ship;

import battleships.engine.TextureManager;

/**
 *
 * @author Louis Bennette
 */
public class Cruiser extends AbstractShip{
    public Cruiser(double x, double y, double height){
        super(x, y, 3*height, height);
        this.size = 3;
        this.name = "Cruiser";
        this.setTexture(TextureManager.loadTexture("cruiser"));
    }
    
    public Cruiser(){
        super();
        this.size = 3;
        this.name = "Cruiser";
    }
}
