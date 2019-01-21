/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.scene.view.multiplayer;

import battleships.engine.GameGlobals;
import battleships.engine.GameLogger;
import battleships.scene.view.popupview.UsernamePopup;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Deahgib
 */
public class Login implements Runnable{

    private UsernamePopup popupView;
    private MultiplayerGame game;
    private String username;
    
    public Login(String name, UsernamePopup unp, MultiplayerGame game){
        this.popupView = unp;
        this.game = game;
        this.username = name;
    }
    
    @Override
    public void run() {
        this.popupView.setLogingIn(true);
        Socket socket;
        try {
            this.popupView.setTitle("Connecting");
            socket = new Socket(GameGlobals.serverURL, GameGlobals.SERVER_PORT);
            if (socket.isConnected() && this.popupView.isLogingIn()) {
                this.popupView.setTitle("Connected!");
                MessageListener ml = new MessageListener(socket, this.game);
                if(!this.popupView.isLogingIn()){
                    return;
                }
                Thread mlThread = new Thread(ml);
                String loginConfirmed = MessageIOHandler.getMessage(socket);
                if(!loginConfirmed.equals("3get-username") || !this.popupView.isLogingIn()){
                    return;
                }
                this.game.setSocket(socket);
                this.game.setMessageListener(ml, mlThread);
                if(!this.popupView.isLogingIn()){
                    return;
                }
                GameGlobals.setOnlineUserName(this.username);
                MessageIOHandler.sendServerCommand("usr=" + this.username, socket);
                this.game.removeChildView(popupView);
                if(!this.popupView.isLogingIn()){
                    return;
                }
                if (this.popupView.isFullControl()) {
                    this.game.endAnimation();
                    mlThread.start();
                }
            }else{
                socket.close();
            }
        } catch (UnknownHostException ex) {
            GameLogger.severe(UsernamePopup.class.getName() + "UnknownHostException");
            this.popupView.setTitle("Failed to connect.");
        } catch (IOException ex) {
            GameLogger.severe(UsernamePopup.class.getName() + "IOException");
            this.popupView.setTitle("Failed to connect.");
        } catch (Exception ex) {
            GameLogger.severe(UsernamePopup.class.getName() + "Unknown Exception");
            this.popupView.setTitle("Failed to connect.");
        }
        this.popupView.setLogingIn(false);
    }

}
