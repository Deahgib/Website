/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver.game;

import battleshipsserver.core.MessageIOHandler;
import battleshipsserver.core.Parser;
import battleshipsserver.core.Printer;
import battleshipsserver.core.ServerGlobals;
import battleshipsserver.users.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Louis
 */
public class GameInstance implements Runnable, ActionListener{
    private boolean isKillThisThread;

    private boolean gameStarted;
    
    private Timer turnPause;
    
    private User[] users;
    private boolean relayMessages;
    
    
    public GameInstance(User[] u){
        this.users = u;
        gameStarted = false;
        turnPause = new Timer(2500, this);
        this.relayMessages = true;
        this.isKillThisThread = false;
    }
    
    @Override
    public void run() {
        System.out.println("Instance thread ("+Thread.currentThread().getName()+") has started.");
        
        MessageIOHandler.sendCommandMessage("op-name="+users[1].getUsername(), users[0]);
        MessageIOHandler.sendCommandMessage("op-name="+users[0].getUsername(), users[1]);
        
        this.longPause();
        
        MessageIOHandler.sendCommandMessage("state-ship-placement", users[0]);
        MessageIOHandler.sendCommandMessage("state-ship-placement", users[1]);
        
        
        while(!ServerGlobals.isShutdownServer() && !ServerGlobals.isKillAllThreads() && !ServerGlobals.isKillAllGameInstances() && !this.isKillThisThread){
            this.smallPause();
            
            this.pollUser(users[0]);
            
            this.handleUserMessage(users[0], users[1]);
            this.handleUserMessage(users[1], users[0]);
            
            if(!gameStarted){
                if(users[0].hasPlacedShips() && users[1].hasPlacedShips()){
                    this.longPause();
                    
                    users[0].setUserTurn(true);
                    users[1].setUserTurn(false);
                    MessageIOHandler.sendCommandMessage("state-turn=1",users[0]);
                    MessageIOHandler.sendCommandMessage("state-turn=0",users[1]);
                    gameStarted = true;
                }
            }
            
        }
        Printer.println("Instance thread ("+Thread.currentThread().getName()+") has ended.");
    }
    
    /**
     * 
     * @param recieved
     * @param opp 
     */
    private void handleUserMessage(User sender, User opponent) {
        if (sender.getMessageListener().hasNewMessage()) {
            String m = sender.getMessageListener().getNewMessage();
            int type = Parser.getMessageType(m);
            String tail = Parser.getMessage(m);
            if (type == Parser.PLAYERtoSERVER) {
                handleServerCommand(tail, sender, opponent);
            } else if (type == Parser.PLAYERtoPLAYER) {
                if(this.relayMessages){
                    MessageIOHandler.relayCommandMessage(tail, opponent);
                }
            } else {
                broadcast(tail, sender);
            }
        }
    }
    
    private void handleServerCommand(String message, User sender, User opponent){
        if(Parser.doesMessageHaveVariable(message)){
            String variable = Parser.getMessageVariable(message);
            message = Parser.truncateVariable(message);
            
            switch(message){ // VARIABLE BASED COMMANDS
                case "game-over":
                    if(variable.equals("1")){
                        
                    }
                    break;
            }
        }else{
            switch(message){ // STANDARD COMMANDS
                case "user-ready":
                    if (!gameStarted) {
                        sender.setPlacedShips(true);
                        MessageIOHandler.sendCommandMessage("state-wait-for-opponent", sender);
                    }
                    break;
                case "user-not-ready":
                    if (!gameStarted) {
                        sender.setPlacedShips(false);
                        MessageIOHandler.sendCommandMessage("state-ship-placement", sender);
                    }
                    break;
                case "next-turn":
                    this.relayMessages = false;
                    this.turnPause.start();
                    break;
                case "user-lost":
                    this.smallPause();
                    MessageIOHandler.sendCommandMessage("game-over=0", sender);
                    MessageIOHandler.sendCommandMessage("game-over=1", opponent);
                    break;
                case "dco":
                    this.smallPause();
                    MessageIOHandler.sendCommandMessage("op-dco", opponent);
                    this.isKillThisThread = true;
                    //MessageIOHandler.sendCommandMessage("usr-dco", sender);
                    break;
                case "req":
                    
                    break;
                default:
                    Printer.println("UNKNOWN COMMAND RECIEVED: " + Parser.PLAYERtoSERVER + message);
                    break;
            }
        }
    }
    
    private void switchTurn() {
        if (gameStarted) {
            if (users[0].isUserTurn()) {
                users[0].setUserTurn(false);
                users[1].setUserTurn(true);
                MessageIOHandler.sendCommandMessage("state-turn=0", users[0]);
                MessageIOHandler.sendCommandMessage("state-turn=1", users[1]);
            } else {
                users[0].setUserTurn(true);
                users[1].setUserTurn(false);
                MessageIOHandler.sendCommandMessage("state-turn=1", users[0]);
                MessageIOHandler.sendCommandMessage("state-turn=0", users[1]);
            }
        }
    }

    private void broadcast(String message, User origin){
        MessageIOHandler.sendStringMessage(origin.getUsername()+": "+message, users[0]);
        MessageIOHandler.sendStringMessage(origin.getUsername()+": "+message, users[1]);
    }
    
    private void pollUser(User user) {
        if(!user.getSocket().isConnected()){
            
        }
        if(user.getSocket().isClosed()){
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.switchTurn();
        turnPause.stop();
        this.relayMessages = true;
    }

    private void smallPause() {
        try {
            Thread.sleep(50);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Couldnt sleep in instance thread (" + Thread.currentThread().getName() + ")");
        }
    }
    
    private void longPause(){
        for(int i = 0; i<40; i++){
            this.smallPause();
        }
    }
}
