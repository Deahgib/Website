package battleships.scene.view.singleplayer;

import battleships.engine.*;
import battleships.engine.animation.MissileAnimation;
import battleships.engine.animation.PauseAnimation;
import battleships.engine.audio.AudioManager;
import battleships.engine.events.IEvent;
import battleships.engine.events.TimeEvent;
import battleships.engine.events.TimeEventList;
import battleships.scene.SceneState;
import battleships.scene.components.button.Button;
import battleships.scene.components.grid.AbstractGrid;
import battleships.scene.components.ship.AbstractShip;
import battleships.scene.view.AbstractGame;
import battleships.scene.view.player.ComputerAIPlayer;
import battleships.scene.view.player.UserPlayer;
import battleships.scene.view.popupview.GameMenu;
import battleships.scene.view.popupview.GameOver;

/**
 *
 * @author Louis Bennette
 */
public class SingleplayerGame extends AbstractGame{
    private UserPlayer userPlayer;
    private ComputerAIPlayer enemyPlayer;
    
    public SingleplayerGame(){
        super();
        
        this.userPlayer = new UserPlayer();
        this.enemyPlayer = new ComputerAIPlayer();
        
        // Debug
        this.enemyPlayer.initAIShips();
        
        Button backButton = new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight()*2, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight()*2, dimentions.getGridCellHeight(), "Back", this);
        Button readyButton = new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight()*5, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight()*2, dimentions.getGridCellHeight(), "Ready", this);
        readyButton.getButtonLabel().setFontSize(28f);
        
        this.gameButtons.add(backButton);
        this.gameButtons.add(readyButton);
        
        GameLogger.print("Singleplayer game object successfully created.");
        
        AudioManager.play(AudioManager.GAME_MUSIC);
        //AudioManager.play(AudioManager.AMBIENCE);
        //TimeEventList.add(new TimeEvent(this, 30 * Time.SECOND, "ambienceLooper"));
    }

    /**
     * This is the main update call used in singleplayer. This update loop will 
     * call subsequent update loops belonging to components of singleplayer.
     * This method is called once a frame, it will handle the update of 
     * component locations, user interactions with components and update called 
     * to child views if active as well as animations.
     * It knows what calls to make based on the state the game is currently in.
     */
    @Override
    public void update() {
        if (this.shouldUpdate()) {
            this.gameButtons.handleClickEvent(); // Everyframe handle button clicks
            switch (SceneState.getPlayerState()) { 
                case SHIP_PLACEMENT: // Allow the player in this state to move ships around and place them.
                    this.userPlayer.updateShipPlacement();
                    if(this.userPlayer.isMovingShip()){
                        this.bannerLabel.setText("'Right Click' or 'R' to rotate");
                    }
                    else{
                        this.bannerLabel.setText("Place your ships");
                    }
                    break;

                case WAIT_FOR_OPONENT:
                    // In this state the user's ships are locked
                    // The AI's ships are allways ready, so we start the game.
                    if (this.userPlayer.isReady() && this.enemyPlayer.isReady()) {
                        this.shortMessageLabel.setVisible(true);
                        // Determine who goes first.
                        java.util.Random n = new java.util.Random();
                        int i = n.nextInt(2);
                        if (i == 0) {
                            SceneState.setPlayerState(SceneState.Player.USER_TURN);
                            this.bannerLabel.setText("Your turn");
                        } else {
                            SceneState.setPlayerState(SceneState.Player.OPONENT_TURN);
                            this.bannerLabel.setText("Opponent's turn");
                        }
                        this.gameButtons.removeButtonWithName("Notready");
                        this.gameButtons.add(new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight() * 4, dimentions.getBottomMargin() - dimentions.getGridCellHeight() * 3, dimentions.getGridCellHeight() * 4, dimentions.getGridCellHeight() * 3, "Fire", this));
                    } else {
                        this.enemyPlayer.initAIShips();
                    }
                    break;

                case USER_TURN:
                    // This gives the user control where they would like to
                    // fire at their opponent. 
                    this.userPlayer.upadteSelectedTarget();
                    if(Input.getKeyUp(Input.KEY_SPACE)){
                        TimeEventList.add(new TimeEvent(this, "Fire"));
                    }
                    break;

                case OPONENT_TURN:
                    // This is when the AI kicks in and decides what it's next 
                    // target is.
                    this.enemyPlayer.findNextCellToAttack();
                    if (this.enemyPlayer.getTargetGrid().getSelected() != null) {

                        this.setAnimation(new MissileAnimation(this, 
                                SceneState.Player.USER_TURN, 
                                SceneState.getPlayerState(), 
                                this.enemyPlayer.getTargetGrid().getSelected(), 
                                true, (Time.SECOND * 1)));
                        SceneState.setPlayerState(SceneState.Player.ANIMATING);
                        this.getAnimation().start();
                    }
                    break;

                case GAME_OVER:
                    //System.out.println("Game Over");
                    break;
            }

            // ========================================================
            //                   GAME OVER DETECTED
            // ========================================================
            if (this.isGameOver(this.userPlayer, this.enemyPlayer)) {
                TimeEventList.add(new TimeEvent(this, "gameOver"));
            }

            // Debug Game Over popup test
            if (GameGlobals.DEBUG_MODE) {
                if (Input.getKeyUp(Input.KEY_P)) {
                    if (this.getActiveSubview() == null) {
                        GameOver child = new GameOver(this, true, "You cheated!");
                        this.addChildView(child);
                        this.setActiveSubview(child);
                    }
                }
            }
            
            // Open the Menu when "Esc" is pressed.
            if (Input.getKeyUp(Input.KEY_ESC)) {
                if(this.getActiveSubview() == null){
                    GameMenu child = new GameMenu(this, true);
                    this.addChildView(child);
                    this.setActiveSubview(child);
                }
            }
        }
        
        if(SceneState.getPlayerState() == SceneState.Player.ANIMATING) {
            if (this.getAnimation() != null) { // If animation exisists.
                // If the animation is not started we start it, or if it has 
                // been started, we update it.
                if (this.getAnimation().isAnimationStarted()) {
                    // When the animation set's itself to ended we detect it 
                    // here and end it properly.
                    if (this.getAnimation().isAnimationEnded()) {
                        this.endAnimation();
                    } else {
                        this.getAnimation().updateAnimation();
                    }
                } else {
                    this.getAnimation().start();
                }
            }
        }
        
        this.updateSubviews();
    }
    
    @Override
    public void draw(){
        this.background.draw();
        
        SceneState.Player state = SceneState.getPlayerState();
        if (this.getAnimation() != null) {
            if (this.getAnimation().isAnimationStarted()) {
                state = this.getAnimation().getPreviousState();
            }
        }
        
        switch (state) {
            case GAME_OVER:
                break;
            case WAIT_FOR_OPONENT:
            case SHIP_PLACEMENT:
                this.userPlayer.getShipHolder().draw();
            case OPONENT_TURN: // On opponent turn show the user's fleet.
                this.userPlayer.getFleetGrid().draw();
                for (AbstractShip ship : this.userPlayer.getShips()) {
                    if(ship.isShipBeingMoved() && this.userPlayer.getFleetGrid().hasMouseHover()){
                        Point p = ship.getShipHoverPositionForGrid(this.userPlayer.getFleetGrid());
                        if(this.userPlayer.getFleetGrid().isPositionValidFor(ship, (int)p.getX(), (int)p.getY())){
                            this.userPlayer.getFleetGrid().drawShipHoverBox(ship, (int)p.getX(), (int)p.getY());
                        }
                    }
                    ship.draw();
                }
                this.userPlayer.getFleetGrid().drawDecals();
                break;

            case USER_TURN: // On users turn show the user's attempts/target grid.
                this.userPlayer.getTargetGrid().draw();
                break;
        };
        
        if (this.getAnimation() != null) {
            if (this.getAnimation().isAnimationStarted()) {
                this.getAnimation().drawAnimation();
            }
        }
        
        this.bannerLabel.draw();
        this.shortMessageLabel.draw();
        this.gameButtons.draw();
        
        this.drawSubviews();
    }

    /**
     * This is the event handling method, If any singleplayer component launches
     * an event it is handled here.
     * @param event 
     */
    @Override
    public void handleEvent(IEvent event) {
        GameLogger.print("Singleplayer event \"" + event.getName() + "\" was dispached.");
        
        switch (event.getName()) {
            case "Back":
                SceneState.setGeneralState(SceneState.General.MENU);
                break;
            case "Ready":
                if (this.userPlayer.getFleetGrid().isGridFilled()) {
                    SceneState.setPlayerState(SceneState.Player.WAIT_FOR_OPONENT);
                    this.gameButtons.removeButtonWithName("Ready");
                    this.gameButtons.removeButtonWithName("Back");
                    this.gameButtons.add(new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight() * 2, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight() * 2, dimentions.getGridCellHeight(), "Notready", this));
                    this.userPlayer.setReady(true);
                    this.bannerLabel.setText("Waiting for opponent");
                }
                break;
            case "Notready":
                this.gameButtons.removeButtonWithName("Notready");
                this.gameButtons.add(new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight() * 2, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight() * 2, dimentions.getGridCellHeight(), "Back", this));
                this.gameButtons.add(new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight() * 5, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight() * 2, dimentions.getGridCellHeight(), "Ready", this));
                SceneState.setPlayerState(SceneState.Player.SHIP_PLACEMENT);
                this.userPlayer.setReady(false);
                break;
            case "Fire":
                if (this.userPlayer.getTargetGrid().getSelected() != null) {
                    int x = (int) this.userPlayer.getTargetGrid().getSelected().getX();
                    int y = (int) this.userPlayer.getTargetGrid().getSelected().getY();

                    if (this.enemyPlayer.getFleetGrid().isHit(x, y)) {
                        GameLogger.print("Player HIT AI at x=" + x + ", y=" + y);
                        this.shortMessageLabel.setText("Hit!");
                        this.userPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.HIT);
                        AudioManager.play(AudioManager.P_HIT);
                        
                        if (this.enemyPlayer.getFleetGrid().isSink(x, y)) {
                            this.shortMessageLabel.setText("You sunk my " + this.enemyPlayer.getFleetGrid().getShipAt(x, y).getName());
                            GameLogger.print("Player sunk AI's " + this.enemyPlayer.getFleetGrid().getShipAt(x, y).getName());
                            AudioManager.stop(AudioManager.P_HIT);
                            AudioManager.play(AudioManager.P_SINK);
                        }
                    } else {
                        this.shortMessageLabel.setText("Miss");
                        this.userPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.MISS);
                        AudioManager.play(AudioManager.P_MISS);
                    }

                    this.setAnimation(new PauseAnimation(this, 
                            SceneState.Player.OPONENT_TURN, 
                            SceneState.getPlayerState(), (Time.SECOND * 2)));
                    SceneState.setPlayerState(SceneState.Player.ANIMATING);
                    this.getAnimation().start();
                    
                    this.userPlayer.getTargetGrid().resetSelected();
                }
                break;
            case "missileAnim":
                int x = (int) this.enemyPlayer.getTargetGrid().getSelected().getX();
                int y = (int) this.enemyPlayer.getTargetGrid().getSelected().getY();
                if (this.userPlayer.getFleetGrid().isHit(x, y)) {
                    GameLogger.print("AI HIT player at x=" + x + ", y=" + y);
                    
                    // Tell the AI that it hit!
                    this.enemyPlayer.hitAShip(this.enemyPlayer.getTargetGrid().getSelected());
                    
                    // Update the view's model.
                    this.enemyPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.HIT);
                    this.userPlayer.getFleetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.HIT);
                    AudioManager.play(AudioManager.E_HIT);
                    if(this.userPlayer.getFleetGrid().isSink(x, y)){
                        this.enemyPlayer.setSunkAShip(this.userPlayer.getFleetGrid().getShipAt(x, y).getSize());
                        this.shortMessageLabel.setText("Ai sunk your " + this.userPlayer.getFleetGrid().getShipAt(x, y).getName());
                        AudioManager.stop(AudioManager.E_HIT);
                        AudioManager.play(AudioManager.E_SINK);
                        GameLogger.print("AI sunk player's " + this.userPlayer.getFleetGrid().getShipAt(x, y).getName());
                    }
                } else {
                    this.enemyPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.MISS);
                    this.userPlayer.getFleetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.MISS);
                    AudioManager.play(AudioManager.E_MISS);
                }
                this.enemyPlayer.getTargetGrid().resetSelected();
                break;
            case "gameOver":
                SceneState.setPlayerState(SceneState.Player.GAME_OVER);
                this.shortMessageLabel.setVisible(true);
                if (this.hasPlayerLost(this.userPlayer)) {
                    this.shortMessageLabel.setText("");
                    if (this.getActiveSubview() == null) {
                        GameLogger.print("Adding view");
                        GameOver child = new GameOver(this, true, "You lose.");
                        this.addChildView(child);
                        this.setActiveSubview(child);
                        AudioManager.stop(AudioManager.E_SINK);
                        AudioManager.play(AudioManager.GAME_OVER_LOOSE);
                    }
                } else if (this.hasPlayerLost(this.enemyPlayer)) {
                    if (this.getActiveSubview() == null) {
                        this.shortMessageLabel.setText("");
                        GameOver child = new GameOver(this, true, "You win!");
                        this.addChildView(child);
                        this.setActiveSubview(child);
                        AudioManager.stop(AudioManager.P_SINK);
                        AudioManager.play(AudioManager.GAME_OVER_WIN);
                    }
                } else {
                    // This should never happen.
                    this.shortMessageLabel.setText("RulesDrawError");
                }
                break;
            case "depause":
                this.getAnimation().end();
                if(this.getAnimation().getResumeState()==SceneState.Player.OPONENT_TURN){
                    this.bannerLabel.setText("Opponent's turn");
                    this.shortMessageLabel.setText("");
                }
                else if(this.getAnimation().getResumeState()==SceneState.Player.USER_TURN){
                    this.bannerLabel.setText("Your turn");
                    this.shortMessageLabel.setVisible(true);
                }
                
                break;
                
            case "ambienceLooper":
                AudioManager.play(AudioManager.AMBIENCE);
                TimeEventList.add(new TimeEvent(this, 30 * Time.SECOND, "ambienceLooper"));
                break;
        }
    }
}
