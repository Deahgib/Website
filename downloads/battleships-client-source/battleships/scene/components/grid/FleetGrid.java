package battleships.scene.components.grid;

import battleships.engine.GameLogger;
import battleships.engine.OpenGLManager;
import battleships.engine.TextureManager;
import battleships.scene.components.ship.AbstractShip;
import org.newdawn.slick.opengl.Texture;

/**
 * @author Louis Bennette
 */
public class FleetGrid extends AbstractGrid {    
    private AbstractShip[][] fleetLocations;
    // Hitmaker is an enum in Abstract grid.
    private HitMarker[][] previousAttempts;
    
    private int currentNumberOfShipsInGrid;
    private Texture shipHoverTex;
    
    public FleetGrid(double x, double y, double cellSize){
        super(x, y, cellSize);
        this.fleetLocations = new AbstractShip[this.GRID_SIZE][this.GRID_SIZE];
        this.previousAttempts = new HitMarker[this.GRID_SIZE][this.GRID_SIZE];
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = 0; j < GRID_SIZE; j++){
                this.previousAttempts[i][j] = HitMarker.NONE;
            }
        }
        this.cellHit = TextureManager.loadTexture("targetcell-hit");
        this.cellMiss = TextureManager.loadTexture("targetcell-miss");
        this.shipHoverTex = TextureManager.loadTexture("hover");
        
        this.currentNumberOfShipsInGrid = 0;
    }
    
    public FleetGrid(){
        super();
        this.fleetLocations = new AbstractShip[GRID_SIZE][GRID_SIZE];
        this.currentNumberOfShipsInGrid = 0;
    }
    
    public void drawShipHoverBox(AbstractShip ship, int x, int y){
            double xPos = this.getX() + x * this.cellPixelSize;
            double yPos = this.getY() + y * this.cellPixelSize;
            double hoverWidth;
            double hoverHeight;
            if(ship.isHorizontalOrientation()){
                hoverWidth = ship.getWidth();
                hoverHeight = ship.getHeight();
            }
            else{
                hoverWidth = ship.getHeight();
                hoverHeight = ship.getWidth();
            }
            OpenGLManager.drawSquareWithTex((float)xPos, (float)yPos, (float)hoverWidth, (float)hoverHeight, this.shipHoverTex);
        
    }
    
    public void addShip(AbstractShip ship, int targetCellX, int targetCellY){
        boolean shipHorizontal = ship.isHorizontalOrientation();
        int shipSize = ship.getSize();
        GameLogger.print("Adding " + ship.getName() + " to the map.");
        currentNumberOfShipsInGrid ++;
        if(shipHorizontal){
            for(int i =0; i < shipSize; i++){
                this.fleetLocations[targetCellX+i][targetCellY] = ship;
            }
        }
        else
        {
            for(int i =0; i < shipSize; i++){
                this.fleetLocations[targetCellX][targetCellY+i] = ship;
            }
        }
    }
    
    public void placeShip(AbstractShip ship, int targetCellX, int targetCellY){
        // Place the ship at the right location
        ship.setX(this.origin.getX()+targetCellX * this.getCellPixelSize());
        ship.setY(this.origin.getY()+targetCellY * this.getCellPixelSize());
        ship.markAsSafeLocation();
    }
    
    public boolean hasShip(AbstractShip ship){
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = 0; j < GRID_SIZE; j++){
                if(this.fleetLocations[i][j] == ship){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void removeShip(AbstractShip ship){
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = 0; j < GRID_SIZE; j++){
                if(this.fleetLocations[i][j] == ship){
                    this.fleetLocations[i][j] = null;
                }
            }
        }
        currentNumberOfShipsInGrid --;
    }
    
    public void drawDecals(){
        for(int i=0; i < this.GRID_SIZE; i++){
            for(int j=0; j < this.GRID_SIZE; j++){
                if(this.previousAttempts[i][j] == HitMarker.MISS){
                    float selectedX = (float)(this.getCellPixelSize()*i + this.origin.getX());
                    float selectedY = (float)(this.getCellPixelSize()*j + this.origin.getY());
                    OpenGLManager.drawSquareWithTex(selectedX, selectedY, (float)this.getCellPixelSize(), (float)this.getCellPixelSize(), this.cellMiss);
                }
                else if(this.previousAttempts[i][j] == HitMarker.HIT){
                    float selectedX = (float)(this.getCellPixelSize()*i + this.origin.getX());
                    float selectedY = (float)(this.getCellPixelSize()*j + this.origin.getY());
                    OpenGLManager.drawSquareWithTex(selectedX, selectedY, (float)this.getCellPixelSize(), (float)this.getCellPixelSize(), this.cellHit);
                }
            }
        }
    }
    
    public void registerAttempt(int x, int y, HitMarker type){
        this.previousAttempts[x][y] = type;
    }
    
    /**
     * 
     * @param x coord
     * @param y coord
     * @return returns true if x, y represent the location of a ship.
     */
    public boolean isHit(int x, int y){
        if(this.fleetLocations[x][y] != null){
            this.getShipAt(x, y).shipWasHit();
            return true;
        }
        return false;
    }
    
    public boolean isSink(int x, int y){
        return this.getShipAt(x, y).isShipSunk();
    }
    
    public AbstractShip getShipAt(int targetCellX, int targetCellY){
        return fleetLocations[targetCellX][targetCellY];
    }
    
    public boolean isPositionValidFor(AbstractShip ship, int targetCellX, int targetCellY){
        boolean shipHorizontal = ship.isHorizontalOrientation();
        int shipSize = ship.getSize();
        
        if(targetCellX >= GRID_SIZE || targetCellX < 0){
            return false;
        }
        if(targetCellY >= GRID_SIZE || targetCellY < 0){
            return false;
        }
        
        if(shipHorizontal){
            if((targetCellX + ship.getSize()) > GRID_SIZE || targetCellX < 0){
                return false;
            }
            for(int i =0; i < shipSize; i++){
                if(fleetLocations[targetCellX+i][targetCellY]!=null){
                    return false;
                }
            }
        }
        else
        {
            if((targetCellY + ship.getSize()) > GRID_SIZE || targetCellY < 0){
                return false;
            }
            for(int i =0; i < shipSize; i++){
                if(fleetLocations[targetCellX][targetCellY+i]!=null){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isGridFilled(){
        if(currentNumberOfShipsInGrid == 5)
            return true;
            else
        return false;
    }
}
