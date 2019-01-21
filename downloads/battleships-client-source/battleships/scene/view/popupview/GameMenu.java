package battleships.scene.view.popupview;

import battleships.GameLoop;
import battleships.engine.GameGlobals;
import battleships.engine.Input;
import battleships.engine.Window;
import battleships.engine.events.IEvent;
import battleships.engine.events.TimeEvent;
import battleships.engine.events.TimeEventList;
import battleships.scene.SceneState;
import battleships.scene.components.button.Button;
import battleships.scene.components.button.ButtonRegistry;
import battleships.scene.components.button.togglebutton.ToggleButton;
import battleships.scene.components.image.Image;
import battleships.scene.view.AbstractView;
import battleships.scene.view.multiplayer.MultiplayerGame;

/**
 *
 * @author Louis Bennette
 */
public class GameMenu extends AbstractPopupView {
    private ButtonRegistry menuButtons;
    
    public GameMenu(AbstractView parent, boolean pauseGame){
        super(parent, pauseGame, 0, 0, Window.getWidth(), Window.getHeight());
        this.menuButtons = new ButtonRegistry();
        
        // Repackage this in the resolution singleton class.
        double bXLocation = this.getX() + this.getWidth()/100 * 15;
        double bWidth = this.getWidth()/100 * 20;
        double bHeight = this.getHeight()/100 * 10;
        double bVerticalPadding = bHeight;
        double yMargin = this.getY() + this.getHeight()/100 * 20;
        double bNumber = 0;
        
        this.background = new Image(this.getX(), this.getY(), this.getWidth() , this.getHeight(), "game-menu-back");
        
        this.menuButtons.add(new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "Resume", this));
        bNumber++;
        if(!(parent instanceof MultiplayerGame)){
            this.menuButtons.add(new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "Replay", this));
        }
        bNumber++;
        ToggleButton toggleMusic = new ToggleButton(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "Sound", this);
        if(GameGlobals.gameMusicOn){
            toggleMusic.setText("Sound: ON");
            toggleMusic.setToggleState(ToggleButton.State.ON);
        }else{
            toggleMusic.setText("Sound: OFF");
            toggleMusic.setToggleState(ToggleButton.State.OFF);
        }
        this.menuButtons.add(toggleMusic);
        bNumber++;
        this.menuButtons.add(new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "Exit Game", this));
    }
    
    @Override
    public void update() {
        if(this.isVisible()){
            this.menuButtons.handleClickEvent();
        }
        if (Input.getKeyUp(Input.KEY_ESC)) {
            TimeEventList.add(new TimeEvent(this, "Resume")); // Give no durration to make the event instant.
        }
    }

    @Override
    public void draw() {
        if(this.isVisible()){
            this.background.draw();
            this.menuButtons.draw();
        }
    }

    @Override
    public void handleEvent(IEvent event) {
        switch(event.getName()){
            case "Resume":
                this.parent.removeChildView(this);
                if(this.isFullControl()){
                    this.parent.endAnimation();
                }
                break;
            case "Replay":
                GameLoop.setRestartCurrentView(true);
                break;
            case "Sound":
                ToggleButton tb = (ToggleButton)event;
                if(tb.getToggleState() == ToggleButton.State.OFF){
                    GameGlobals.gameMusicOn = false;
                    GameGlobals.gameEffectSoundsOn = false;
                    tb.setText("Sound: OFF");
                }else if(tb.getToggleState() == ToggleButton.State.ON){
                    GameGlobals.gameMusicOn = true;
                    GameGlobals.gameEffectSoundsOn = true;
                    tb.setText("Sound: ON");
                }
                break;
            case "Exit Game":
                if (parent instanceof MultiplayerGame) {
                    MultiplayerGame p = (MultiplayerGame) parent;
                    p.disconnectFromServer();
                }
                SceneState.setGeneralState(SceneState.General.MENU);

                break;
        }
    }
}
