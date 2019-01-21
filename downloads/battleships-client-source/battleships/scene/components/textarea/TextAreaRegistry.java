package battleships.scene.components.textarea;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Louis Bennette
 */
public class TextAreaRegistry {
private List<TextField> registry = new CopyOnWriteArrayList<TextField>();
    
    public TextAreaRegistry(){}
    
    public void add(TextField ent){
        registry.add(ent);
    }
    
    public void draw(){
        for(TextField ent : registry){
            ent.draw();
        }
    }
    
    public void update(){
        for(TextField ent : registry){
            ent.update();
        }
    }

    public void removeTextAreaWithName(String name) {
        Iterator<TextField> it = registry.iterator();
        TextField ta = null;
        while(it.hasNext()){
            ta = it.next();
            if(ta.getName().equals(name)){
                registry.remove(ta);
            }
        }
    }
}
