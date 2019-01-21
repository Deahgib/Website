package battleships.scene.view.credits;


import battleships.engine.*;
import battleships.engine.audio.AudioManager;
import battleships.engine.audio.SoundFile;
import battleships.engine.events.IEvent;
import battleships.scene.components.button.ButtonRegistry;
import battleships.scene.components.image.Image;
import battleships.scene.components.label.Label;
import battleships.scene.view.AbstractView;

/**
 *
 * @author Louis Bennette
 */
public class Credits extends AbstractView{
    private ButtonRegistry menuButtons;
    private ResolutionManager dimentions;
    private Label titleLabel;
    private Label version;
    private Image decorationImage;
    
    private SoundFile mainMenuMusic;
    
    /*
     * 
     */
    public Credits(){
        super(0,0,Window.getWidth(), Window.getHeight());
        this.dimentions = new ResolutionManager(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.menuButtons = new ButtonRegistry();
        
        // Repackage this in the resolution singleton class.
        double bXLocation = this.getX() + this.getWidth()/100 * 15;
        double bWidth = this.getWidth()/100 * 20;
        double bHeight = this.getHeight()/100 * 10;
        double bVerticalPadding = bHeight;
        double yMargin = this.getY() + this.getHeight()/100 * 20;
        double bNumber = 0;
        
        // Make background
        this.background = new Image(this.getX(), this.getY(), this.getWidth() , this.getHeight(), "menu-back");
        
        
        AudioManager.play(AudioManager.CREDITS);
    }
    
    @Override
    public void update(){
        this.menuButtons.handleClickEvent();
        
        if(Input.getKeyUp(Input.KEY_ESC)){
            
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

    }
}