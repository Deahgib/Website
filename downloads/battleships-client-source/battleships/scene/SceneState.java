package battleships.scene;

/**
 *
 * @author Louis Bennette
 */
public class SceneState {
    // ========================= GENERAL STATE
    public static enum General{
        // GENERAL
        MENU, SINGLEPLAYER, MULTIPLAYER, OPTIONS, CREDITS
    }    
    private static General generalState;
    
    public static General getGeneralState() {
        return generalState;
    }

    public static void setGeneralState(General aCurrentState) {
        generalState = aCurrentState;
    }
    
    // ========================= PLAYER STATE
    public static enum Player{
        // PLAYER
        SHIP_PLACEMENT, ANIMATING, WAIT_FOR_OPONENT, USER_TURN, OPONENT_TURN, 
        GAME_OVER, QUEUED
    }
    private static Player playerState;
    
    public static Player getPlayerState() {
        return playerState;
    }

    public static void setPlayerState(Player aCurrentState) {
        playerState = aCurrentState;
    }
}
