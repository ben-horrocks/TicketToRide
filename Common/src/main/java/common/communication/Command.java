package common.communication;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A ServerCommand class that extends the CommandParams class and is Serializable. This class
 * is meant to send Commands between Clients and Servers. The CommandParams class has one
 * public function that ServerCommand has to implement (execute()). The implementation will vary
 * depending on the context of the ServerCommand (what is the destination of the ServerCommand?).
 */
public class Command implements Serializable
{
    private static final long serialVersionUID = 5950169519310163575L;

    private String methodName;
    private String[] parameterTypeNames;
    private Object[] parameters = null;
    private Class<?>[] parameterTypes;
    private String classPath;

    //I don't generate the parameter type names from the
    //parameters because some of the parameters might be
    //null.

    /**
     * The constructor for a command.
     * Note: Could not include class path in commandParams due to the different
     * module dependencies of classes. When wanting to make a command, you'll
     * have to execute it almost immediately to avoid passing around a command
     * that may become invalid in different modules.
     *
     * @param commandParams The parameters for the command class to work correctly.
     * @param classPath     The class path for the class trying to be called.
     * @pre Parameters must be non-null. ClassPath must be a valid class path.
     * CommandParams must contain all valid information pertaining to the method being called.
     * @post Will create a valid Command to be executed.
     */
    public Command(CommandParams commandParams, String classPath)
    {
        this.methodName = commandParams.getMethodName();
        this.parameterTypeNames = commandParams.getParameterTypeNames();
        this.parameters = commandParams.getParameters();
        this.classPath = classPath;
        createParameterTypes();
    }


    public String getMethodName() { return methodName; }

	public String[] getParameterTypeNames() { return parameterTypeNames; }

	public Object[] getParameters() { return parameters; }


    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("methodName = ");
        result.append(methodName);
        result.append("\n");

        result.append("    parameterTypeNames = ");
        for (String parameterTypeName : parameterTypeNames)
        {
            result.append(parameterTypeName);
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length());
        result.append("\n");

        result.append("    parameters = ");
        if (parameters == null)
        {
            result.append("null\n");
        } else
        {
            for (Object parameter : parameters)
            {
                result.append(parameter);
                result.append("(");
                result.append(parameter.getClass().getName());
                result.append(")");
                result.append(", ");
            }
            result.delete(result.length() - 2, result.length());
        }

        return result.toString();
    }

    @SuppressWarnings("TryWithIdenticalCatches") // For the InvocationTargetException on API < 19
    public Object execute()
    {
        Object result = null;
        try
        {
            Class<?> handler = Class.forName(classPath);
            Method getSingleton = handler.getDeclaredMethod("getSINGLETON");
            getSingleton.setAccessible(true);
            Object singleton = getSingleton.invoke(null);
            Method method = handler.getMethod(methodName, parameterTypes);
            result = method.invoke(singleton, parameters);

        } catch (NoSuchMethodException | SecurityException e)
        {
            System.out.println("ERROR: Could not find the method " + methodName +
                               ", or, there was a security error");
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            System.err.println("Illegal access while trying to execute the method " + methodName);
            e.printStackTrace();
        } catch (IllegalArgumentException e)
        {
            System.out.println(
                    "ERROR: Illegal argument while trying to find the method " + methodName);
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            System.err.println("Illegal access while trying to execute the method " + methodName);
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            System.err.println("ERROR: no class found with path: " + classPath);
            e.printStackTrace();
        }

        return result;
    }

    private void createParameterTypes()
    {
        parameterTypes = new Class<?>[parameterTypeNames.length];
        for (int i = 0; i < parameterTypeNames.length; i++)
        {
            try
            {
                parameterTypes[i] = getClassFor(parameterTypeNames[i]);
            } catch (ClassNotFoundException e)
            {
                System.err.println(
                        "ERROR: IN ServerCommand.execute could not create a parameter type from the parameter type name " +
                        parameterTypeNames[i]);
                e.printStackTrace();
            }
        }
    }

    private static Class<?> getClassFor(String className) throws ClassNotFoundException
    {
        Class<?> result;
        switch (className)
        {
            case "boolean":
                result = boolean.class;
                break;
            case "byte":
                result = byte.class;
                break;
            case "char":
                result = char.class;
                break;
            case "double":
                result = double.class;
                break;
            case "float":
                result = float.class;
                break;
            case "int":
                result = int.class;
                break;
            case "long":
                result = long.class;
                break;
            case "short":
                result = short.class;
                break;
            default:
                result = Class.forName(className);
        }
        return result;
    }
}