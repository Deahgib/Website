package battleships.engine.events;

/**
 *
 * @author Louis Bennette
 */
public abstract class AbstractEvent implements IEvent {

    protected IEventResponder eventResponder;
    protected String name;
    private boolean killEvent;
    
    public AbstractEvent(IEventResponder er, String name){
        this.eventResponder = er;
        this.name = name;
        this.killEvent = false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void onEvent() {
        if(!this.isKillEvent()){
            this.eventResponder.handleEvent(this);
        }
    }

    public boolean isKillEvent() {
        return killEvent;
    }

    public void setKillEvent(boolean killEvent) {
        this.killEvent = killEvent;
    }
}
