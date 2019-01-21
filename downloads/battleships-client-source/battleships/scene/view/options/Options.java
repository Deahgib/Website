package battleships.scene.view.options;

import battleships.engine.*;
import battleships.engine.events.IEvent;
import battleships.scene.SceneState;
import battleships.scene.components.button.Button;
import battleships.scene.components.button.ButtonRegistry;
import battleships.scene.components.button.togglebutton.ToggleButton;
import battleships.scene.components.image.Image;
import battleships.scene.components.label.Label;
import battleships.scene.components.textarea.TextField;
import battleships.scene.view.AbstractView;

/**
 *
 * @author Louis Bennette
 */
public class Options extends AbstractView{
    private ButtonRegistry menuButtons;
    private ResolutionManager dimentions;
    private Label titleLabel;
    private Label version;
    
    private Label musicLabel;
    private Label effectsLabel;
    private Label usernameLabel;
    private Label serverLabel;
    
    private TextField usernameTextField;
    private TextField serverTextField;
    /*
     * 
     */
    public Options(){
        super(0,0,Window.getWidth(), Window.getHeight());
        this.dimentions = new ResolutionManager(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.menuButtons = new ButtonRegistry();
        
        this.background = new Image(this.getX(), this.getY(), this.getWidth(), this.getHeight(), "menu-back");
        
        // Repackage this in the resolution singleton class.
        double bXLocation = this.getX() + this.getWidth()/100 * 15;
        double tfWidth = this.getWidth()/100 * 30;
        double bWidth = this.getWidth()/100 * 20;
        double bHeight = this.getHeight()/100 * 10;
        double bVerticalPadding = bHeight/2;
        double yMargin = this.getY() + this.getHeight()/100 * 20;
        double bNumber = 0;
        
        ToggleButton musicButton = new ToggleButton(this.getWidth() - (bXLocation + bWidth), (yMargin + bNumber * (bHeight + bVerticalPadding)), bWidth, bHeight, "musicToggle", this);
        if (GameGlobals.gameMusicOn) {
            musicButton.setText("Music: ON");
            musicButton.setToggleState(ToggleButton.State.ON);
        } else {
            musicButton.setText("Music: OFF");
            musicButton.setToggleState(ToggleButton.State.OFF);
        }
        this.menuButtons.add(musicButton);
        this.musicLabel = new Label(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth*2, bHeight, "musicLabel");
        this.musicLabel.setText("Music:");
        
        bNumber++;
        
        ToggleButton effectsButton = new ToggleButton(this.getWidth()-(bXLocation+bWidth),(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "effectsToggle", this);
        if (GameGlobals.gameEffectSoundsOn) {
            effectsButton.setText("Effects: ON");
            effectsButton.setToggleState(ToggleButton.State.ON);
        } else {
            effectsButton.setText("Effects: OFF");
            effectsButton.setToggleState(ToggleButton.State.OFF);
        }
        this.menuButtons.add(effectsButton);
        this.effectsLabel = new Label(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth*2, bHeight, "effectsLabel");
        this.effectsLabel.setText("Sound effects:");
        
        bNumber++;
        
        this.usernameTextField = new TextField(this.getWidth()-(bXLocation+tfWidth),(yMargin+bNumber*(bHeight+bVerticalPadding)), tfWidth, bHeight, "usernameTextField", this);
        if(GameGlobals.getOnlineUserName() != null){
            String username = GameGlobals.getOnlineUserName();
            if(username.length() >= 3 && username.length() <= 16){
                this.usernameTextField.setText(GameGlobals.getOnlineUserName());
            }
        }
        this.usernameLabel = new Label(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth*2, bHeight, "usernameLabel");
        this.usernameLabel.setText("Online Username:");
        
        bNumber++;
        
        this.serverTextField = new TextField(this.getWidth()-(bXLocation+tfWidth),(yMargin+bNumber*(bHeight+bVerticalPadding)), tfWidth, bHeight, "serverTextField", this);
        this.serverTextField.setText(GameGlobals.serverURL);
        this.serverLabel = new Label(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth*2, bHeight, "serverLabel");
        this.serverLabel.setText("Server Address:");
        
        bNumber++;
        
        Button quitButton = new Button((this.getWidth()/2)-(bWidth/2),(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "quitButton", this);
        quitButton.setText("Done");
        this.menuButtons.add(quitButton);
        
        // Make title Label
        this.titleLabel = new Label(this.getX(), this.getY(), this.getWidth(), yMargin, "title");
        this.titleLabel.setText("Options");
        this.titleLabel.textAlignCentered();
        this.titleLabel.makeTitle(true);
        this.titleLabel.setFontSize(50f);
        this.titleLabel.setFontAutoResisable(false);
        this.titleLabel.setTextColour(255, 255, 255, 255);
        
        this.version = new Label(this.getX(), this.getY() + this.getHeight() - this.dimentions.getGridCellHeight(), this.getWidth() - this.dimentions.getGridCellHeight(), this.dimentions.getGridCellHeight(), "version");
        this.version.setText("Version: " + GameGlobals.VERSION);
        this.version.textAlignRight();
    }
    
    @Override
    public void update(){
        this.menuButtons.handleClickEvent();
        this.usernameTextField.update();
        this.serverTextField.update();
    }
    
    @Override
    public void draw(){
        this.background.draw();
        this.titleLabel.draw();
        this.version.draw();
        this.menuButtons.draw();
        this.usernameTextField.draw();
        this.serverTextField.draw();
        this.musicLabel.draw();
        this.effectsLabel.draw();
        this.usernameLabel.draw();
        this.serverLabel.draw();
    }
    
    @Override
    public void handleEvent(IEvent event) {
        if(event.getName().equals("musicToggle")){
                ToggleButton tb = (ToggleButton)event;
                if(tb.getToggleState() == ToggleButton.State.OFF){
                    GameGlobals.gameMusicOn = false;
                    tb.setText("Music: OFF");
                }else if(tb.getToggleState() == ToggleButton.State.ON){
                    GameGlobals.gameMusicOn = true;
                    tb.setText("Music: ON");
                }
        }
        else if(event.getName().equals("effectsToggle")){
                ToggleButton tb = (ToggleButton)event;
                if(tb.getToggleState() == ToggleButton.State.OFF){
                    GameGlobals.gameEffectSoundsOn = false;
                    tb.setText("Effects: OFF");
                }else if(tb.getToggleState() == ToggleButton.State.ON){
                    GameGlobals.gameEffectSoundsOn = true;
                    tb.setText("Effects: ON");
                }
        }
        else if(event.getName().equals("quitButton")){
            GameGlobals.setOnlineUserName(this.usernameTextField.getText());
            GameGlobals.setServerAddress(this.serverTextField.getText());
            GameGlobals.setSoundSettings(GameGlobals.gameMusicOn, GameGlobals.gameEffectSoundsOn);
            SceneState.setGeneralState(SceneState.General.MENU);
        }
    }
}