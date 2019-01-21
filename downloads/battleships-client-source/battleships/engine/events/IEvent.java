package battleships.engine.events;

/**
 *
 * @author Louis Bennette
 */
public interface IEvent {
    public String getName();
    public void onEvent();
}
