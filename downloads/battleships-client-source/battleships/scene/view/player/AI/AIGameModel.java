package battleships.scene.view.player.AI;

import battleships.engine.Point;
import battleships.scene.components.grid.AbstractGrid.HitMarker;
import battleships.scene.components.grid.TargetGrid;
import java.util.Iterator;

/**
 *
 * @author Louis Bennette
 */
public class AIGameModel {
    private TargetGrid targetGrid;
    
    private Point lastAttempt;
    
    private boolean lastSelectedValid;
    
    private int direction;
    
    private KillState killState;
    
    /**
     * This is substate used when in the kill state to help identify the 
     * orientation of the ship the AI is attacking.
     */
    public enum KillState{
        INDENTIFY, VERTICAL_MODE, HORIZONTAL_MODE
    }
    
    /**
     * This variable is 0 for false, no ship was sunk. And when a ship was sunk 
     * it's set to the size of the ship. When a submarine is sunk, the game will 
     * set this to 3 so the AI knows that it's last hit was sinking a ship of 
     * size 3 and the AI needs to remove 3 points from the unaccounted hits list
     * as they are now accounted for.
     */
    private int sunkAShip;
    
    /**
     * This is a list of all the hits that have been made by the AI
     * These are only unaccounted hits as once a ship is sunk all of the points 
     * belonging to that ship. Are removed from this list, this list only holds
     * hits of unsunk enemy ships.
     */
    private HitList unacountedHits;

    /**
     * This is the origin point of an attack vital.
     */
    private Point originAttack;
    
    public AIGameModel(TargetGrid targetGrid){
        this.unacountedHits = new HitList();
        this.targetGrid = targetGrid;
        this.sunkAShip = 0;
    }

    /**
     * @return the lastAttempt
     */
    public Point getLastAttempt() {
        return lastAttempt;
    }

