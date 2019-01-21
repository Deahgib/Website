/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.engine;

/**
 *
 * @author Deahgib
 */
public class Idea_PopupView {
    /*
     * Firstly the AbstactView will be requied to take a list of AbstractView s
     * If the list has views we update and draw those aswell. This list represents
     * the view's subViews. Subviews are called after the update of the prent view
     * is ended and same for the draw.
     * 
     * The Popup view is a sub view of singleplayer for xample and has it's own 
     * components and is a responder to it's specific events.
     * 
     * PopupView will be a subclass of abstactview and will istelf be abstract.
     * It will hold a closeView boolean for the parent view to know when to remove it
     * from the subView list. 
     * 
     * Then GameNemu popup can be created to pause the game using pause anim and 
     * the gamemenu can have it's options and buttons.
     * 
     */
}
