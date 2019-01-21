package battleships.scene.components.ship;

import battleships.engine.*;
import battleships.scene.components.AbstractComponent;
import battleships.scene.components.grid.AbstractGrid;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractShip extends AbstractComponent {
    protected int size;
    protected int hitCounter;
    protected boolean horizontalOrientation;
    private boolean shipBeingMoved;
    
    protected boolean safeHorizontalOrientation;
    protected Point safeCoords;
    
    /**
     * Constructor only used for the user player's ships.
     */
    public AbstractShip(double x, double y, double width, double height){
        this.origin = new Point(x, y);
        this.width = width;
        this.height = height;
        this.shipBeingMoved = false;
        this.horizontalOrientation = true;
        
        this.safeCoords = new Point(x, y);
        this.safeHorizontalOrientation = true;
        
        // Used only in dev.
        //this.setTexture(TextureManager.loadTexture("generic-ship"));
    }
    
    /**
     * Constructor only used for AI ships.
     */
    public AbstractShip(){
        this.horizontalOrientation = true;
    }

    @Override
    public void draw() {
        if (this.isVisible()) {
            if (this.isHorizontalOrientation()) {
                OpenGLManager.drawSquareWithTex((float) this.getX(), (float) this.getY(), (float) this.getWidth(), (float) this.getHeight(), this.getTexture());
            } else {
                OpenGLManager.drawSquareWithTexAtRightAngle((float) this.getX(), (float) this.getY(), (float) this.getHeight(), (float) this.getWidth(), this.getTexture());
            }
        }
    }

    @Override
    public boolean hasMouseHover(){
        int mouseX = Mouse.getX();
        int mouseY = Window.getHeight()-Mouse.getY();
        if(this.isHorizontalOrientation()){
            if (mouseX > (int) this.getX() && mouseX < (int) this.getX() + this.getWidth()) {
                if (mouseY > (int) this.getY() && mouseY < (int) this.getY() + this.getHeight()) {
                    return true;
                }
            }
        }else{
            if (mouseX > (int) this.getX() && mouseX < (int) this.getX() + this.getHeight()) {
                if (mouseY > (int) this.getY() && mouseY < (int) this.getY() + this.getWidth()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void update() {
        if (isShipBeingMoved()) {
            int mouseX = Mouse.getX();
            int mouseY = Window.getHeight() - Mouse.getY();
            if (this.isHorizontalOrientation()) {
                this.setX(mouseX - this.getWidth() / 2);
                this.setY(mouseY - this.getHeight() / 2);
            } else {
                this.setX(mouseX - this.getHeight() / 2);
                this.setY(mouseY - this.getWidth() / 2);
            }

            if (Input.getKeyUp(Input.KEY_R)||Input.getMouseUp(Input.MOUSE_RIGHT)) {
                this.swapOrientation();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public void setSafeCoords(Point p){
        this.safeCoords.setX(p.getX());
        this.safeCoords.setY(p.getY());
    }
    
    public Point getSafeCoords(){
        return this.safeCoords;
    }
 
    public void markAsSafeLocation() {
        this.setShipBeingMoved(true);
        this.setSafeCoords(this.origin);
        this.safeHorizontalOrientation = this.isHorizontalOrientation();
    }
    
    public void returnToSafeLocation() {
        this.setShipBeingMoved(false);
        this.setX(this.safeCoords.getX());
        this.setY(this.safeCoords.getY());
        this.setHorizontalOrientation(this.safeHorizontalOrientation);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHorizontalOrientation() {
        return this.horizontalOrientation;
    }

    public void setHorizontalOrientation(boolean horizontalOrientation) {
        this.horizontalOrientation = horizontalOrientation;
    }
    
    private void swapOrientation(){
        this.setHorizontalOrientation(!this.isHorizontalOrientation());
    }

    public boolean isShipBeingMoved() {
        return shipBeingMoved;
    }

    public void setShipBeingMoved(boolean isShipBeingMoved) {
        this.shipBeingMoved = isShipBeingMoved;
    }
    
    public boolean isShipSunk(){
        if(this.size <= hitCounter){
            return true;
        }
        return false;
    }
    
    public void shipWasHit(){
        hitCounter++;
    }
    
    /**
     * Only used 
     * 
     * @param grid
     * @return 
     */
    public Point getShipHoverPositionForGrid(AbstractGrid grid) {
        double shipX = Mouse.getX();
        double shipY = Window.getHeight() - Mouse.getY();
        if (this.isHorizontalOrientation()) {
            shipX = shipX - (((double) this.getSize() / 2.0) * grid.getCellPixelSize() - grid.getCellPixelSize() / 2.0);
        } else {
            shipY = shipY - (((double) this.getSize() / 2.0) * grid.getCellPixelSize() - grid.getCellPixelSize() / 2.0);
        }
        Point p = grid.getGridCoordsAtPixel(shipX, shipY);
        return p;
    }
}
