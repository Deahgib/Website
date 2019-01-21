package battleships.scene.view.player;

import battleships.engine.GameLogger;
import battleships.engine.Input;
import battleships.engine.Point;
import battleships.engine.ResolutionManager;
import battleships.engine.Window;
import battleships.engine.audio.AudioManager;
import battleships.scene.SceneState;
import battleships.scene.components.grid.FleetGrid;
import battleships.scene.components.grid.TargetGrid;
import battleships.scene.components.image.Image;
import battleships.scene.components.ship.*;
import battleships.scene.view.AbstractGame;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Louis Bennette
 */
public class UserPlayer extends AbstractPlayer{
    private Image shipHolder;
    
    public UserPlayer(){
        super();
        ResolutionManager dimentions = AbstractGame.dimentions;
        fleetGrid = new FleetGrid(dimentions.getLeftMargin(), dimentions.getTopMargin(), dimentions.getGridCellHeight());
        targetGrid = new TargetGrid(dimentions.getLeftMargin(), dimentions.getTopMargin(), dimentions.getGridCellHeight());
        
        ships.add( new Carrier(   dimentions.getLeftMargin() + dimentions.getGridCellHeight()*11, dimentions.getTopMargin()                                     , dimentions.getGridCellHeight()));
        ships.add( new Battleship(dimentions.getLeftMargin() + dimentions.getGridCellHeight()*11, dimentions.getTopMargin() + dimentions.getGridCellHeight()    , dimentions.getGridCellHeight()));
        ships.add( new Cruiser(   dimentions.getLeftMargin() + dimentions.getGridCellHeight()*11, dimentions.getTopMargin() + dimentions.getGridCellHeight() * 2, dimentions.getGridCellHeight()));
        ships.add( new Submarine( dimentions.getLeftMargin() + dimentions.getGridCellHeight()*11, dimentions.getTopMargin() + dimentions.getGridCellHeight() * 3, dimentions.getGridCellHeight()));
        ships.add( new Destroyer( dimentions.getLeftMargin() + dimentions.getGridCellHeight()*11, dimentions.getTopMargin() + dimentions.getGridCellHeight() * 4, dimentions.getGridCellHeight()));
        
        double padding = dimentions.getGridCellHeight()/4;
        this.shipHolder = new Image((dimentions.getLeftMargin() + dimentions.getGridCellHeight()*11)-padding, dimentions.getTopMargin()-padding, (dimentions.getGridCellHeight()*5)+(padding*2), (dimentions.getGridCellHeight()*5)+(padding*2), "resize-popup");
        
        GameLogger.print("Successfully created UserPlayer object with it's children.");
    }

    
    public void upadteSelectedTarget(){
        if(getTargetGrid().hasMouseHover()) {
            double mouseX = Mouse.getX();
            double mouseY = Window.getHeight() - Mouse.getY();
            if(Input.getMouseUp(Input.MOUSE_LEFT)){
                Point p = getTargetGrid().getGridCoordsAtPixel(mouseX, mouseY);
                getTargetGrid().setSelected((int)p.getX(),(int)p.getY());
            }
        }
    }

    public void updateShipPlacement() {
        // ============= Update The ships.
        for(AbstractShip ship : getShips()){
            // SHIP PLACEMENT STATE.
            if(SceneState.getPlayerState() == SceneState.Player.SHIP_PLACEMENT){
                if(ship.isShipBeingMoved()){
                    if(getFleetGrid().hasMouseHover()){
                        Point p = ship.getShipHoverPositionForGrid(this.getFleetGrid());
                        //System.out.println(p.toString());
                        if(Input.getMouseUp(Input.MOUSE_LEFT)){
                            if(getFleetGrid().isPositionValidFor(ship, (int)p.getX(), (int)p.getY())){
                                getFleetGrid().addShip(ship, (int)p.getX(), (int)p.getY());
                                getFleetGrid().placeShip(ship, (int)p.getX(), (int)p.getY());
                                AudioManager.play(AudioManager.PLACE_SHIP);
                            }
                        }
                    }
                    else{
                        // This is what we should do if there is no mouse hover on the grid. 
                        // Just place the ship back to it's safe location. If hat was on the grid then 
                        // add the ship back to the grid.
                        if(Input.getMouseUp(Input.MOUSE_LEFT)){
                            Point safeCoords = ship.getSafeCoords();
                            Point p = getFleetGrid().getGridCoordsAtPixel(safeCoords.getX(), safeCoords.getY());
                            if(getFleetGrid().isPositionValidFor(ship, (int)p.getX(), (int)p.getY())){
                                getFleetGrid().addShip(ship, (int)p.getX(), (int)p.getY());
                                getFleetGrid().placeShip(ship, (int)p.getX(), (int)p.getY());
                            }
                        }
                    }
                }
                
                if(Input.getMouseUp(Input.MOUSE_LEFT)){
                    ship.setShipBeingMoved(false);
                    ship.returnToSafeLocation();
                }
                if(Input.getMouseDown(Input.MOUSE_LEFT)){
                    if(ship.hasMouseHover()){
                        ship.markAsSafeLocation();
                        if(getFleetGrid().hasShip(ship)){
                            getFleetGrid().removeShip(ship);
                        }
                    }
                }
            }
            
            ship.update();
        }
        //this.ships.get(0).setHorizontalOrientation(false);
        //this.fleetGrid.addShip(this.ships.get(0), 2, 1);
    }
    
    public boolean isMovingShip() {
        for (AbstractShip ship : getShips()) {
            if (ship.isShipBeingMoved()) {
                return true;
            }
        }
        return false;
    }

    public Image getShipHolder() {
        return shipHolder;
    }
}
