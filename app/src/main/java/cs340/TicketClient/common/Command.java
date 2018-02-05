package cs340.TicketClient.common;

/**
 * Created by Kavika F.
 * Credit to Professor Woodfiel at BYU
 */

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cs340.TicketClient.Communicator.ServerProxy;


public class Command implements Serializable
{
	private static Gson gson = new Gson();
	private static final long serialVersionUID = 5950169519310163575L;

	private String methodName;
	private String[] parameterTypeNames;
	private Object[] parameters = null; //Only used on the server side
	private String[] parametersAsJsonStrings;
	private Class<?>[] parameterTypes; //Only used on server side.

	//I don't generate the parameter type names from the
	//parameters because some of the parameters might be
	//null.
	public Command(String methodName, String[] parameterTypeNames, Object[] parameters)
	{
		this.methodName = methodName;
		this.parameterTypeNames = parameterTypeNames;
		this.parametersAsJsonStrings = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++)
		{
			parametersAsJsonStrings[i] = gson.toJson(parameters[i]);
		}
		this.parameters = parameters;
		createParameterTypes();
	}

	/*
	public Command(InputStreamReader inputStreamReader)
	{
		Command tempCommand = (Command) gson.fromJson(inputStreamReader, Command.class);

		methodName = tempCommand.getMethodName();
		parameterTypeNames = tempCommand.getParameterTypeNames();

		parametersAsJsonStrings = tempCommand.getParametersAsJsonStrings();
		createParameterTypes();
		parameters = new Object[parametersAsJsonStrings.length];
		for (int i = 0; i < parametersAsJsonStrings.length; i++)
		{
			parameters[i] = gson.fromJson(parametersAsJsonStrings[i], parameterTypes[i]);
		}

	}
	*/

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


	public String[] getParametersAsJsonStrings()
	{
		return parametersAsJsonStrings;
	}

	public String toString()
	{
		StringBuffer result = new StringBuffer();
		result.append("methodName = " + methodName + "\n");

		result.append("    parameterTypeNames = ");
		for (String parameterTypeName : parameterTypeNames)
		{
			result.append(parameterTypeName + ", ");
		}
		result.delete(result.length() - 2, result.length());
		result.append("\n");

		result.append("    parametersAsJsonStrings = ");
		for (String parameterString : parametersAsJsonStrings)
		{
			result.append("'" + parameterString + "'");
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
				result.append("(" + parameter.getClass().getName() + ")");
				result.append(", ");
			}
			result.delete(result.length() - 2, result.length());
		}

		return result.toString();
	}

	public Object execute()
	{
		Object result = null;

		try
		{
			Method method = ServerProxy.class.getMethod(methodName, parameterTypes);
			result = method.invoke(ServerProxy.getInstance(), parameters);
		} catch (NoSuchMethodException | SecurityException e)
		{
			System.out.println("ERROR: Could not find the method " + methodName + ", or, there was a security error");
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			System.err.println("Illegal accesss while trying to execute the method " + methodName);
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			System.out.println("ERROR: Illegal argument while trying to find the method " + methodName);
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			System.err.println("Illegal accesss while trying to execute the method " + methodName);
			e.printStackTrace();
		}

		return result;
	}

	private final void createParameterTypes()
	{
		parameterTypes = new Class<?>[parameterTypeNames.length];
		for (int i = 0; i < parameterTypeNames.length; i++)
		{
			try
			{
				parameterTypes[i] = getClassFor(parameterTypeNames[i]);
			} catch (ClassNotFoundException e)
			{
				System.err.println("ERROR: IN Command.execute could not create a parameter type from the parameter type name " +
						parameterTypeNames[i]);
				e.printStackTrace();
			}
		}
	}

	private static final Class<?> getClassFor(String className)
			throws ClassNotFoundException
	{
		Class<?> result = null;
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