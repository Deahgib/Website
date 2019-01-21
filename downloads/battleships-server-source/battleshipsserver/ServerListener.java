package battleshipsserver;

import battleshipsserver.core.Printer;
import battleshipsserver.core.ServerGlobals;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Louis
 */
public class ServerListener implements Runnable {
    @Override
    public void run() {
        ServerSocket theListener = createServerSocket(ServerGlobals.SERVER_PORT);
        if(theListener != null){
            pollForIncommingUsers(theListener);
        }
        else
        {
            System.err.println("Failed to create server socket.");
        }
        destroyServerSocket(theListener);
    }
    
    private ServerSocket createServerSocket(int port){
        try {
            ServerSocket tmp = new ServerSocket(port);
            tmp.setSoTimeout(0);
            return tmp;
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void destroyServerSocket(ServerSocket theListener){
        try {
            theListener.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failed to close the server socket.");
        }
    }    
    
    private void pollForIncommingUsers(ServerSocket theListener){
        while(!ServerGlobals.isShutdownServer()){
            Socket tmp = new Socket();
            try {
                Printer.println(" - Waiting for new client to connect.");
                tmp = theListener.accept();
                Printer.println(" - Found a client.");
                Thread loginThread = new Thread(new Login(tmp));
                loginThread.start();
            } catch (IOException ex) {
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Failure on client connection.");
                ServerGlobals.setShutdownServer(true);
            }
        }
    }
}