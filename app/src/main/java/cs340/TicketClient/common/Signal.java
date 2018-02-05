package cs340.TicketClient.common;

/**
 * Created by jhens on 2/2/2018.
 */

public class Signal {

    enum SignalType{
        ERROR, OK, ANNOUNCEMENT
    }

    private SignalType type;
    private Object obj;

    Signal(SignalType type, Object obj)
    {
        this.type = type;
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public SignalType getType() {
        return type;
    }
}
