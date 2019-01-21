/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver.users;

import battleshipsserver.core.MessageIOHandler;
import battleshipsserver.core.Printer;
import battleshipsserver.core.ServerGlobals;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Louis
 */
public class UserList {
    private CopyOnWriteArrayList<User> unassinedUsers = new CopyOnWriteArrayList<User>();
    private CopyOnWriteArrayList<User> onlineUsers = new CopyOnWriteArrayList<User>();
    
    public UserList(){}
    
    public void addNewUser(User u){
        onlineUsers.add(u);
        Printer.println("User "+u.getUsername()+" has come online.");
        queueUser(u);
    }
    
    public void queueUser(User u){
        unassinedUsers.add(u);
        Printer.println(u.getUsername()+ " is queued for matchmaking.");
        MessageIOHandler.sendCommandMessage("in-queue", u);
    }
    
    public void disconnectUser(User u){
        if(isUserOnline(u)){
            onlineUsers.remove(u);
            removeUserFromQueue(u);
            u.killMessageListenerThread();
            MessageIOHandler.sendCommandMessage("usr-dco", u);
            if(!u.getSocket().isClosed()){
                try {
                    u.getSocket().close();
                } catch (IOException ex) {}
            }
            Printer.println("User "+u.getUsername()+" has gone offline.");
        }else{
            System.err.println("User is allready offline.");
        }
    }
    
    public void removeUserFromQueue(User u){
        if(isUserWaiting(u)){
            unassinedUsers.remove(u);
        }
        else{
            System.err.println("User is not in queue.");
        }
    }
    
    private boolean isUserOnline(User u){
        return onlineUsers.contains(u);
    }
    
    private boolean isUserWaiting(User u){
        return unassinedUsers.contains(u);
    }
    
    public boolean areUsersWaiting(){
        return unassinedUsers.size()>0;
    }
    
    public User getQueueAt(int i){
        return unassinedUsers.get(i);
    }
    
    public int getQueueLength(){
        return unassinedUsers.size();
    }
    
    public void printOnlineUsers(){
        for(User tmp : onlineUsers){
            Printer.println(tmp.getUsername());
        }
    }
    
    public int getNumberOfWaitingUsers(){
        return unassinedUsers.size();
    }
    
    public int getNumberOfOnlineUsers(){
        return onlineUsers.size();
    }
    
    public void disconnectAllUsers(){
        for(User u : onlineUsers){
            MessageIOHandler.sendCommandMessage("usr-dco", u);
            u.killMessageListenerThread();
            u = null;
        }
        unassinedUsers = new CopyOnWriteArrayList<User>();
        onlineUsers = new CopyOnWriteArrayList<User>();
    }
    
    public CopyOnWriteArrayList<User> getOnlineUsers(){
        return onlineUsers;
    }

    public void cleanup() {
        long currentTime = System.currentTimeMillis();
        for (User u : onlineUsers) {
            long timeSinceLastMessage = currentTime - u.getLastMessageTime();
            if (timeSinceLastMessage > ServerGlobals.userTimeoutLength) {
                if(!isUserWaiting(u)){
                    disconnectUser(u);
                }
            }
        }
    }
}
