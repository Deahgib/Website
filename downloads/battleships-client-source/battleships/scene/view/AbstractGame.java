package battleships.scene.view;

import battleships.engine.ResolutionManager;
import battleships.engine.Window;
import battleships.scene.components.button.ButtonRegistry;
import battleships.scene.components.grid.AbstractGrid;
import battleships.scene.components.image.Image;
import battleships.scene.components.label.Label;
import battleships.scene.components.ship.AbstractShip;
import battleships.scene.view.player.AbstractPlayer;


/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractGame extends AbstractView {
    protected ButtonRegistry gameButtons;
    public static ResolutionManager dimentions;
    protected Label bannerLabel;
    protected Label shortMessageLabel;
    
    /**
     * This this the parent class to all games, multiplayer and singleplayer.
     * This class will initialise basic things like the dimentions of the view. 
     * The button registry which is where the button are held in memory and also 
     * updated, drawn, removed and added. 
     * There is also a label that is initialised. which is the main label that 
     * every game will have along the top of the screen to update the player on 
     * the game.
     * 
     * Every view has a background image on it and this is where the Game 
     * background image is initialised.
     */
    protected AbstractGame(){
        super(0, 0, Window.getWidth(), Window.getHeight());
        AbstractGame.dimentions = new ResolutionManager(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.gameButtons = new ButtonRegistry();
        
        this.bannerLabel = new Label(
                this.getX(), 
                this.getY(), 
                this.getWidth(), 
                this.dimentions.getTopMargin(), 
                "bannerLabel");
        this.bannerLabel.setText("Place your ships");
        this.bannerLabel.textAlignCentered();
        this.bannerLabel.setFontAutoResisable(false);
        
        double xSMLPos = this.getX() + this.dimentions.getGridCellHeight()/2 + this.dimentions.getLeftMargin() + this.dimentions.getGridCellHeight()*AbstractGrid.getGRID_SIZE();
        double ySMLPos = this.getY() + this.dimentions.getTopMargin();
        double widthSML = this.getWidth() - xSMLPos - this.dimentions.getGridCellHeight()/2;
        double heightSML = this.dimentions.getGridCellHeight() * 2;
        this.shortMessageLabel = new Label(
                xSMLPos, 
                ySMLPos, 
                widthSML, 
                heightSML, 
                "shortMessageLabel");
        this.shortMessageLabel.setVisible(false);
        this.shortMessageLabel.setText("");
        this.shortMessageLabel.textAlignCentered();
        this.shortMessageLabel.setFontAutoResisable(false);
        
        this.background = new Image(this.getX(), this.getY(), this.getWidth(), this.getHeight(), "game-back");
    }
    
    protected boolean isGameOver(AbstractPlayer p1, AbstractPlayer p2){
        if(this.hasPlayerLost(p1) || this.hasPlayerLost(p2)){
            return true;
        }
        return false;
    }
    protected boolean hasPlayerLost(AbstractPlayer p1){
        int p1ships = 5;
        for(AbstractShip ship : p1.getShips()){
            if(ship.isShipSunk()){
                p1ships--;
            }
        }
        if(p1ships <= 0){
            return true;
        }
        return false;
    }
}
