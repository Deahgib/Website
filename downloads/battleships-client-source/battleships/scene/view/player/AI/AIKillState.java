package battleships.scene.view.player.AI;

import battleships.engine.Point;
import battleships.scene.components.grid.AbstractGrid;
import battleships.scene.components.grid.AbstractGrid.HitMarker;
import battleships.scene.components.grid.TargetGrid;
import java.util.Random;

/**
 *
 * @author Louis Bennette
 */
public class AIKillState extends AIBehaviour{
    
    public AIKillState(AIGameModel gameModel, TargetGrid tg){
        super(gameModel);
        gameModel.setKillState(AIGameModel.KillState.INDENTIFY);
        this.gameModel.setOriginAttack(this.gameModel.getLastAttempt());
        //System.out.println("Kill behaviour loaded");
    }
    
    /**
     * This will be called when the AI is in it's kill state. The heuristic 
     * here is to sink the enemy ships as efficiently as possible first by 
     * identifying the orientation of the ship and by focusing on emptying the
     * unaccounted hit's list before resuming to search state.
     * More on the strategy in the project's Design document Section 2.3.
     * 
     * @return the next point to attack the enemy at.
     */
    @Override
    public Point getNextCell() {
        Point nextCell = null;
        //System.out.println("PreviousState=" + this.gameModel.getKillState());
        // Maybe need to do some checking to see if the origin is still in the list and if it isn't choose a new origin.
        this.updateState();

        //System.out.println("Substate=" + this.gameModel.getKillState());
        switch (this.gameModel.getKillState()) {
            case INDENTIFY:
                nextCell = this.identifyMode();
                break;
            case HORIZONTAL_MODE:
                nextCell = this.horizontalMode();
                break;
            case VERTICAL_MODE:
                nextCell = this.verticalMode();
                break;
        }
        
        //System.out.println(this.gameModel.getUnacountedHits().size() + " unaccounted hits in list.");
        
        return nextCell;
    }

    private void updateState(){
        Point origin = this.gameModel.getOriginAttack();
        Point lastAttempt = this.gameModel.getLastAttempt();
        switch (this.gameModel.getKillState()) {
            case INDENTIFY:
                if (this.isLastAttemptHit()) {
                    if (origin.getX() == lastAttempt.getX()) {
                        if (origin.getY() != lastAttempt.getY()) {
                            gameModel.setKillState(AIGameModel.KillState.VERTICAL_MODE);
                        }
                    }
                    if (origin.getY() == lastAttempt.getY()) {
                        if (origin.getX() != lastAttempt.getX()) {
                            gameModel.setKillState(AIGameModel.KillState.HORIZONTAL_MODE);
                        }
                    }
                }
                break;
            case HORIZONTAL_MODE:
                if (!this.isHorizontalKillPosible()) {
                    gameModel.setKillState(AIGameModel.KillState.INDENTIFY);
                }
                break;
            case VERTICAL_MODE:
                if (!this.isVerticalKillPosible()) {
                    gameModel.setKillState(AIGameModel.KillState.INDENTIFY);
                }
                break;
        }
        if (!this.gameModel.getUnacountedHits().containsClone(this.gameModel.getOriginAttack())) {
            // Find new origin!
            this.gameModel.findNewOrigin();
        }
    }
    
    private Point identifyMode() {
        Point nextCell = null;
        Random rand = new Random();
        // Is it the first hit 
        int selectedX = -1;
        int selectedY = -1;
        while (!this.gameModel.getTargetGrid().isValidTarget(selectedX, selectedY)) {
            // Look of new origin if the current origin is invalid
            if(!this.gameModel.isPossibleOrigin(this.gameModel.getOriginAttack())){
                this.gameModel.findNewOrigin();
            }
            
            // Find a random spot to attack.
            int orient = rand.nextInt(2);
            int direction = rand.nextInt(2);
            if (orient == 0) {
                selectedY = (int) this.gameModel.getOriginAttack().getY();
                if (direction == 0) {
                    selectedX = (int) this.gameModel.getOriginAttack().getX() + 1;
                } else {
                    selectedX = (int) this.gameModel.getOriginAttack().getX() - 1;
                }
            } else {
                selectedX = (int) this.gameModel.getOriginAttack().getX();
                if (direction == 0) {
                    selectedY = (int) this.gameModel.getOriginAttack().getY() + 1;
                } else {
                    selectedY = (int) this.gameModel.getOriginAttack().getY() - 1;
                }
            }
        }
        nextCell = new Point(selectedX, selectedY);

        return nextCell;
    }
    
