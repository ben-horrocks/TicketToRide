package common;

/**
 * Created by Kavika F.
 * Credit to Professor Woodfiel at BYU
 */

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import CS340.TicketServer.ClientProxy;
import CS340.TicketServer.ServerFacade;


public class Command implements Serializable
{
	private static final long serialVersionUID = 5950169519310163575L;

	private String methodName;
	private String[] parameterTypeNames;
	private Object[] parameters = null; //Only used on the server side
	private Class<?>[] parameterTypes; //Only used on server side.
	public enum CommandType
	{
		CLIENT, SERVER
	}
	private CommandType commandType;

	//I don't generate the parameter type names from the
	//parameters because some of the parameters might be
	//null.
	public Command(String methodName, String[] parameterTypeNames, Object[] parameters,
				   CommandType commandType)
	{
		this.methodName = methodName;
		this.parameterTypeNames = parameterTypeNames;
		this.parameters = parameters;
		createParameterTypes();
		this.commandType = commandType;
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
			Method method = null;
			if (commandType == CommandType.SERVER)
			{
				method = ServerFacade.class.getMethod(methodName, parameterTypes);
				result = method.invoke(ServerFacade.getSINGLETON(), parameters);
			}
			else if (commandType == CommandType.CLIENT)
			{
				method = ClientProxy.class.getMethod(methodName, parameterTypes);
				result = method.invoke(ClientProxy.getSINGLETON(), parameters);
			}
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