    /**
     * @param lastAttempt the lastAttempt to set
     */
    public void setLastAttempt(Point lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    /**
     * @return the unacountedHits
     */
    public HitList getUnacountedHits() {
        return unacountedHits;
    }

    /**
     * @return the originOfAttack
     */
    public Point getOriginAttack() {
        return this.originAttack;
    }

    /**
     * @param originOfAttack the originOfAttack to set
     */
    public void setOriginAttack(Point originOfAttack) {
        this.originAttack = originOfAttack;
    }
    
    /**
     * @return the targetGrid
     */
    public TargetGrid getTargetGrid() {
        return targetGrid;
    }

    /**
     * @param targetGrid the targetGrid to set
     */
    public void setTargetGrid(TargetGrid targetGrid) {
        this.targetGrid = targetGrid;
    }

    /**
     * @return the lastAttemptValid
     */
    public boolean isLastSelectedValid() {
        System.out.println("Is last selected valid? " + lastSelectedValid);
        return lastSelectedValid;
    }

    /**
     * @param lastSelectedValid the lastAttemptValid to set
     */
    public void setLastSelectedValid(boolean lastAttemptValid) {
        this.lastSelectedValid = lastAttemptValid;
    }
    
    public void sunkShipOfSize(int shipSize) {
        //System.out.println("==========================================");
        //System.out.println("AI SUNK A SHIP OF SIZE " + shipSize);
        //System.out.println("# OF HITS IN LIST " + this.unacountedHits.size());
        
        if(this.unacountedHits.size() == shipSize){
            this.unacountedHits = new HitList();
        } else if (this.unacountedHits.size() > shipSize) {
            double lastX = this.lastAttempt.getX();
            double lastY = this.lastAttempt.getY();
            Point[] shipPoints = new Point[shipSize];
            // Check see if there is a line drawn between the origin 
            if(this.isLine(this.originAttack, this.lastAttempt)){
                if (this.isLineHorizontal(this.originAttack, this.lastAttempt)) {
                    if (this.lastAttempt.getX() > this.originAttack.getX()) {
                        for (int i = 0; i < shipSize; i++) {
                            Point tmp = new Point(this.lastAttempt.getX() - i, this.lastAttempt.getY());
                            if (this.unacountedHits.containsClone(tmp)) {
                                //System.out.println("Preping: " + tmp.toString());
                                shipPoints[i] = tmp;
                            } else {
                                //System.err.println("ERROR HIT NOT FOUND IN LIST FOR AI 0");
                            }
                        }
                    } else if (this.lastAttempt.getX() < this.originAttack.getX()) {
                        for (int i = 0; i < shipSize; i++) {
                            Point tmp = new Point(this.lastAttempt.getX() + i, this.lastAttempt.getY());
                            if (this.unacountedHits.containsClone(tmp)) {
                                //System.out.println("Preping: " + tmp.toString());
                                shipPoints[i] = tmp;
                            } else {
                                //System.err.println("ERROR HIT NOT FOUND IN LIST FOR AI 1");
                            }
                        }
                    } else {
                        //System.err.println("ERROR Origin X and LastAttempt X are equal for horizontal mode. Illegal.");
                    }

                } else // We assume the line drawn was vertical.
                {
                    if (this.lastAttempt.getY() > this.originAttack.getY()) {
                        for (int i = 0; i < shipSize; i++) {
                            Point tmp = new Point(this.lastAttempt.getX(), this.lastAttempt.getY() - i);
                            if (this.unacountedHits.containsClone(tmp)) {
                                //System.out.println("Preping: " + tmp.toString());
                                shipPoints[i] = tmp;
                            } else {
                                //System.err.println("ERROR HIT NOT FOUND IN LIST FOR AI 0");
                            }
                        }
                    } else if (this.lastAttempt.getY() < this.originAttack.getY()) {
                        for (int i = 0; i < shipSize; i++) {
                            Point tmp = new Point(this.lastAttempt.getX(), this.lastAttempt.getY() + i);
                            if (this.unacountedHits.containsClone(tmp)) {
                                //System.out.println("Preping: " + tmp.toString());
                                shipPoints[i] = tmp;
                            } else {
                                //System.err.println("ERROR HIT NOT FOUND IN LIST FOR AI 1");
                            }
                        }
                    } else {
                        //System.err.println("ERROR Origin Y and LastAttempt Y are equal for vertical mode. Illegal.");
                    }
                }
            }
            if (shipPoints.length == shipSize){
                for(int i = 0 ; i < shipPoints.length; i++){
                    //System.out.println("Removing: " + shipPoints[i].toString());
                    this.unacountedHits.remove(shipPoints[i]);
                }
            }
        }
        else{
            //System.out.println("Error due to invalid hit location collection -> Feature not yet implemented.");
        }
        
        
        //System.out.println("==========================================");
    }
    
    /**
     * 
     */
    private boolean isLine(Point p1, Point p2){
        if(p1.getY() == p2.getY()){
            if(p1.getX() != p2.getX()){
                return true;
            }
        }
        else if(p1.getX() == p2.getX()){
            if(p1.getY() != p2.getY()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check to see if the line drawn between p1 and p2 is a horizontal line or a vertical line.
     * @param p1 
     * @param p2
     * @return true if the line is horizontal
     */
    private boolean isLineHorizontal(Point p1, Point p2){
        if(p1.getY() == p2.getY()){
            if(p1.getX() != p2.getX()){
                return true;
            }
        }
        return false;
    }
    
    public boolean isHit(int x, int y){
        if(this.targetGrid.getLocationContense(x, y) == HitMarker.HIT){
            return true;
        }
        return false;
    }
    
    public void findNewOrigin(){
        if(this.unacountedHits.size() > 0){
            Iterator<Point> it = this.unacountedHits.iterator();
            Point possibleOrigin = null;
            while(it.hasNext()){
                possibleOrigin = it.next();
                if(this.isPossibleOrigin(possibleOrigin)){
                    this.setOriginAttack(possibleOrigin);
                    break;
                }
            }
            this.killState = KillState.INDENTIFY;
        }
    }
    
    public boolean isPossibleOrigin(Point p){
        if(this.getTargetGrid().isPoint(p.getX()+1, p.getY(), HitMarker.NONE)){
            return true;
        }
        else if(this.getTargetGrid().isPoint(p.getX()-1, p.getY(), HitMarker.NONE)){
            return true;
        }
        else if(this.getTargetGrid().isPoint(p.getX(), p.getY()+1, HitMarker.NONE)){
            return true;
        }
        else if(this.getTargetGrid().isPoint(p.getX(), p.getY()-1, HitMarker.NONE)){
            return true;
        }
        return false;
    }

    public void setKillState(KillState killState) {
        this.killState = killState;
    }
    
    public KillState getKillState() {
        return this.killState;
    }
}