    private Point horizontalMode() {
        Point origin = this.gameModel.getOriginAttack();
        Point lastAttmept = this.gameModel.getLastAttempt();
        double selectedX = -1; // Make invalid
        if(this.isLastAttemptHit() && this.gameModel.isLastSelectedValid()){
            if(origin.getX() < lastAttmept.getX()){
                selectedX = lastAttmept.getX()+1;
                while(this.gameModel.getTargetGrid().isPoint(selectedX, this.gameModel.getLastAttempt().getY(), HitMarker.HIT)){
                    selectedX++;
                }
            }
            else if(origin.getX() > lastAttmept.getX()){
                selectedX = lastAttmept.getX()-1;
                while(this.gameModel.getTargetGrid().isPoint(selectedX, this.gameModel.getLastAttempt().getY(), HitMarker.HIT)){
                    selectedX--;
                }
            }
        } else if (this.isLastAttemptMiss() || !this.gameModel.isLastSelectedValid()) {
            if (origin.getX() < lastAttmept.getX()) {
                selectedX = origin.getX() - 1;
                while(this.gameModel.getTargetGrid().isPoint(selectedX, this.gameModel.getLastAttempt().getY(), HitMarker.HIT)){
                    selectedX--;
                }
            } else if (origin.getX() > lastAttmept.getX()) {
                selectedX = origin.getX() + 1;
                while(this.gameModel.getTargetGrid().isPoint(selectedX, this.gameModel.getLastAttempt().getY(), HitMarker.HIT)){
                    selectedX++;
                }
            }
        }
        return new Point(selectedX, origin.getY());
    }
    
    
    private Point verticalMode() {
        Point origin = this.gameModel.getOriginAttack();
        Point lastAttmept = this.gameModel.getLastAttempt();
        double selectedY = -1; // Make invalid
        if(this.isLastAttemptHit() && this.gameModel.isLastSelectedValid()){
            if(origin.getY() < lastAttmept.getY()){
                selectedY = lastAttmept.getY()+1;
                while(this.gameModel.getTargetGrid().isPoint(this.gameModel.getLastAttempt().getX(), selectedY, HitMarker.HIT)){
                    selectedY++;
                }
            }
            else if(origin.getY() > lastAttmept.getY()){
                selectedY = lastAttmept.getY()-1;
                while(this.gameModel.getTargetGrid().isPoint(this.gameModel.getLastAttempt().getX(), selectedY, HitMarker.HIT)){
                    selectedY--;
                }
            }
        } else if (this.isLastAttemptMiss() || !this.gameModel.isLastSelectedValid()) {
            if (origin.getY() < lastAttmept.getY()) {
                selectedY = origin.getY() - 1;
                while(this.gameModel.getTargetGrid().isPoint(this.gameModel.getLastAttempt().getX(), selectedY, HitMarker.HIT)){
                    selectedY--;
                }
            } else if (origin.getY() > lastAttmept.getY()) {
                selectedY = origin.getY() + 1;
                while(this.gameModel.getTargetGrid().isPoint(this.gameModel.getLastAttempt().getX(), selectedY, HitMarker.HIT)){
                    selectedY++;
                }
            }
        }
        return new Point(origin.getX(), selectedY);
    }
    
    private boolean isHorizontalKillPosible(){
        Point origin = this.gameModel.getOriginAttack();
        double orgX = origin.getX();
        double orgY = origin.getY();
        
        int changeDirection = 0;
        while(changeDirection < 2){
            if(changeDirection == 0){
                orgX++;
            }else if(changeDirection == 1){
                orgX--;
            }

            if(this.gameModel.getTargetGrid().isPoint(orgX, orgY, HitMarker.NONE)){
                return true;
            }
            else if(this.gameModel.getTargetGrid().isPoint(orgX, orgY, HitMarker.MISS) || !this.gameModel.getTargetGrid().isInBounds(orgX, orgY)){
                changeDirection++;
                orgX = origin.getX();
                orgY = origin.getY();
            }
        }
        return false;
    }
    
    private boolean isVerticalKillPosible(){
        Point origin = this.gameModel.getOriginAttack();
        double orgX = origin.getX();
        double orgY = origin.getY();
        
        int changeDirection = 0;
        while(changeDirection < 2){
            if(changeDirection == 0){
                orgY++;
            }else if(changeDirection == 1){
                orgY--;
            }
            
            if(this.gameModel.getTargetGrid().isPoint(orgX, orgY, HitMarker.NONE)){
                return true;
            }
            else if(this.gameModel.getTargetGrid().isPoint(orgX, orgY, HitMarker.MISS)){
                changeDirection++;
                orgX = origin.getX();
                orgY = origin.getY();
            }
        }
        return false;
    }
    
    private boolean isLastAttemptHit() {
        double lastX = this.gameModel.getLastAttempt().getX();
        double lastY = this.gameModel.getLastAttempt().getY();
        return this.gameModel.getTargetGrid().isPoint(lastX, lastY, AbstractGrid.HitMarker.HIT);
    }

    private boolean isLastAttemptMiss() {
        double lastX = this.gameModel.getLastAttempt().getX();
        double lastY = this.gameModel.getLastAttempt().getY();
        return this.gameModel.getTargetGrid().isPoint(lastX, lastY, AbstractGrid.HitMarker.MISS);
    }
}
