package battleships.engine.events;

import battleships.engine.Time;

/**
 *
 * @author Louis Bennette
 */
public class TimeEvent extends AbstractEvent{
    private long endTime;
    private boolean pause;
    
    
    /**
     * This time event will assume that the event will happen as soon as possible.
     * @param er
     * @param name 
     */
    public TimeEvent(IEventResponder er, String name){
        super(er, name);
        this.endTime = Time.getTime();
        this.pause = false;
    }
    
    public TimeEvent(IEventResponder er, long duration, String name){
        super(er, name);
        long curretnTime = Time.getTime();
        this.endTime = curretnTime + duration;
        this.pause = false;
    }
    
    /**
     * @return true if the event time is larger than the current time. i.e. We've
     * passed the event time.
     */
    public boolean isEventReady(){
        if(!this.isPaused()){
            long currentTime = Time.getTime();
            if(currentTime > this.getEndTime()){
                return true;
            }
        }
        return false;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isPaused() {
        return pause;
    }

    public void setPaused(boolean pause) {
        this.pause = pause;
    }
}
