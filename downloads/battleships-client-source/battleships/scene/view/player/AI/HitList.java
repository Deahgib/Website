package battleships.scene.view.player.AI;

import battleships.engine.GameLogger;
import battleships.engine.Point;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Louis Bennette
 */
public class HitList extends ArrayList<Point> {
    
    public HitList() {
        super();
    }
    
    public boolean add(Point p){
        if(!this.containsClone(p)){
            return super.add(p);
        }
        else{
            GameLogger.warning("AI: Illegal attempt to add same hit location twice.");
            return false;
        }
    }
    
    public boolean containsClone(Point p){
        Iterator<Point> it = this.iterator();
        Point listP;
        while(it.hasNext()){
            listP = it.next();
            if(listP.equals(p)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        Point p = (Point) o;
        Iterator<Point> it = this.iterator();
        int indexOfElement = -1;
        Point listP;
        while (it.hasNext()) {
            listP = it.next();
            if (listP.equals(p)) {
                indexOfElement = super.indexOf(listP);
                break;
            }
        }
        
        Point rtn = super.remove(indexOfElement);
        if (rtn == null) {
            return false;
        } else {
            return true;
        }
    }
}
