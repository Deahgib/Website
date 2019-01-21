package battleships.scene.components.button;

import battleships.engine.GameLogger;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Louis Bennette
 */
public class ButtonRegistry {
    private List<AbstractButton> registry = new CopyOnWriteArrayList<AbstractButton>();
    
    public ButtonRegistry(){
        GameLogger.print("Created a new ButtonRegistry.");
    }
    
    public void add(AbstractButton ent){
        registry.add(ent);
    }
    
    public void draw(){
        for(AbstractButton button : registry){
            if(button.isVisible()){
                button.draw();
            }
        }
    }
    
    public void handleClickEvent(){
        for(AbstractButton button : registry){
            if(button.isVisible()){
                if(button.isClicked()){
                    button.onEvent();
                }
            }
        }
    }
    
    public AbstractButton getButtonWithName(String name) {
        Iterator<AbstractButton> it = registry.iterator();
        AbstractButton b = null;
        while(it.hasNext()){
            b = it.next();
            if(b.getName().equals(name)){
                return b;
            }
        }
        return null;
    }

    public void removeButtonWithName(String name) {
        Iterator<AbstractButton> it = registry.iterator();
        AbstractButton b = null;
        while(it.hasNext()){
            b = it.next();
            if(b.getName().equals(name)){
                registry.remove(b);
            }
        }
    }
}
