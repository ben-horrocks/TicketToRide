package common.communication;

import java.io.Serializable;

public class Signal implements Serializable
{
    private SignalType signalType;
    private Object object;

    public Signal(SignalType signalType, Object object)
    {
        this.signalType = signalType;
        this.object = object;
    }

    public Object getObject()
    {
        return object;
    }

    public SignalType getSignalType()
    {
        return signalType;
    }

    @Override
    public String toString()
    {
        return signalType + ": " + object.toString();
    }
}