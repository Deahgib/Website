package battleships.scene.view.player;

import battleships.engine.Point;
import battleships.scene.components.grid.AbstractGrid;
import battleships.scene.components.grid.AbstractGrid.HitMarker;
import battleships.scene.components.grid.FleetGrid;
import battleships.scene.components.grid.TargetGrid;
import battleships.scene.components.ship.*;
import battleships.scene.view.player.AI.AIBehaviour;
import battleships.scene.view.player.AI.AIGameModel;
import battleships.scene.view.player.AI.AIKillState;
import battleships.scene.view.player.AI.AISearchState;
import java.util.Random;

/**
 *
 * @author Louis Bennette
 */
public class ComputerAIPlayer extends AbstractPlayer {

    private AIGameModel gameModel;
    
    private AIBehaviour currentState;
    
    private State state;
    /**
     * This is substate used when in the kill state to help identify the 
     * orientation of the ship the AI is attacking.
     */
    private enum State{
        SEARCH, KILL
    }

    /**
     * This is the A.I. player instance. The view that is coordinating this must
     * create an instance of AI player and then calls initAIShips() this will 
     * set up the AI's fleet. When the game has started and it is the AI's turn 
     * to attack, we call findNextCellToAttack() if there is a next cell to 
     * attack that has been found we add that to the AI player's targetGrid as
     * setSelected. To validate if the selected variable is we call getSelected
     * on the target grid and if the returned value is not null then the AI has 
     * selected a cell to attack. at this point the game will handle attacking 
     * the AI's opponent and will also update the AI's targetGrid to hit or 
     * miss.
     */
    public ComputerAIPlayer() {
        super();
        fleetGrid = new FleetGrid();
        targetGrid = new TargetGrid();

        ships.add(new Carrier());
        ships.add(new Battleship());
        ships.add(new Cruiser());
        ships.add(new Submarine());
        ships.add(new Destroyer());
        
        this.gameModel = new AIGameModel(this.getTargetGrid());
        
        this.state = State.SEARCH;
        this.currentState = new AISearchState(this.gameModel, AbstractGrid.getGRID_SIZE());

        System.out.println("Constructed AI player.");
    }

    
    /**
     * This sets up the ships of the AI randomly. This only needs to be called 
     * once however it can be called again, this will not change the 
     * configuration of the ships.
     * We first randomly choose the orientation of a ship. Then we
     * randomly choose a position in the grid for the ship and check to see if 
     * that position is valid. If the position is valid then we add that ship to
     * the grid and move on to the next. When all the ships are placed we set 
     * the player to ready.
     */
    public void initAIShips() {
        int i = 0;
        while (!this.isReady()) {
            Random r = new Random();
            int x = r.nextInt(this.fleetGrid.getGRID_SIZE());
            int y = r.nextInt(this.fleetGrid.getGRID_SIZE());

            // Randomly choose an orientation for the ship.
            int orientation = r.nextInt(2);
            if (orientation == 0) {
                this.ships.get(i).setHorizontalOrientation(true);
            } else {
                this.ships.get(i).setHorizontalOrientation(false);
            }

            // Randomly choose a position in the grid for the ship and check to 
            // see if that position is valid.
            if (this.fleetGrid.isPositionValidFor(this.ships.get(i), x, y)) {
                this.fleetGrid.addShip(this.ships.get(i), x, y);
                i++;
                if (this.fleetGrid.isGridFilled()) {
                    // When all ships are placed set player to ready.
                    this.setReady(true);
                }
            }
        }
    }

    /**
     * This will find the next cell for the AI to attack. This depends on the 
     * state the AI is in. If the AI is in a search state then it will simply
     * find a random cell to attack. And if the AI is in kill state it will 
     * use a simple heuristic as a strategy to find the next best cell to 
     * attack and kill the enemy ship.
     */
    public void findNextCellToAttack() {
        System.out.println("-------------- New turn.");
        
        this.confirmLastAttemptHit();
        this.setCurrentState();
        
        Point selectedCell = null;
        while(!this.getTargetGrid().isValidTarget(selectedCell)){
            switch(this.state){
                case SEARCH:
                    if(this.currentState == null || !(this.currentState instanceof AISearchState)){
                        this.currentState = new AISearchState(this.gameModel, AbstractGrid.getGRID_SIZE());
                    }
                    break;
                case KILL:
                    if(this.currentState == null || !(this.currentState instanceof AIKillState)){
                        this.currentState = new AIKillState(this.gameModel, this.getTargetGrid());
                    }
                    break;
            }
            selectedCell = this.currentState.getNextCell();
            
            /* // Debug statement
            if(this.getLastAttemptCoords() == null){
                selectedCell = new Point(6, 1);
            }*/
            
            if (this.gameModel.getTargetGrid().isValidTarget(selectedCell)) {
                System.out.println(selectedCell.toString() + " selected was valid");
                this.gameModel.setLastSelectedValid(true);
            } else {
                System.out.println(selectedCell.toString() + " selected was invalid!");
                this.gameModel.setLastSelectedValid(false);
            }
        }
        
        if(this.getTargetGrid().isValidTarget(selectedCell)){
            this.getTargetGrid().setSelected((int)selectedCell.getX(), (int)selectedCell.getY());
            this.setLastAttemptCoords(selectedCell);
            System.out.println("Selected: " + selectedCell.toString());
        }
        else{
            this.getTargetGrid().resetSelected();
        }
    }
    
    // Used for debug only.
    private boolean confirmLastAttemptHit() {
        if(this.getLastAttemptCoords() != null){
            int lastX = (int) this.getLastAttemptCoords().getX();
            int lastY = (int) this.getLastAttemptCoords().getY();
            if (this.getTargetGrid().getLocationContense(lastX, lastY) == HitMarker.HIT) {
                System.out.println("LAST ATTEMPT WAS A HIT");
                return true;
            }
            else{
                System.out.println("LAST ATTEMPT WAS A MISS");
                return false;
            }
        }
        return false;
    }
    
    public void hitAShip(Point p){
        this.gameModel.getUnacountedHits().add(p);
    }
    
    private void setCurrentState() {
        if (this.gameModel.getUnacountedHits().isEmpty()) {
            if(this.state == State.KILL){
                this.state = State.SEARCH;
                System.out.println("CHANGING TO SEARCH");
            }
        }
        else{
            if(this.state == State.SEARCH){
                this.state = State.KILL;
                System.out.println("CHANGING TO KILL");
            }
        }
    }
    
    /**
     * @param sunkAShip the sunkAShip to set
     */
    public void setSunkAShip(int sunkAShip) {
        this.gameModel.sunkShipOfSize(sunkAShip);
    }
    
    /**
     * @return the lastAttemptCoords
     */
    public Point getLastAttemptCoords() {
        return this.gameModel.getLastAttempt();
    }

    /**
     * @param lastAttemptCoords the lastAttemptCoords to set
     */
    public void setLastAttemptCoords(Point lastAttemptCoords) {
        this.gameModel.setLastAttempt(lastAttemptCoords);
    }
}
