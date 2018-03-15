package common;

import java.io.Serializable;

/**
 * The parameters for a Command object.
 */
public class CommandParams implements Serializable
{
	private String methodName;
	private String[] parameterTypeNames;
	private Object[] parameters;
	private String classPath;

	/**
	 * @pre Parameters cannot be null. Arrays can have null objects in them, though.
	 * FacadeEnum must be a valid enum.
	 * @post A valid CommandParams object.
	 * @param methodName The name of the method wanting to be made.
	 * @param parameterTypeNames The name of the types for the parameters.
	 * @param parameters The parameters for the method.
	 * @param facade The destination facade for this set of command parameters.
	 */
	public CommandParams(String methodName, String[] parameterTypeNames, Object[] parameters, FacadeEnum facade)
	{
		this.methodName = methodName;
		this.parameterTypeNames = parameterTypeNames;
		this.parameters = parameters;
		setClassPath(facade);
	}

	public String getMethodName() { return methodName; }

	public String[] getParameterTypeNames() { return parameterTypeNames; }

	public Object[] getParameters() { return parameters; }

	public String getClassPath() { return classPath; }

	public enum FacadeEnum { SERVER, CLIENT }

	private void setClassPath(FacadeEnum facade)
	{
		if (facade == FacadeEnum.SERVER) { classPath = "CS340.TicketServer.ServerFacade"; }
		else if (facade == FacadeEnum.CLIENT) { classPath = "cs340.TicketClient.Communicator.ClientFacade"; }
	}
}
