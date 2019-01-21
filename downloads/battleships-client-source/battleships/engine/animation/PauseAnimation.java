package battleships.engine.animation;

import battleships.engine.Time;
import battleships.engine.events.IEventResponder;
import battleships.engine.events.TimeEvent;
import battleships.engine.events.TimeEventList;
import battleships.scene.SceneState;

/**
 *
 * @author Louis Bennette
 */
public class PauseAnimation extends AbstractAnimation{
    private long duration;
    private TimeEvent endEvent;
    
    public PauseAnimation(IEventResponder r, SceneState.Player rs, SceneState.Player ps, long timeMilliSecs){
        super(r, rs, ps);
        this.duration = timeMilliSecs;
        this.name = "pauseAnim";
    }
    
    @Override
    public void start(){
        this.endEvent = new TimeEvent(this.responder, this.duration, "depause");
        TimeEventList.add(this.endEvent);
        this.animationStarted = true;
    }

    @Override
    public void updateAnimation() {
    }

    @Override
    public void onEvent() {
    }
    
    private long resumeDuration;
    @Override
    public void prepareForPause(){
        this.endEvent.setPaused(true);
        long endTime = this.endEvent.getEndTime();
        long currentTime = Time.getTime();
        this.resumeDuration = endTime - currentTime;
    }
    @Override
    public void resetTimers(){
        long currentTime = Time.getTime();
        this.endEvent.setEndTime(currentTime + this.resumeDuration);
        
        this.endEvent.setPaused(false);
    }
}
