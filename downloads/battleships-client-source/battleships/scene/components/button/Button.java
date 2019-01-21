package battleships.scene.components.button;

import battleships.engine.audio.*;
import battleships.engine.events.IEventResponder;

/**
 *
 * @author Louis Bennette
 */
public class Button extends AbstractButton{
    
    public Button (double x, double y, double width, double height, String name, IEventResponder viewOrigin){
        super(x, y, width, height, name);
        this.eventResponder = viewOrigin;
        
    }
    
    @Override
    public void onEvent() {
        if(this.isVisible()){
            AudioManager.play(AudioManager.CLICK);
            this.eventResponder.handleEvent(this);
        }
    }
}