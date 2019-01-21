package battleships.engine.animation;

import battleships.engine.events.IEventResponder;
import battleships.scene.SceneState;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractAnimation implements IAnimation {
    protected IEventResponder responder;
    protected SceneState.Player resumeState;
    protected SceneState.Player previousState;
    protected boolean animationStarted;
    protected boolean animationEnded;
    protected String name;
    
    /**
     * 
     * @param r the class that the animation will respond to with onAction() 
     * events.
     * @param rs The state that must be resumed once the animation is over.
     * @param ps The previous state that the game was in before the animation 
     * started..
     */
    protected AbstractAnimation(IEventResponder r, SceneState.Player rs, SceneState.Player ps){
        this.responder = r;
        this.resumeState = rs;
        this.previousState = ps;
    }
    
    @Override
    public void drawAnimation(){}
    
    @Override
    public void start() {
        this.animationStarted = true;
    }

    @Override
    public void end() {
        this.animationEnded = true;
    }

    public void setStarted(boolean val){
        this.animationStarted = val;
    }
    
    @Override
    public boolean isAnimationStarted() {
        return animationStarted;
    }

    @Override
    public boolean isAnimationEnded() {
        return animationEnded;
    }

    @Override
    public SceneState.Player getResumeState() {
        return resumeState;
    }

    @Override
    public SceneState.Player getPreviousState() {
        return previousState;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * 
     */
    public void prepareForPause(){
        // D nothng by default.
    }
    /**
     * Override this method to handle timer based systems for them to be pause-
     * able.
     */
    public void resetTimers(){
        //Do nothing by default.
    }
}
