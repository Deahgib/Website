package battleships.scene.view.popupview;

import battleships.GameLoop;
import battleships.engine.Input;
import battleships.engine.ResolutionManager;
import battleships.engine.events.IEvent;
import battleships.engine.events.TimeEvent;
import battleships.engine.events.TimeEventList;
import battleships.scene.SceneState;
import battleships.scene.components.button.Button;
import battleships.scene.components.button.ButtonRegistry;
import battleships.scene.components.image.Image;
import battleships.scene.components.label.Label;
import battleships.scene.view.AbstractView;

/**
 *
 * @author Louis Bennette
 */
public class GameOver extends AbstractPopupView {
    private ButtonRegistry menuButtons;
    private Label title;
    private Label message;
    
    public GameOver(AbstractView parent, boolean pauseGame, String message){
        super(parent, pauseGame, parent.getWidth()/4, parent.getHeight()/4, parent.getWidth()-parent.getWidth()/2, parent.getHeight()-parent.getHeight()/2);
        //super(parent, pauseGame, parent.getWidth()/8, parent.getHeight()/8, parent.getWidth()-parent.getWidth()/4, parent.getHeight()-parent.getHeight()/4);
        this.menuButtons = new ButtonRegistry();
        
        // Repackage this in the resolution singleton class.
        
        double bWidth = this.getWidth()/100 * 40;
        double bHeight = this.getHeight()/100 * 20;
        double b1XLocation = this.getX() + (this.getWidth()/100 * 50) - (bWidth+ResolutionManager.percentageAmountForValue(this.getWidth(), 5));
        double b2XLocation = this.getX() + this.getWidth()/100 * 50 + ResolutionManager.percentageAmountForValue(this.getWidth(), 5);
        double bYLocation = this.getY() + this.getHeight() - (bHeight+ResolutionManager.percentageAmountForValue(this.getHeight(), 5));
        
        this.background = new Image(this.getX(), this.getY(), this.getWidth() , this.getHeight(), "resize-popup");
        
        this.menuButtons.add(new Button(b1XLocation,bYLocation, bWidth, bHeight, "Replay", this));
        
        this.menuButtons.add(new Button(b2XLocation,bYLocation, bWidth, bHeight, "Main Menu", this));
        
        this.title = new Label(this.getX(), this.getY(), this.getWidth(), ResolutionManager.percentageAmountForValue(this.getHeight(),20), "title");
        this.title.textAlignCentered();
        this.title.setText("GAME OVER");
        
        this.message = new Label(this.getX(), this.getY()+ResolutionManager.percentageAmountForValue(this.getHeight(),20), this.getWidth(), ResolutionManager.percentageAmountForValue(this.getHeight(),10), "message");
        this.message.textAlignCentered();
        this.message.setText(message);
    }

    @Override
    public void update() {
        if(this.isVisible()){
            this.menuButtons.handleClickEvent();
        }
        if (Input.getKeyUp(Input.KEY_ESC)) {
            TimeEventList.add(new TimeEvent(this, "Main Menu")); // Give no durration to make the event instant.
        }
    }

    @Override
    public void draw() {
        if(this.isVisible()){
            this.background.draw();
            this.menuButtons.draw();
            this.title.draw();
            this.message.draw();
        }
    }

    @Override
    public void handleEvent(IEvent event) {
        switch (event.getName()) {
            case "Replay":
                GameLoop.setRestartCurrentView(true);
                break;
            case "Main Menu":
                SceneState.setGeneralState(SceneState.General.MENU);
                break;
        }
    }

    public String getMessage() {
        return message.getText();
    }

    public void setMessage(String msg) {
        this.message.setText(msg);
    }
}
