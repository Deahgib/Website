package battleships.engine.animation;

import battleships.engine.events.IEvent;
import battleships.scene.SceneState;


/**
 *
 * @author Louis Bennette
 */
public interface IAnimation extends IEvent{
    public void updateAnimation();
    public void drawAnimation();
    
    public void start();
    public void end();
    public boolean isAnimationStarted();
    public boolean isAnimationEnded();
    public SceneState.Player getResumeState();
    public SceneState.Player getPreviousState();
}
