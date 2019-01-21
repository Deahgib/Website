package battleships.scene.components.ship;

import battleships.engine.TextureManager;

/**
 *
 * @author Louis Bennette
 */
public class Battleship extends AbstractShip {
    
    public Battleship(double x, double y, double height){
        super(x, y, 4*height, height);
        this.size = 4;
        this.name = "Battleship";
        this.setTexture(TextureManager.loadTexture("battleship"));
    }
    
    public Battleship(){
        super();
        this.size = 4;
        this.name = "Battleship";
    }
}
