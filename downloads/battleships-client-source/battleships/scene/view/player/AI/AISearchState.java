package battleships.scene.view.player.AI;

import battleships.engine.Point;
import java.util.Random;

/**
 *
 * @author Louis Bennette
 */
public class AISearchState extends AIBehaviour {
    
    private int gridSize;
    
    public AISearchState(AIGameModel gameModel, int gridSize){
        super(gameModel);
        this.gridSize = gridSize;
        //System.out.println("Search behaviour loaded");
    }
    
    /**
     * This will be called when the AI is in it's search state. The heuristic 
     * here is to semi randomly attack a cell in order to locate ships.
     * More on the strategy in the project's Design document Section 2.3.
     * 
     * @return the next point to attack to enemy.
     */
    @Override
    public Point getNextCell() {
        Random r = new Random();
        int state = r.nextInt(2);
        int x;
        int y;
        
        if(state == 0){ // X is even
            x = r.nextInt(getGridSize());
            if(x%2 == 1){
                x--;
            }
            y = r.nextInt(getGridSize());
            if(y%2 == 0){
                y++;
            }
        }
        else{ // X is odd
            x = r.nextInt(getGridSize());
            if(x%2 == 0){
                x++;
            }
            y = r.nextInt(getGridSize());
            if(y%2 == 1){
                y--;
            }
        }
        return new Point(x, y);
    }

    /**
     * @return the gridSize
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * @param gridSize the gridSize to set
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
}
