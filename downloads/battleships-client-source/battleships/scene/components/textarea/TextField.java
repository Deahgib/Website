package battleships.scene.components.textarea;

import battleships.engine.Input;
import battleships.engine.InputReader;
import battleships.engine.Point;
import battleships.engine.audio.AudioManager;
import battleships.engine.events.IEvent;
import battleships.engine.events.IEventResponder;
import battleships.scene.components.AbstractComponent;
import battleships.scene.components.image.Image;
import battleships.scene.components.label.Label;

/**
 *
 * @author Louis Bennette
 */
public class TextField extends AbstractComponent implements IEvent {
    private IEventResponder responder;
    
    private boolean beingClicked;
    private boolean beingEdited;
    private boolean editable;
    
    private Label textLabel;
    private Image background;
    private Image backgroundHL;
    
    public TextField(double x, double y, double width, double height, String name, IEventResponder responder){
        this.setLocation(new Point(x, y));
        
        this.setWidth(width);
        this.setHeight(height);
        
        this.beingClicked = false;
        this.beingEdited = false;
        
        this.background = new Image(this.getX(), this.getY(), this.getWidth(), this.getHeight(), "resize-popup");
        this.backgroundHL = new Image(this.getX(), this.getY(), this.getWidth(), this.getHeight(), "resize-popup-highlight");
        
        this.name = name;
        this.textLabel = new Label(this.getX(), this.getY(), this.getWidth(), this.getHeight(), (name+"Label"));
        this.textLabel.setText("");
        this.textLabel.textAlignCentered();
        
        this.responder = responder;
    }
    
    @Override
    public void draw() {
        if (this.isVisible()) {
            if (this.isBeingEdited()) {
                this.backgroundHL.draw();
            } else {
                this.background.draw();
            }
            this.textLabel.draw();
        }
    }

    @Override
    public void update() {
        if(this.isClicked()){
            this.setBeingEdited(true);
            AudioManager.play(AudioManager.CLICK);
        }
        
        if(!this.hasMouseHover()){
            if(Input.getMouseDown(Input.MOUSE_LEFT)){
                this.setBeingEdited(false);
            }
        }
        
        if(this.isBeingEdited()){
            if(Input.getKeyUp(Input.KEY_BACK)){
                String text = this.textLabel.getText();
                if(!text.isEmpty()){
                    text = text.substring(0, (text.length()-1));
                    this.textLabel.setText(text);
                }
            }
            String tailText = InputReader.getInput();
            if(!tailText.isEmpty()){
                String text = this.textLabel.getText();
                text = text + tailText;
                this.textLabel.setText(text);
            }
            if(Input.getKeyUp(Input.KEY_ENTER)){
                onEvent();
            }
        }
    }
    
    
    
    private boolean isClicked(){
        if(this.hasMouseHover()){
            if(Input.getMouseDown(Input.MOUSE_LEFT)){
                this.beingClicked = true;
            }
        }
        if(Input.getMouseUp(Input.MOUSE_LEFT)){
            if(this.beingClicked && this.hasMouseHover()){
                return true;
            }
            else
            {
                this.beingClicked = false;
            }
        }
        return false;
    }

    public boolean isBeingEdited() {
        return beingEdited;
    }

    public void setBeingEdited(boolean beingEdited) {
        this.beingEdited = beingEdited;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getText() {
        return this.textLabel.getText();
    }

    public void setText(String text) {
        this.textLabel.setText(text);
    }

    @Override
    public void onEvent() {
        this.responder.handleEvent(this);
    }
}
