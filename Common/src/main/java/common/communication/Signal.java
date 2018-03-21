package common.communication;

import java.io.Serializable;

public class Signal implements Serializable
{
    private SignalType signalType;
    private Object object;
    private String objectClassName;

    public Signal(SignalType signalType, Object object)
    {
        this.signalType = signalType;
        this.object = object;
        objectClassName = object.getClass().getName();
    }

    public Object getObject() { return object; }

    public SignalType getSignalType() { return signalType; }

    public String getObjectClassName() { return objectClassName; }
    @Override
    public String toString()
    {
        return signalType + ": " + objectClassName;
    }
}