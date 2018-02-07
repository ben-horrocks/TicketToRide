package common.DataModels;

public class Signal
{
    private SignalType signalType;
    private Object object;

    public Signal(SignalType signalType, Object object)
    {
		this.signalType = signalType;
		this.object = object;
    }

	public Object getObject() { return object; }

	public SignalType getSignalType() { return signalType; }
}