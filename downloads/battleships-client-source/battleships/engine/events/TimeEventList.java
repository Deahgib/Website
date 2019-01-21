package battleships.engine.events;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Louis Bennette
 */
public class TimeEventList {
    private static List<TimeEvent> timeEvents = new CopyOnWriteArrayList<TimeEvent>();
    
    public static void add(TimeEvent event){
        timeEvents.add(event);
    }
    
    public static void handleEvent(){
        for(TimeEvent event : timeEvents){
            if(event.isEventReady()){
                event.onEvent();
                timeEvents.remove(event);
            }
        }
    }

    public static void cleanup() {
        Iterator<TimeEvent> it = timeEvents.iterator();
        TimeEvent ev;
        while(it.hasNext()){
            ev = it.next();
            if(ev == null){
                timeEvents.remove(ev);
            }
        }
    }
    
    public static boolean isEmpty(){
        return TimeEventList.timeEvents.isEmpty();
    }

    public static void remove(String eventName) {
        Iterator<TimeEvent> it = timeEvents.iterator();
        TimeEvent ev;
        while(it.hasNext()){
            ev = it.next();
            if(ev.getName().equals(eventName)){
                timeEvents.remove(ev);
                ev.setKillEvent(true);
            }
        }
    }
}
