package battleships.engine.events;

/**
 *
 * @author Louis Bennette
 */
public class MessageRecievedEvent extends AbstractEvent implements Runnable{
    private String message;
    public MessageRecievedEvent(IEventResponder er, String name, String message){
        super(er, name);
        this.message = message;
    }
    
    public String getMessage(){
        return this.message;
    }

    @Override
    public void run() {
        this.onEvent();
    }
}
