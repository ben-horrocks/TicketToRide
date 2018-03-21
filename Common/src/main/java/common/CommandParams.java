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

    /**
     * @param methodName         The name of the method wanting to be made.
     * @param parameterTypeNames The name of the types for the parameters.
     * @param parameters         The parameters for the method.
     * @pre Parameters cannot be null. Arrays can have null objects in them, though.
     * FacadeEnum must be a valid enum.
     * @post A valid CommandParams object.
     */
    public CommandParams(String methodName, String[] parameterTypeNames, Object[] parameters)
    {
        this.methodName = methodName;
        this.parameterTypeNames = parameterTypeNames;
        this.parameters = parameters;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public String[] getParameterTypeNames()
    {
        return parameterTypeNames;
    }

    public Object[] getParameters()
    {
        return parameters;
    }
}
