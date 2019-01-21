package battleships.scene.components.button;

import battleships.engine.Input;
import battleships.engine.OpenGLManager;
import battleships.engine.Point;
import battleships.engine.TextureManager;
import battleships.engine.events.IEvent;
import battleships.engine.events.IEventResponder;
import battleships.scene.components.AbstractComponent;
import battleships.scene.components.label.Label;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractButton extends AbstractComponent implements IEvent {
    private boolean buttonClicked;
    /**
     * We only load one static texture for all buttons.
     */
    private Texture hoverTex;
    /**
     * We only load one static texture for all buttons.
     */
    private Texture clickedTex;
    
    private Label textLabel;
    
    protected IEventResponder eventResponder;
    
    protected AbstractButton(double x, double y, double width, double height, String name){
        this.origin = new Point(x, y);
        this.width = width;
        this.height = height;
        this.name = name;
        this.buttonClicked = false;
        
        this.textLabel = new Label((x+10), y, (width-20), height, (name+"Label"));
        this.textLabel.setText(name);
        this.textLabel.textAlignCentered();
        
        this.setTexture(TextureManager.loadTexture("generic-button"));
        hoverTex = TextureManager.loadTexture("button-hover");
        clickedTex = TextureManager.loadTexture("button-clicked");
    }
    

    @Override
    public void draw() {
        //System.out.println("Drawing a button");
        if (this.isVisible()) {
            Texture drawTex = this.getTexture();
            if (this.hasMouseHover() && !this.buttonClicked) {
                drawTex = this.getHoverTex();
            } else if (this.buttonClicked) {
                drawTex = this.getClickedTex();
            }
            OpenGLManager.drawSquareWithTex((float) this.getX(), (float) this.getY(), (float) this.getWidth(), (float) this.getHeight(), drawTex);
            //System.out.println("!!!!!!!!!!!" + this.getX() + "," + this.getY());

            this.getButtonLabel().draw();
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * A button is clicked when it has been pressed and released in the bounds
     * of the button. This is used to execute the event of a button.
     * @return true is the button has been clicked. 
     */
    public boolean isClicked(){
        // We check first to see if the button has been pressed.
        if(this.hasMouseHover()){
            if(Input.getMouseDown(Input.MOUSE_LEFT)){
                // Button is pressed
                this.buttonClicked = true;
            }
        }
        // Now we check if the button is released withing the button's bounds.
        if(Input.getMouseUp(Input.MOUSE_LEFT)){
            if(this.buttonClicked && this.hasMouseHover()){
                this.buttonClicked = false;
                // Only return true if the button is pressed and released in the
                // bounds of the button.
                return true;
            }
            else
            {
                this.buttonClicked = false;
            }
        }
        return false;
    }

    /**
     * Set the name of the component.
     * @param name the name as a string.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the text of the label on the button.
     * @param text The text that is drawn to screen of the button.
     */
    public void setText(String text){
        this.getButtonLabel().setText(text);
    }
    
    /**
     * Retrieve the text of the button. The text is held by the label, this is a
     * nested call to the button's label.
     * @return the text of the button.
     */
    public String getText(){
        return this.getButtonLabel().getText();
    }

    /**
     * This is the Label object held by the button this hold all the text 
     * information of the button
     * @return a Label object of the button to be manipulated if needed.
     */
    public Label getButtonLabel() {
        return textLabel;
    }

    /**
     * @return the hoverTex
     */
    public Texture getHoverTex() { 
        return hoverTex;
    }
    /**
     * @param aHoverTex the hoverTex to set
     */
    public void setHoverTex(Texture aHoverTex) {
        hoverTex = aHoverTex;
    }
    
    /**
     * @return the clickedTex
     */
    public Texture getClickedTex() {
        return clickedTex;
    }
    /**
     * @param aClickedTex the clickedTex to set
     */
    public void setClickedTex(Texture aClickedTex) {
        clickedTex = aClickedTex;
    }
}
