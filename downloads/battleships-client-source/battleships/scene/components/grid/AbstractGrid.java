package battleships.scene.components.grid;

import battleships.engine.OpenGLManager;
import battleships.engine.Point;
import battleships.engine.TextureManager;
import battleships.scene.components.AbstractComponent;
import battleships.scene.components.image.Image;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractGrid extends AbstractComponent{
    protected static final int GRID_SIZE = 10;


    protected double cellPixelSize;
    protected Texture cellHit;
    protected Texture cellMiss;
    protected Image borderImage;
    
    public enum HitMarker{
        NONE,
        HIT,
        MISS
    }
    
    public AbstractGrid(double x, double y, double cellSize){
        this.origin = new Point(x, y);
        this.cellPixelSize = cellSize;
        this.width = cellSize * GRID_SIZE;
        this.height = cellSize * GRID_SIZE;
        this.setTexture(TextureManager.loadTexture("grid-back"));
        double padding = cellSize / 4;
        this.borderImage = new Image(x-padding, y-padding, this.width+(padding*2), this.height+(padding*2), "grid-border");
    }
    
    /**
     * Empty constructor used for the AI.
     */
    public AbstractGrid(){
        // AI BUILT A GRID
    }

    @Override
    public void draw() {
        if (this.isVisible()) {
            this.borderImage.draw();
            OpenGLManager.drawSquareWithTex((float) this.getX(), (float) this.getY(),
                    (float) this.getWidth(), (float) this.getHeight(), this.getTexture());

            OpenGLManager.setColor(0, 0, 0);
            for (int i = 0; i <= getGRID_SIZE(); i++) {
                OpenGLManager.drawLine(new Point(origin.getX() + (i * this.cellPixelSize), origin.getY()), new Point(origin.getX() + (i * this.cellPixelSize), origin.getY() + this.getHeight()));
                OpenGLManager.drawLine(new Point(origin.getX(), origin.getY() + (i * this.cellPixelSize)), new Point(origin.getX() + this.getWidth(), origin.getY() + (i * this.cellPixelSize)));
            }
            OpenGLManager.setColor(255, 255, 255);
        }
    }
    
    
    @Override
    public void update() {
        
    }
    
    /**
     * Returns the cell in the grid at the screen coordinates given. Typically 
     * coordinates of a mouse pointer are given to retrieve what position in the 
     * grid the player has clicked.
     * @param x represents the screen pixel in the horizontal orientation where 
     * 0 is the left.
     * @param y represents the screen pixel in the vertical orientation where 0 
     * is the top.
     * @return the x and y value of the grid. as a point object.
     */
    public Point getGridCoordsAtPixel(double x, double y) {
        double relativeGridXPos = x - this.origin.getX();
        double relativeGridYPos = y - this.origin.getY();
        
        return new Point(relativeGridXPos/this.getCellPixelSize(), relativeGridYPos/this.getCellPixelSize());
    }

    
    /**
     * Returns the pixel size of the cell as a double.
     * @return the value of the cell's pixel size
     */
    public double getCellPixelSize() {
        return cellPixelSize;
    }

    /**
     * @return the GRID_SIZE
     */
    public static int getGRID_SIZE() {
        return GRID_SIZE;
    }
    
    /**
     * Returns whether the the points are within the bouns of the grid
     * @param x this is the X coord we wish to test.
     * @param y this is the Y coord we wish to test.
     * @return true id the given coords are within the bounds of the grid.
     */
    public boolean isInBounds(double x, double y){
        if(x>=AbstractGrid.getGRID_SIZE() || y>=AbstractGrid.getGRID_SIZE()){
            return false;
        }
        if(x<0 || y<0){
            return false;
        }
        return true;
    }
}
