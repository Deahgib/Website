package battleships.scene.view.popupview;

import battleships.scene.view.AbstractView;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractPopupView extends AbstractView{
    protected AbstractView parent;
    protected boolean visible;
    private boolean closeRequested;
    private boolean fullControl;
    
    public AbstractPopupView(AbstractView parent, boolean fullControl, double x, double y, double width, double height){
        super(x, y, width, height);
        this.parent = parent;
        this.fullControl = fullControl;
        this.visible = false;
    }
    
    public boolean isCloseRequested(){
        return this.closeRequested;
    }
    
    public void setCloseRequested(boolean isClose){
        this.closeRequested = isClose;
    }
    
    public boolean isVisible(){
        return this.visible;
    }
    
    public void setVisible(boolean vis){
        if(vis){
            if(this.isFullControl()){
                this.parent.setPaused(this.parent, 0L);
            }
        }
        this.visible = vis;
    }
    
    public void closePopupView() {
        if (this.isFullControl()) {
            this.parent.getAnimation().onEvent();
        }
        this.parent.removeChildView(this);
    }

    public boolean isFullControl() {
        return fullControl;
    }

    public void setFullControl(boolean fullControl) {
        this.fullControl = fullControl;
    }
}
