package common;

import java.io.Serializable;

/**
 *
 */
public class CommandParams implements Serializable
{
	private String methodName;
	private String[] parameterTypeNames;
	private Object[] parameters;

	public CommandParams(String methodName, String[] parameterTypeNames, Object[] parameters)
	{
		this.methodName = methodName;
		this.parameterTypeNames = parameterTypeNames;
		this.parameters = parameters;
	}

	public String getMethodName() { return methodName; }

	public String[] getParameterTypeNames() { return parameterTypeNames; }

	public Object[] getParameters() { return parameters; }
}
