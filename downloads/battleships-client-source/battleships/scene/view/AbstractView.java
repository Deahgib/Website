package battleships.scene.view;

import battleships.engine.GameLogger;
import battleships.engine.animation.AbstractAnimation;
import battleships.engine.animation.PauseAnimation;
import battleships.scene.SceneState;
import battleships.scene.components.image.Image;
import battleships.scene.view.popupview.AbstractPopupView;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractView implements IView{
    private List<AbstractPopupView> subViews = new CopyOnWriteArrayList<AbstractPopupView>();
    private AbstractPopupView activeSubview;
    private Stack<AbstractAnimation> allAnimations = new Stack<AbstractAnimation>();
    private AbstractAnimation animation;
    protected String name;
    protected Image background;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    protected AbstractView(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        GameLogger.print("Adding view " + this.toString());
    }
    
    
    protected boolean hasSubview(){
        if(!this.subViews.isEmpty()){
            return true;
        }
        return false;
    }
    
    public void setPaused(IView responder, long duration){
        this.setAnimation(new PauseAnimation(responder, SceneState.getPlayerState(), 
                SceneState.getPlayerState(), duration));
        this.getAnimation().setStarted(true);
        SceneState.setPlayerState(SceneState.Player.ANIMATING);
    }
    
    protected void addChildView(AbstractPopupView child){
        this.subViews.add(child);
    }
    
    public void removeChildView(AbstractPopupView child){
        Iterator<AbstractPopupView> it = this.subViews.iterator();
        IView removeView = null;
        while(it.hasNext()){
            removeView = it.next();
            if(removeView == child){
                this.subViews.remove(child);
            }
        }
        if (child == this.activeSubview) {
            this.activeSubview = null;
        }
    }
    
    protected boolean shouldUpdate(){
        if(this.hasSubview()){
            if(this.activeSubview != null){
                if(this.activeSubview.isFullControl()){
                    return false;
                }
            }
        }
        return true;
    }

    protected void updateSubviews() {
        if (this.activeSubview != null) {
            if (this.activeSubview.isVisible()) {
                this.activeSubview.update();
            } else {
                this.activeSubview.setVisible(true);
            }
        }
    }

    protected void drawSubviews() {
        if (this.activeSubview != null) {
            if (this.activeSubview.isVisible()) {
                this.activeSubview.draw();
            } else {
                this.activeSubview.setVisible(true);
            }
        }
    }

    public AbstractAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(AbstractAnimation animation) {
        if (animation != null) {
            if (!this.allAnimations.isEmpty()) {
                this.allAnimations.peek().prepareForPause();
            }

            this.allAnimations.push(animation);
            this.animation = animation;
        } else {
            if(this.animation != null){
                SceneState.setPlayerState(this.getAnimation().getResumeState());
            }
            this.animation = null;
        }
    }
    
    public void endAnimation(){
        this.getAnimation().end();
        this.allAnimations.pop();
        //System.out.println("Ended animation");
        if(!this.allAnimations.isEmpty()){
            this.animation = this.allAnimations.peek();
            this.animation.resetTimers();
        }else{
            this.setAnimation(null);
        }
    }

    public AbstractPopupView getActiveSubview() {
        return activeSubview;
    }

    public void setActiveSubview(AbstractPopupView activeSubview) {
        this.activeSubview = activeSubview;
    }
    
    @Override
    public double getX(){
        return this.x;
    }
    
    @Override
    public double getY(){
        return this.y;
    }
    
    @Override
    public double getWidth(){
        return this.width;
    }
    
    @Override
    public double getHeight(){
        return this.height;
    }
    
}
