/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.scene.view.player;

import battleships.scene.SceneState;
import battleships.scene.components.grid.FleetGrid;
import battleships.scene.components.grid.TargetGrid;
import battleships.scene.components.ship.*;
import java.util.ArrayList;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractPlayer {
    protected ArrayList<AbstractShip> ships = new ArrayList<AbstractShip>();
    
    protected FleetGrid fleetGrid;
    protected TargetGrid targetGrid;
    
    protected boolean ready;
    
    
    public AbstractPlayer(){
        SceneState.setPlayerState(SceneState.Player.SHIP_PLACEMENT);
        this.ready = false;
    }

    public AbstractShip getShipAt(int x, int y){
        AbstractShip ship = getFleetGrid().getShipAt(x, y);
        
        if(ship != null){
            return ship;
        }
        else
        {
            System.err.println("---Ship at: " + x + ", " + y + " not found");
            return null;
        }
    }

    public ArrayList<AbstractShip> getShips() {
        return ships;
    }

    public FleetGrid getFleetGrid() {
        return fleetGrid;
    }

    public TargetGrid getTargetGrid() {
        return targetGrid;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
