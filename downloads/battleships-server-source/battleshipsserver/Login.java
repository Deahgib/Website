/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver;

import battleshipsserver.core.MessageIOHandler;
import battleshipsserver.core.Parser;
import battleshipsserver.users.OnlineUsers;
import battleshipsserver.users.User;
import java.net.Socket;

/**
 * @author Louis
 */
public class Login implements Runnable{
    private Socket socket;
    public Login(Socket s){
        this.socket = s;
    }
    
    @Override
    public void run() {
        MessageIOHandler.sendCommandMessage("get-username", new User("", this.socket));
        String username = MessageIOHandler.getMessage(this.socket);
        username = Parser.getMessageVariable(username);
        User newUser = new User(username, this.socket);
        newUser.startMessageListenerThread();
        OnlineUsers.getOnlineUsers().addNewUser(newUser);
    }
    
}
