package battleships.scene.view.popupview;

import battleships.engine.GameGlobals;
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
import battleships.scene.components.textarea.TextField;
import battleships.scene.view.AbstractView;
import battleships.scene.view.multiplayer.Login;
import battleships.scene.view.multiplayer.MultiplayerGame;

/**
 *
 * @author Louis Bennette
 */
public class UsernamePopup extends AbstractPopupView {

    private ButtonRegistry menuButtons;
    private Label title;
    private TextField textField;
    private boolean logingIn;

    public UsernamePopup(AbstractView parent, boolean pauseGame) {
        super(parent, pauseGame, parent.getWidth() / 4, parent.getHeight() / 4, parent.getWidth() - parent.getWidth() / 2, parent.getHeight() - parent.getHeight() / 2);
        //super(parent, pauseGame, parent.getWidth()/8, parent.getHeight()/8, parent.getWidth()-parent.getWidth()/4, parent.getHeight()-parent.getHeight()/4);
        this.menuButtons = new ButtonRegistry();

        // Repackage this in the resolution singleton class.

        double bWidth = this.getWidth() / 100 * 40;
        double bHeight = this.getHeight() / 100 * 20;
        double b1XLocation = this.getX() + (this.getWidth() / 100 * 50) - (bWidth + ResolutionManager.percentageAmountForValue(this.getWidth(), 5));
        double b2XLocation = this.getX() + this.getWidth() / 100 * 50 + ResolutionManager.percentageAmountForValue(this.getWidth(), 5);
        double bYLocation = this.getY() + this.getHeight() - (bHeight + ResolutionManager.percentageAmountForValue(this.getHeight(), 5));

        this.background = new Image(this.getX(), this.getY(), this.getWidth(), this.getHeight(), "resize-popup");

        this.menuButtons.add(new Button(b1XLocation, bYLocation, bWidth, bHeight, "Back", this));

        this.menuButtons.add(new Button(b2XLocation, bYLocation, bWidth, bHeight, "Login", this));

        this.title = new Label(this.getX(), this.getY(), this.getWidth(), ResolutionManager.percentageAmountForValue(this.getHeight(), 20), "title");
        this.title.textAlignCentered();
        this.title.setText("Enter your username:");
        
        this.textField = new TextField(this.getX()+this.getWidth()/4, this.getY()+ResolutionManager.percentageAmountForValue(this.getHeight(), 35), this.getWidth()/2, ResolutionManager.percentageAmountForValue(this.getHeight(), 20), "textField", this);
        this.textField.setBeingEdited(true);
        
        this.logingIn = false;
        
        if(GameGlobals.DEBUG_MODE){
            this.textField.setText("devClient");
            TimeEventList.add(new TimeEvent(this, "Login"));
        }
        
        if(GameGlobals.getOnlineUserName() != null){
            String username = GameGlobals.getOnlineUserName();
            if(username.length() >= 3 && username.length() <= 16){
            this.textField.setText(GameGlobals.getOnlineUserName());
            TimeEventList.add(new TimeEvent(this, "Login"));
            }
        }
    }

    @Override
    public void update() {
        if (this.isVisible()) {
            this.menuButtons.handleClickEvent();
            this.textField.update();
        }
        if (Input.getKeyUp(Input.KEY_ESC)) {
            TimeEventList.add(new TimeEvent(this, "Back")); // Give no durration to make the event instant.
        }
        if (Input.getKeyUp(Input.KEY_ENTER)) {
            TimeEventList.add(new TimeEvent(this, "Login")); // Give no durration to make the event instant.
        }
    }

    @Override
    public void draw() {
        if (this.isVisible()) {
            this.background.draw();
            this.menuButtons.draw();
            this.title.draw();
            this.textField.draw();
        }
    }

    @Override
    public void handleEvent(IEvent event) {
        switch (event.getName()) {
            case "Login":
                if (this.textField.getText().length() >= 3 && this.textField.getText().length() <= 16) {
                    if(!this.isLogingIn()){
                        Thread logginThread = new Thread(new Login(this.textField.getText(), this, (MultiplayerGame)this.parent));
                        logginThread.start();
                    }
                } else if(this.textField.getText().length() < 3){
                    this.title.setText("Minimum of three characters required.");
                }else{
                    this.title.setText("Maximum of 16 characters only.");
                }
                break;
            case "Back":
                SceneState.setGeneralState(SceneState.General.MENU);
                break;
        }
    }

    /**
     * @return the logingIn
     */
    public boolean isLogingIn() {
        return logingIn;
    }

    /**
     * @param logingIn the logingIn to set
     */
    public void setLogingIn(boolean logingIn) {
        this.logingIn = logingIn;
    }

    /**
     * @return the title as a string
     */
    public String getTitle() {
        return title.getText();
    }

    /**
     * @param title String to set the title to the given String
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }
}