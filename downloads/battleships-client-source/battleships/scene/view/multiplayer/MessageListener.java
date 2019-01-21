/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleships.scene.view.multiplayer;

import battleships.engine.events.MessageRecievedEvent;
import battleships.scene.view.multiplayer.MultiplayerGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Louis
 */
public class MessageListener implements Runnable{

    private Socket socket;
    private MultiplayerGame responder;
    
    public MessageListener(Socket s, MultiplayerGame g) {
        this.socket = s;
        this.responder = g;
    }

    @Override
    public void run() {
        while(!this.socket.isClosed() && this.socket.isBound()){
            String message = MessageIOHandler.getMessage(this.socket);
            if(message!=null){
                new Thread(new MessageRecievedEvent(responder, "messageRecieved", message)).start();
            }
        }
    }
}
