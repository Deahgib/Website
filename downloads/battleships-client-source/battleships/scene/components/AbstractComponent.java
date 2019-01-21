package battleships.scene.components;

import battleships.engine.Point;
import battleships.engine.Window;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractComponent implements IComponent {
    protected Point origin;
    private Texture texture;
    
    protected String name;
    
    protected double width;
    protected double height;
    
    protected boolean visible = true;
    
    /**
     * This sets the location of the components top left corner.
     * @param x The x position on screen of the top left corner of a component.
     * @param y The y position on screen of the top left corner of a component.
     */
    @Override
    public void setLocation(double x, double y) {
        this.origin.setX(x);
        this.origin.setY(y);
    }
    
    /**
     * This sets the location of the components top left corner.
     * @param p The point representing the top left corner of a component.
     */
    @Override
    public void setLocation(Point p) {
        this.origin = p;
    }

    /**
     * This sets the x location of the components top left corner.
     * @param x The x position on screen of the top left corner of a component.
     */
    @Override
    public void setX(double x) {
        this.origin.setX(x);
    }

    /**
     * This sets the y location of the components top left corner.
     * @param y The y position on screen of the top left corner of a component.
     */
    @Override
    public void setY(double y) {
        this.origin.setY(y);
    }

    /**
     * This sets the width of the component.
     * @param width The width in pixels of the component.
     */
    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * This sets the height of the component.
     * @param height The height in pixels of the component.
     */
    @Override
    public void setHeight(double height) {
        this.height = height;
    }
    
    /**
     * @return The location of the component's top left corner as a Point.
     */
    @Override
    public Point getLocation() {
        return this.origin;
    }

    /**
     * @return The x location of the component's top left corner
     */
    @Override
    public double getX() {
        return this.origin.getX();
    }

    /**
     * @return The y location of the component's top left corner
     */
    @Override
    public double getY() {
        return this.origin.getY();
    }

    
    /**
     * @return The width in pixels of the component.
     */
    @Override
    public double getWidth() {
        return this.width;
    }
    
    /**
     * @return The height in pixels of the component.
     */
    @Override
    public double getHeight() {
        return this.height;
    }
    
    /**
     * @return Return the name of the component. "Unamed Component" is default.
     */
    public String getName(){
        if(this.name == null){
            this.name = "Unamed Component";
        }
        return this.name;
    }
    
    /**
     * Takes the mouse pixel location and checks if it is in the bounds of the 
     * component.
     * @return true if the mouse location is within the bounds of the component
     * as the method is called.
     */
    public boolean hasMouseHover() {
        int mouseX = Mouse.getX();
        int mouseY = Window.getHeight()-Mouse.getY();
        
        if(mouseX > (int)this.getX() && mouseX < (int)this.getX()+this.getWidth()){
            if(mouseY > (int)this.getY() && mouseY < (int)this.getY()+this.getHeight()){
                return true;
            }
        }
        return false;
    }

    /**
     * The components are all set to visible = true by default.
     * @return true if the component is set to visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the visibility of the component. false to disable drawing the 
     * component and updating of the component.
     * @param visible supply to set visibility.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the tex
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * @param tex the tex to set
     */
    public void setTexture(Texture tex) {
        this.texture = tex;
    }
}
