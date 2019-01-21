
package battleships.scene.view.multiplayer;

import battleships.engine.*;
import battleships.engine.audio.AudioManager;
import battleships.engine.events.IEvent;
import battleships.engine.events.MessageRecievedEvent;
import battleships.engine.events.TimeEvent;
import battleships.engine.events.TimeEventList;
import battleships.scene.SceneState;
import battleships.scene.components.button.Button;
import battleships.scene.components.grid.AbstractGrid;
import battleships.scene.components.ship.AbstractShip;
import battleships.scene.view.AbstractGame;
import battleships.scene.view.player.UserPlayer;
import battleships.scene.view.popupview.GameMenu;
import battleships.scene.view.popupview.MessagePopup;
import battleships.scene.view.popupview.UsernamePopup;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deahgib
 */
public class MultiplayerGame extends AbstractGame{
    private MessagePopup messagePopup;
    
    private Socket socket;
    private MessageListener messageListener; 
    private Thread messageListenerThread;
    private UserPlayer userPlayer;
    private String opponentName;
    private boolean madeAShot;
    
    public MultiplayerGame(){
        super();
        
        this.userPlayer = new UserPlayer();
        
        this.bannerLabel.setText("Multiplayer");
        
        Button backButton = new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight()*2, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight()*2, dimentions.getGridCellHeight(), "Back", this);
        Button readyButton = new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight()*5, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight()*2, dimentions.getGridCellHeight(), "Ready", this);
        readyButton.getButtonLabel().setFontSize(28f);
        
        this.gameButtons.add(backButton);
        this.gameButtons.add(readyButton);
        
        Button notreadyButton = new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight() * 2, dimentions.getBottomMargin() - dimentions.getGridCellHeight(), dimentions.getGridCellHeight() * 2, dimentions.getGridCellHeight(), "Notready", this);
        notreadyButton.setVisible(false);
        notreadyButton.setText("Not Ready");
        this.gameButtons.add(notreadyButton);
        
        Button fireButton = new Button(dimentions.getRightMargin() - dimentions.getGridCellHeight() * 4, dimentions.getBottomMargin() - dimentions.getGridCellHeight() * 3, dimentions.getGridCellHeight() * 4, dimentions.getGridCellHeight() * 3, "Fire", this);
        fireButton.setVisible(false);
        this.gameButtons.add(fireButton);
                    
        
        GameLogger.print("Singleplayer game object successfully created.");
        
        AudioManager.play(AudioManager.GAME_MUSIC);
        //AudioManager.play(AudioManager.AMBIENCE);
        //TimeEventList.add(new TimeEvent(this, 30 * Time.SECOND, "ambienceLooper"));
        UsernamePopup child = new UsernamePopup(this, true);
        this.addChildView(child);
        this.setActiveSubview(child);
        
         messagePopup = new MessagePopup(this, true, "", "");
         this.addChildView(this.messagePopup);
         
         this.opponentName = "Opponent";
         this.madeAShot = false;
    }

    @Override
    public void update() {
        if (this.shouldUpdate()) {
            this.gameButtons.handleClickEvent();
            switch (SceneState.getPlayerState()) {
                case QUEUED:
                    break;
                case SHIP_PLACEMENT:
                    this.userPlayer.updateShipPlacement();
                    if(this.userPlayer.isMovingShip()){
                        this.bannerLabel.setText("'Right Click' or 'R' to rotate");
                    }
                    else{
                        this.bannerLabel.setText("Place your ships");
                    }
                    break;

                case WAIT_FOR_OPONENT:
                    break;

                case USER_TURN:
                    //System.out.println("My turn");
                    if (!this.madeAShot) {
                        this.userPlayer.upadteSelectedTarget();
                        if (Input.getKeyUp(Input.KEY_SPACE)) {
                            TimeEventList.add(new TimeEvent(this, "Fire"));
                        }
                    }
                    break;

                case OPONENT_TURN:
                    
                    break;

                case GAME_OVER:
                    
                    break;
            }
            
            if (Input.getKeyUp(Input.KEY_ESC)) {
                if(this.getActiveSubview() == null){
                    GameMenu child = new GameMenu(this, false);
                    this.addChildView(child);
                    this.setActiveSubview(child);
                }
            }
        }
        
        if(SceneState.getPlayerState() == SceneState.Player.ANIMATING) {
            if (this.getAnimation() != null) {
                if (this.getAnimation().isAnimationStarted()) {
                    if (this.getAnimation().isAnimationEnded()) {
                        this.endAnimation();
                        //System.out.println("Clear animation");
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
            case QUEUED:
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
        }
        
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

    @Override
    public void handleEvent(IEvent event) {
        GameLogger.print("Singleplayer event \"" + event.getName() + "\" was dispached.");
        switch (event.getName()) {
            case "Back":
                SceneState.setGeneralState(SceneState.General.MENU);
                this.disconnectFromServer();
                break;
            case "Ready":
                if (this.userPlayer.getFleetGrid().isGridFilled()) {
                    MessageIOHandler.sendServerCommand("user-ready", this.socket);
                }
                break;
            case "Notready":
                if(this.userPlayer.isReady()){
                    MessageIOHandler.sendServerCommand("user-not-ready", this.socket);
                    this.userPlayer.setReady(false);
                }
                break;
            case "Fire":
                if (SceneState.getPlayerState() == SceneState.Player.USER_TURN && !this.madeAShot) {
                    if (this.userPlayer.getTargetGrid().getSelected() != null) {
                        int selx = (int) this.userPlayer.getTargetGrid().getSelected().getX();
                        int sely = (int) this.userPlayer.getTargetGrid().getSelected().getY();
                        MessageIOHandler.sendPlayerCommand(("try=" + selx + "," + sely), this.socket);
                        this.madeAShot = true;
                    }
                }
                break;
            case "opponentToUser":
                break;     
            case "depause":
                this.getAnimation().end();
                break;
            case "messageRecieved":
                MessageRecievedEvent mre = (MessageRecievedEvent)event;
                this.handleMessageRecieved(mre.getMessage());
                break;
        }
    }
    
    public void setSocket(Socket s){
        this.socket = s;
    }
    
    public Socket getSocket(){
        return this.socket;
    }
    
    public void setMessageListener(MessageListener ml, Thread mlThread){
        this.messageListener = ml;
        this.messageListenerThread = mlThread;
    }

    public void disconnectFromServer() {
        MessageIOHandler.sendServerCommand("dco", this.socket);
        try {
            this.socket.close();
        } catch (IOException ex) {
        }
    }

    private void handleMessageRecieved(String message){
        int type = Parser.getMessageType(message);
        message = Parser.getMessage(message);

        if (type == Parser.PLAYERtoPLAYER || type == Parser.SERVERtoPLAYER) {
            if (Parser.doesMessageHaveVariable(message)) {
                String variable = Parser.getMessageVariable(message);
                message = Parser.truncateVariable(message);

                switch (message) {
                    case "op-name":
                        this.opponentName = variable;
                        this.bannerLabel.setText("Opponent found: " + this.opponentName);
                        break;
                    case "state-turn":
                        if (variable.equals("0")) {
                            SceneState.setPlayerState(SceneState.Player.OPONENT_TURN);
                            this.bannerLabel.setText(this.opponentName+"'s turn");
                        } else {
                            SceneState.setPlayerState(SceneState.Player.USER_TURN);
                            this.bannerLabel.setText("Your turn");
                        }
                        this.madeAShot = false;
                        this.shortMessageLabel.setVisible(true);
                        this.shortMessageLabel.setText("");
                        this.gameButtons.removeButtonWithName("Notready");
                        this.gameButtons.removeButtonWithName("Ready");
                        this.gameButtons.removeButtonWithName("Back");
                        this.gameButtons.getButtonWithName("Fire").setVisible(true);
                        break;
                    case "try":
                        if (SceneState.getPlayerState() == SceneState.Player.OPONENT_TURN) {
                            String[] s = variable.split(",");
                            int x = Integer.parseInt(s[0]);
                            int y = Integer.parseInt(s[1]);

                            if (this.userPlayer.getFleetGrid().isHit(x, y)) {
                                this.userPlayer.getFleetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.HIT);
                                if (this.userPlayer.getFleetGrid().isSink(x, y)) {
                                    GameLogger.print("Online opponent sunk player's " + this.userPlayer.getFleetGrid().getShipAt(x, y).getName());
                                    this.shortMessageLabel.setText(this.opponentName+" sunk your " + this.userPlayer.getFleetGrid().getShipAt(x, y).getName());
                                    AudioManager.stop(AudioManager.E_HIT);
                                    AudioManager.play(AudioManager.E_SINK);
                                    if (!this.hasPlayerLost(userPlayer)) {
                                        MessageIOHandler.sendPlayerCommand(("sink=" + this.userPlayer.getFleetGrid().getShipAt(x, y).getName()), this.socket);
                                    } else {
                                        MessageIOHandler.sendServerCommand("user-lost", this.socket);
                                    }
                                } else {
                                    GameLogger.print("Online Opponent HIT player at x=" + x + ", y=" + y);
                                    // Update the view's model.
                                    AudioManager.play(AudioManager.E_HIT);
                                    MessageIOHandler.sendPlayerCommand("hit", this.socket);
                                }
                            } else {
                                MessageIOHandler.sendPlayerCommand("miss", this.socket);
                                this.userPlayer.getFleetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.MISS);
                                AudioManager.play(AudioManager.E_MISS);
                            }
                        }
                        break;
                    case "sink":
                        if (SceneState.getPlayerState() == SceneState.Player.USER_TURN && this.madeAShot) {
                            if (this.userPlayer.getTargetGrid().getSelected() != null) {
                                int x = (int) this.userPlayer.getTargetGrid().getSelected().getX();
                                int y = (int) this.userPlayer.getTargetGrid().getSelected().getY();
                                this.shortMessageLabel.setText("You sunk their " + variable);
                                this.userPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.HIT);
                                AudioManager.play(AudioManager.P_SINK);
                                this.userPlayer.getTargetGrid().resetSelected();
                                MessageIOHandler.sendServerCommand("next-turn", this.socket);
                            }
                        }
                        break;
                    case "game-over":
                        this.shortMessageLabel.setText("");
                        GameLogger.print("Adding view");
                        this.messagePopup.setTitle("GAME OVER");
                        if (variable.equals("0")) {
                            if (this.getActiveSubview() == null) {
                                this.messagePopup.setMessage("You lose.");
                                AudioManager.stop(AudioManager.E_SINK);
                                AudioManager.play(AudioManager.GAME_OVER_LOOSE);
                            }
                        } else {
                            if (this.getActiveSubview() == null) {
                                this.messagePopup.setMessage("You defeated " + this.opponentName + "!");
                                AudioManager.stop(AudioManager.P_SINK);
                                AudioManager.play(AudioManager.GAME_OVER_WIN);
                            }
                        }
                        this.setActiveSubview(this.messagePopup);
                        break;
                }
            } else {
                switch (message) {
                    case "in-queue":
                        this.bannerLabel.setText("Queued for a game.");
                        SceneState.setPlayerState(SceneState.Player.QUEUED);
                        break;
                    case "hit":
                        if (SceneState.getPlayerState() == SceneState.Player.USER_TURN && this.madeAShot) {
                            if (this.userPlayer.getTargetGrid().getSelected() != null) {
                                int x = (int) this.userPlayer.getTargetGrid().getSelected().getX();
                                int y = (int) this.userPlayer.getTargetGrid().getSelected().getY();
                                GameLogger.print("Player HIT AI at x=" + x + ", y=" + y);
                                this.shortMessageLabel.setText("Hit!");
                                this.userPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.HIT);
                                AudioManager.play(AudioManager.P_HIT);
                                this.userPlayer.getTargetGrid().resetSelected();
                                MessageIOHandler.sendServerCommand("next-turn", this.socket);
                            }
                        }
                        break;
                    case "miss":
                        if (SceneState.getPlayerState() == SceneState.Player.USER_TURN && this.madeAShot) {
                            if (this.userPlayer.getTargetGrid().getSelected() != null) {
                                int x = (int) this.userPlayer.getTargetGrid().getSelected().getX();
                                int y = (int) this.userPlayer.getTargetGrid().getSelected().getY();
                                this.shortMessageLabel.setText("Miss");
                                this.userPlayer.getTargetGrid().registerAttempt(x, y, AbstractGrid.HitMarker.MISS);
                                AudioManager.play(AudioManager.P_MISS);
                                this.userPlayer.getTargetGrid().resetSelected();
                                MessageIOHandler.sendServerCommand("next-turn", this.socket);
                            }
                        }
                        break;
                    case "state-ship-placement":
                        if (SceneState.getPlayerState() != SceneState.Player.SHIP_PLACEMENT) {
                            this.gameButtons.getButtonWithName("Notready").setVisible(false);
                            this.gameButtons.getButtonWithName("Ready").setVisible(true);
                            this.gameButtons.getButtonWithName("Back").setVisible(true);
                            SceneState.setPlayerState(SceneState.Player.SHIP_PLACEMENT);
                            this.userPlayer.setReady(false);
                        }
                        break;
                    case "state-wait-for-opponent":
                        SceneState.setPlayerState(SceneState.Player.WAIT_FOR_OPONENT);
                        this.gameButtons.getButtonWithName("Notready").setVisible(true);
                        this.gameButtons.getButtonWithName("Ready").setVisible(false);
                        this.gameButtons.getButtonWithName("Back").setVisible(false);
                        this.userPlayer.setReady(true);
                        this.bannerLabel.setText("Waiting for opponent");
                        break;
                    case "usr-dco":
                        messageListenerThread.interrupt();
                        messageListenerThread = null;
                        SceneState.setGeneralState(SceneState.General.MENU);
                        this.disconnectFromServer();
                        break;
                        
                    case "op-dco":
                        this.messagePopup.setTitle("Opponent Disconnected");
                        this.messagePopup.setMessage("Your opponent has disconnected from the game.");
                        this.setActiveSubview(this.messagePopup);
                        break;
                        
                    case "fatal":
                        this.messagePopup.setTitle("Fatal Server Error");
                        this.messagePopup.setMessage("There has been an error on the server, please contact the server admin.");
                        this.setActiveSubview(this.messagePopup);
                        break;
                }
            }
        } else if (type == Parser.CHAT) {
            // Do nothing chat not implemented yet.
        } else {
            GameLogger.severe("Suspected tampering message " + type + message + " recieved from server.");
        }
    }

}
