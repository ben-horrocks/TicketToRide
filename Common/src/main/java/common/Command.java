package common;

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
	 * The constructor for the ServerCommand to be sent.
	 * @param methodName The name of the method the sending party wishes to invoke.
	 * @param parameterTypeNames The types of the parameters.
	 * @param parameters The parameters to be used in the method.
	 */
	public Command(String methodName, String[] parameterTypeNames, Object[] parameters)
	{
		this.methodName = methodName;
		this.parameterTypeNames = parameterTypeNames;
		this.parameters = parameters;
		createParameterTypes();
	}

	public Command(CommandParams commandParams)
	{
		this.methodName = commandParams.getMethodName();
		this.parameterTypeNames = commandParams.getParameterTypeNames();
		this.parameters = commandParams.getParameters();
		this.classPath = commandParams.getClassPath();
		createParameterTypes();
	}

	/*
	public String getMethodName() { return methodName; }

	public String[] getParameterTypeNames() { return parameterTypeNames; }

	public Object[] getParameters() { return parameters; }
	*/

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
				result.append( ")");
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
			System.out.println("ERROR: Could not find the method " + methodName + ", or, there was a security error");
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			System.err.println("Illegal access while trying to execute the method " + methodName);
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			System.out.println("ERROR: Illegal argument while trying to find the method " + methodName);
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
				System.err.println("ERROR: IN ServerCommand.execute could not create a parameter type from the parameter type name " +
						parameterTypeNames[i]);
				e.printStackTrace();
			}
		}
	}

	private static Class<?> getClassFor(String className)
			throws ClassNotFoundException
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