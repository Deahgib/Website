 package battleshipsserver.core;

/**
 * @author Louis
 */
public class ServerGlobals {
    public static final boolean USE_GUI = true;
    
    public static final String VERSION = "Alpha 4";
    
    public static final int SERVER_PORT = 6464;
    
    public static final long userTimeoutLength = 300000L; // Milliseconds
    
    private static boolean shutdownServer = false;
    private static boolean killAllThreads = false;
    private static boolean killAllGameInstances = false;

    public static boolean isShutdownServer() {
        return shutdownServer;
    }
    public static void setShutdownServer(boolean aShutdownServer) {
        shutdownServer = aShutdownServer;
    }

    public static boolean isKillAllThreads() {
        return killAllThreads;
    }
    
    public static void setKillAllThreads(boolean aKillAllThreads) {
        killAllThreads = aKillAllThreads;
    }

    public static boolean isKillAllGameInstances() {
        return killAllGameInstances;
    }
    
    public static void setKillAllGameInstances(boolean aKillAllChatInstances) {
        killAllGameInstances = aKillAllChatInstances;
    }
}
