package battleships.engine.animation;

import battleships.engine.Point;
import battleships.engine.audio.AudioManager;
import battleships.engine.events.IEventResponder;
import battleships.scene.SceneState;
import battleships.scene.components.missile.Missile;

/**
 *
 * @author Louis Bennette
 */
public class MissileAnimation extends AbstractAnimation{
    private Point actionLocation;
    private boolean actionType;
    
    private PauseAnimation pause;
    private Missile missile;
    
    public MissileAnimation(IEventResponder r, SceneState.Player returnState, SceneState.Player previousState, Point p, boolean towardsUser, long timeMilliSecs){
        super(r, returnState, previousState);
        this.missile = new Missile(p, towardsUser);
        this.name = "missileAnim";
        
        this.pause = new PauseAnimation(r, returnState, previousState, timeMilliSecs);
    }
    
    @Override
    public void start(){
        super.start();
        AudioManager.play(AudioManager.MISSILE_FLY);
    }
    
    @Override
    public void updateAnimation() {
        if (!this.missile.isMissileEnded()) {
            this.missile.update();
        } else {
            if(!this.pause.isAnimationStarted()){
                this.onEvent();
                this.pause.start();
            }
        }
    }
    
    @Override
    public void drawAnimation(){
        if(!this.missile.isMissileEnded()){
            this.missile.draw();
        }
    }
    
    public Point getActionLocation() {
        return actionLocation;
    }

    /**
     * @return True if the action is happening to the user, False if the action
     * is caused by the user and is towards the opponent.
     */
    public boolean getActionType() {
        return actionType;
    }

    @Override
    public void onEvent() {
        AudioManager.stop(AudioManager.MISSILE_FLY);
        this.responder.handleEvent(this);
    }
    
    @Override
    public void prepareForPause(){
        if(this.pause.isAnimationStarted()){
            this.pause.prepareForPause();
        }
    }
    
    @Override
    public void resetTimers() {
        if (this.pause.isAnimationStarted()) {
            this.pause.resetTimers();
        }
    }   
}