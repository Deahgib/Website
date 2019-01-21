package battleships.engine;

/**
 * @author Louis Bennette
 */
public class Time {
    private static double delta;
    private static long thisFrameStartTime;
    private static long lastFrameStartTime;
    
    public static final long SECOND = 1000000000L;
    
    /**
     * Gets the current system time in nanoseconds as accurately as java 
     * nanoTime() provides. 
     * @return the time as a long
     */
    public static long getTime(){
        return System.nanoTime();
    }

    /**
     * Every frame is one loop of the game loop. For every loop we update the 
     * time since the last loop. Delta represents the time between frames of the 
     * game.
     * @return The time since the previous frame
     */
    public static double getDelta(){
        return delta;
    }
    
    /**
     * This is called for every loop of the game. For every frame in the game we
     * calculate the time since the previous frame. This should only be called
     * once every loop no more or no less.
     */
    public static void setDelta(){
        Time.thisFrameStartTime = Time.getTime();
        Time.delta = (double)(Time.thisFrameStartTime - Time.lastFrameStartTime)/SECOND;
        Time.lastFrameStartTime = Time.thisFrameStartTime;
    }
    
    /**
     * Must init the time just before the game loop starts or objects with 
     * motion could, maybe, perhaps ping out of existance.
     */
    public static void initTime()
    {
        GameLogger.print("Started game time.");
        Time.lastFrameStartTime = Time.getTime();
    }
}
