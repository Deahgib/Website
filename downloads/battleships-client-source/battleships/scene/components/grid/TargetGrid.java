package battleships.scene.components.grid;

import battleships.engine.OpenGLManager;
import battleships.engine.Point;
import battleships.engine.TextureManager;
import battleships.engine.audio.AudioManager;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Louis Bennette
 */
public class TargetGrid extends AbstractGrid {

    private Point selected;
    private HitMarker[][] previousLocations;
    private Texture cellSelected;
    
    public TargetGrid(double x, double y, double cellSize){
        super(x, y, cellSize);
        this.selected = null;
        this.previousLocations = new HitMarker[this.GRID_SIZE][this.GRID_SIZE];
        for(int i=0; i < this.GRID_SIZE; i++){
            for(int j=0; j < this.GRID_SIZE; j++){
                this.previousLocations[i][j] = HitMarker.NONE;
            }
        }
        this.cellHit = TextureManager.loadTexture("targetcell-hit");
        this.cellMiss = TextureManager.loadTexture("targetcell-miss");
        this.cellSelected = TextureManager.loadTexture("targetcell-selected");
    }
    
    public TargetGrid(){
        super();
        this.selected = null;
        this.previousLocations = new HitMarker[this.GRID_SIZE][this.GRID_SIZE];
        for(int i=0; i < this.GRID_SIZE; i++){
            for(int j=0; j < this.GRID_SIZE; j++){
                this.previousLocations[i][j] = HitMarker.NONE;
            }
        }
    }

    @Override
    public void draw() {
        if (this.isVisible()) {
            super.draw();
            // Other stuff.
            if (this.selected != null) {
                float selectedX = (float) (this.getCellPixelSize() * this.selected.getX() + this.origin.getX());
                float selectedY = (float) (this.getCellPixelSize() * this.selected.getY() + this.origin.getY());
                //OpenGLManager.setColor(200, 25, 25);
                OpenGLManager.drawSquareWithTex(selectedX, selectedY, (float) this.getCellPixelSize(), (float) this.getCellPixelSize(), this.cellSelected);
            }

            for (int i = 0; i < this.getGRID_SIZE(); i++) {
                for (int j = 0; j < this.getGRID_SIZE(); j++) {
                    if (this.previousLocations[i][j] == HitMarker.MISS) {
                        float selectedX = (float) (this.getCellPixelSize() * i + this.origin.getX());
                        float selectedY = (float) (this.getCellPixelSize() * j + this.origin.getY());
                        OpenGLManager.drawSquareWithTex(selectedX, selectedY, (float) this.getCellPixelSize(), (float) this.getCellPixelSize(), this.cellMiss);
                    } else if (this.previousLocations[i][j] == HitMarker.HIT) {
                        float selectedX = (float) (this.getCellPixelSize() * i + this.origin.getX());
                        float selectedY = (float) (this.getCellPixelSize() * j + this.origin.getY());
                        OpenGLManager.drawSquareWithTex(selectedX, selectedY, (float) this.getCellPixelSize(), (float) this.getCellPixelSize(), this.cellHit);
                    }
                }
            }
        }
    }
    
    public HitMarker getLocationContense(int x, int y){
        return this.previousLocations[x][y];
    }
    
    public void registerAttempt(int x, int y, HitMarker type){
        if(type == HitMarker.HIT){
            AudioManager.play(AudioManager.EXPLOSION);
        }
        this.previousLocations[x][y] = type;
    }

    public Point getSelected() {
        return selected;
    }
    
    public boolean isValidTarget(int x, int y){
        if(x < 0 || x >= this.getGRID_SIZE()){
            return false;
        }
        if(y < 0 || y >= this.getGRID_SIZE()){
            return false;
        }
        
        return this.previousLocations[x][y] == HitMarker.NONE;
    }
    
    public boolean isValidTarget(Point point){
        if(point == null){
            return false;
        }
        return this.isValidTarget((int)point.getX(), (int)point.getY());
    }
    
    /**
     * @param x
     * @param y 
     */
    public void setSelected(int x, int y) {
        if(this.isValidTarget(x, y)) this.selected = new Point(x, y);
    }
    
    /**
     * Resets the selected variable to null.
     */
    public void resetSelected() {
        this.selected = null;
    }
    
    /**
     * This is used to test whether the location given given by X and Y evaluates
     * to be the same as the given variable marker
     * @param x the X location we test for
     * @param y the Y location we test for
     * @param marker the HitMarker we want to evaluate
     * @return true if the location X and Y holds the same HitMarker as 
     * marker.
     */
    public boolean isPoint(double x, double y, HitMarker marker){
        if(this.isInBounds(x, y)){
            if(this.getLocationContense((int)x, (int)y) == marker){
                return true;
            }
        }
        return false;
    }

    
}
