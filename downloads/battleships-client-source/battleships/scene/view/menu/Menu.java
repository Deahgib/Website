package battleships.scene.view.menu;

import battleships.GameLoop;
import battleships.engine.*;
import battleships.engine.audio.AudioManager;
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
public class Menu extends AbstractView{
    private ButtonRegistry menuButtons;
    private ResolutionManager dimentions;
    private Label titleLabel;
    private Label version;
    private Image decorationImage;
    
    /*
     * 
     */
    public Menu(){
        super(0,0,Window.getWidth(), Window.getHeight());
        this.dimentions = new ResolutionManager(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.menuButtons = new ButtonRegistry();
        
        // Repackage this in the resolution singleton class.
        double bXLocation = this.getX() + this.getWidth()/100 * 15;
        double bWidth = this.getWidth()/100 * 20;
        double bHeight = this.getHeight()/100 * 10;
        double bVerticalPadding = bHeight/2;
        double yMargin = this.getY() + this.getHeight()/100 * 20;
        double bNumber = 0;
        
        // Make background
        this.background = new Image(this.getX(), this.getY(), this.getWidth() , this.getHeight(), "menu-back");
        
        // Make decor image
        this.decorationImage = new Image(this.getX()+this.getWidth()-this.getHeight(), this.getY(), this.getHeight() , this.getHeight(), "menu-image");
        
        // Make buttons
        Button singleplayerButton = new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "singleplayerButton", this);
        singleplayerButton.setText("Singleplayer");
        this.menuButtons.add(singleplayerButton);
        bNumber++;
        Button multiplayerButton = new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "multiplayerButton", this);
        multiplayerButton.setText("Multiplayer");
        this.menuButtons.add(multiplayerButton);
        bNumber++;
        Button optionsButton = new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "optionsButton", this);
        optionsButton.setText("Options");
        this.menuButtons.add(optionsButton);
        bNumber++;
        bNumber++;
        Button quitButton = new Button(bXLocation,(yMargin+bNumber*(bHeight+bVerticalPadding)), bWidth, bHeight, "quitButton", this);
        quitButton.setText("Quit");
        this.menuButtons.add(quitButton);
        
        // Make title Label
        this.titleLabel = new Label(this.getX(), this.getY(), this.getWidth(), yMargin, "title");
        this.titleLabel.setText("Battleships");
        this.titleLabel.textAlignCentered();
        this.titleLabel.makeTitle(true);
        this.titleLabel.setFontSize(50f);
        this.titleLabel.setFontAutoResisable(false);
        this.titleLabel.setTextColour(255, 255, 255, 255);
        
        this.version = new Label(this.getX(), this.getY() + this.getHeight() - this.dimentions.getGridCellHeight(), this.getWidth() - this.dimentions.getGridCellHeight(), this.dimentions.getGridCellHeight(), "version");
        this.version.setText("Version: " + GameGlobals.VERSION);
        this.version.textAlignRight();
        
        AudioManager.play(AudioManager.MENU_MUSIC);
    }
    
    @Override
    public void update(){
        this.menuButtons.handleClickEvent();
        
        if(Input.getKeyUp(Input.KEY_RSHIFT) && Input.getKey(Input.KEY_SPACE) && Input.getMouse(Input.MOUSE_RIGHT)){
            AudioManager.play(AudioManager.SECRET);
        }
    }
    
    @Override
    public void draw(){
        this.background.draw();
        this.decorationImage.draw();
        this.titleLabel.draw();
        this.version.draw();
        this.menuButtons.draw();
    }
    
    @Override
    public void handleEvent(IEvent event) {
        if(event.getName().equals("singleplayerButton")){
            TimeEventList.remove("musicLooper");
            AudioManager.stop(AudioManager.MENU_MUSIC);
            SceneState.setGeneralState(SceneState.General.SINGLEPLAYER);
        }
        else if(event.getName().equals("multiplayerButton")){
            TimeEventList.remove("musicLooper");
            AudioManager.stop(AudioManager.MENU_MUSIC);
            SceneState.setGeneralState(SceneState.General.MULTIPLAYER);
        }
        else if(event.getName().equals("optionsButton")){
            // Do nothing for now.
            AudioManager.stop(AudioManager.MENU_MUSIC);
            SceneState.setGeneralState(SceneState.General.OPTIONS);
        }
        else if(event.getName().equals("quitButton")){
            TimeEventList.remove("musicLooper");
            AudioManager.stop(AudioManager.MENU_MUSIC);
            
            SceneState.setGeneralState(SceneState.General.MULTIPLAYER);
            GameLoop.setCloseRequested(true);
        }
        else if(event.getName().equals("musicLooper")){
            TimeEventList.add(new TimeEvent(this, 3*Time.SECOND, "musicLooper"));
        }
    }
}