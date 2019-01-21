package battleships.scene.view.player.AI;

import battleships.engine.Point;

/**
 *
 * @author Louis Bennette
 */
public abstract class AIBehaviour {
    // Default
    protected AIGameModel gameModel;
    protected AIBehaviour(AIGameModel gameModel){
        this.gameModel = gameModel;
    }
    
    public Point getNextCell(){
        return null;
    }

    /**
     * @return the gameModel
     */
    public AIGameModel getGameModel() {
        return gameModel;
    }

    /**
     * @param gameModel the gameModel to set
     */
    public void setGameModel(AIGameModel gameModel) {
        this.gameModel = gameModel;
    }
}
