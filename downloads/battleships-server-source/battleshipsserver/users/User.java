package battleshipsserver.users;

import battleshipsserver.core.MessageListener;
import java.net.Socket;

/**
 *
 * @author Louis
 */
public class User {
    private String username;
    private Socket socket;
    private MessageListener messageListener;
    private Thread messageListenerThread;
    private long lastMessageTime;
    
    private boolean placedShips;
    private boolean userTurn;
    
    public User(String u, Socket s){
        this.username = u;
        this.socket = s;
        this.messageListener = new MessageListener(this);
        messageListenerThread = new Thread(this.getMessageListener());
        resetUserForNewGame();
        
        this.lastMessageTime = System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public MessageListener getMessageListener() {
        return messageListener;
    }
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
    
    public void startMessageListenerThread(){
        messageListenerThread.start();
    }
    public void killMessageListenerThread(){
        messageListenerThread = null;
    }

    public boolean hasPlacedShips() {
        return placedShips;
    }
    public void setPlacedShips(boolean placedShips) {
        this.placedShips = placedShips;
    }
    
    public boolean isUserTurn() {
        return userTurn;
    }
    public void setUserTurn(boolean turn) {
        this.userTurn = turn;
    }
    
    public void resetUserForNewGame(){
        this.setPlacedShips(false);
    }

    /**
     * @return the lastMessageTime
     */
    public long getLastMessageTime() {
        return lastMessageTime;
    }

    /**
     * @param lastMessageTime the lastMessageTime to set
     */
    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

}