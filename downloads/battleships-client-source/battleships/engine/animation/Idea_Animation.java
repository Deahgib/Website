/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.engine.animation;

/**
 *
 * @author Deahgib
 */
public class Idea_Animation {
    /*
     * The animation class will provide a simple animation.
     * # 1 it wll be an action
     * 
     * # 2 The scene view will have an animation state.
     * 
     * # 3 The scene view will have an animation variable.
     * 
     * #4 An Animation will have a boolean indication if it's started or not.
     * 
     * #5 To start an animation the view will set up the varialbe to a child
     * class of animation. The child classes are the specific animation actions 
     * themselves. one for missile, one for pausing ect...
     * 
     * #6 The animations are actions so they need a responder to call back to.
     * (Likely to be the scene view)
     * 
     * #7 EX: a missile animation will respond to the sceneview.
     * It will have a destination as a boolean like: sendToEnemy = true.
     * If false the animation will send to the user.
     * If will have a Point as a target.
     * It will have a state to change back to on completion.
     * 
     * A missile will be rendered and move to it's target location and on 
     * missile impact the responder will be called with an action to update,
     * n this case place a decal in the attacked spot.
     * And then there will be a pause for the user to see what has happened 
     * before the turn switches. 
     * 
     * 
     * NOTE and animation is only going to work when used between two states.
     * 
     * Animations can have sounds! This means we only need to load the sound in 
     * memory while the animation exisits.
     */
}
