package battleships.scene.components.missile;

import battleships.engine.OpenGLManager;
import battleships.engine.Point;
import battleships.engine.ResolutionManager;
import battleships.engine.TextureManager;
import battleships.engine.Time;
import battleships.scene.components.AbstractComponent;
import battleships.scene.view.AbstractGame;

/**
 *
 * @author Louis Bennette
 */
public class Missile extends AbstractComponent {
    
    private boolean incoming;

    private double currentVelocity;
    private double previousVelocity;
    private double acceleration;
    
    private boolean missileEnded;
    
    private double targetHeight;
    
    public Missile(Point p, boolean incoming){
        this.missileEnded = false;
        ResolutionManager dimentions = AbstractGame.dimentions;
        // Set the dimentions of the missile based on the size of a grid cell.
        double cellSize = dimentions.getGridCellHeight();
        this.setWidth(cellSize / 8);
        this.setHeight(cellSize / 2);
        this.incoming = incoming;
        
        // Set the location of a missile basedon the given coordinate.
        double xMargin = dimentions.getLeftMargin();
        double yMargin = dimentions.getTopMargin();
        int xCoord = (int)p.getX();
        int yCoord = (int)p.getY();
        double x = (xMargin + (cellSize - this.getWidth())/2) + xCoord * cellSize;
        double y = -this.getHeight();

        this.origin = new Point(x, y);
        this.targetHeight = (yMargin + yCoord * cellSize);
        
        // Set mechanical properties.
        this.currentVelocity = 0;
        this.previousVelocity = 100;
        this.acceleration = -100;
        if(incoming){
            this.acceleration = this.acceleration * -1;
        }
        
        // Set the texture.
        this.setTexture(TextureManager.loadTexture("missile"));
    }
    
    @Override
    public void draw() {
        if (this.isVisible()) {
            OpenGLManager.drawSquareWithTex((float) this.getX(), (float) this.getY(), (float) this.getWidth(), (float) this.getHeight(), this.getTexture());
        }
    }

    @Override
    public void update() {
        if(!this.isMissileEnded()){
            double time = Time.getDelta();
            // V = u + at
            this.currentVelocity = this.previousVelocity + this.acceleration * time;
            // S = ut + 1/2at^2
            double distance = (this.previousVelocity * time) + (0.5 * this.acceleration * (time * time));
            this.setY(this.getY() + distance);
            
            this.previousVelocity = this.currentVelocity;
            //System.out.println(this.previousVelocity);
            if(this.getY() >= this.targetHeight){
                this.missileEnded = true;
            }
        }
    }

    public boolean isMissileEnded() {
        return missileEnded;
    }
}
