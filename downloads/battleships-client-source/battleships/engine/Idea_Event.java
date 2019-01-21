/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.engine;

/**
 *
 * @author Deahgib
 */
public class Idea_Event {
    /*
     * Create an event system.
     * 
     * Event will take a base event, this will be the universal event system.
     * For all animations, button presses or text inputs.
     * 
     * It will hold a responder.
     * 
     * The event eill have a doEvent method this is called on the responder.
     * the responder class will have an onEvent (or onAction) method which takes
     * a "Type" enumeration class(for the type of event Text input, Button press
     * , animation or time), and the caller as an Event class. the 
     * event can later have a time event was launched variable.
     * 
     * With this we can make system based events.
     * 
     * We can have  TimeEvent this is held in a list, and the list is checked 
     * for every loop in the GameLoop class. If the executeEventTime variable 
     * is past the current clock time then we execute that event and remove it 
     * from the list. That event will call back to a class and execute some 
     * secion of code that event corresponds to. The TimeEvent class will need 
     * to be allocated and initialised with a String name as an identifier as 
     * well as an endTime
     * 
     * The animation class will need to be revamped 
     * The animation is simply going to be a model. This 
     * this model wll simply hold wheter the animation is over. Pause is still 
     * useable but the Missile animation will no longer use Pause. Insead a
     * time event will be created. The executed action will update the model of 
     * the animation setting it to Over..
     * Pause is still usefull if an animation is required to maintain the 
     * cureent draw and stop updates. of the view. TimeEvent alone will not change the game event 
     * to ANIMATING state and change back. The animation is still nessesary for 
     * a Pause.
     * 
     * 
     */
}
