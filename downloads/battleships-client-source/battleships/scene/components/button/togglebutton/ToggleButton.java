package battleships.scene.components.button.togglebutton;

import battleships.engine.audio.AudioManager;
import battleships.engine.events.IEventResponder;
import battleships.scene.components.button.AbstractButton;

/**
 *
 * @author Louis Bennette
 */
public class ToggleButton extends AbstractButton {

    private State toggleState;
    
    public enum State{
        ON, OFF
    }
    
    public ToggleButton(double x, double y, double width, double height, String name, IEventResponder viewOrigin){
        super(x, y, width, height, (name));
        this.eventResponder = viewOrigin;
        this.setToggleState(State.OFF);
    }
    
    @Override
    public void onEvent() {
        if(this.getToggleState() == State.OFF){
            this.setToggleState(State.ON);
        }else if(this.getToggleState() == State.ON){
            this.setToggleState(State.OFF);
        }
        AudioManager.play(AudioManager.CLICK);
        this.eventResponder.handleEvent(this);
    }
    
    /**
     * @return the toggleState
     */
    public State getToggleState() {
        return toggleState;
    }

    /**
     * @param toggleState the toggleState to set
     */
    public void setToggleState(State toggleState) {
        this.toggleState = toggleState;
    }
}
