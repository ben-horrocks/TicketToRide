package communicators;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Command;

/**
 * Created by Kavika F.
 */

public class ClientCommunicator
{
	public static final ClientCommunicator SINGLETON = new ClientCommunicator();

	private ClientCommunicator()
	{
		try
		{
			socket = new Socket(SERVER_HOST, 8080);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		}
		catch (IOException e)
		{
			System.out.println("IOException in ClientCommunicator: " + e);
			e.printStackTrace();
		}
	}

	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;

	public Object send(Command command) throws IOException {

		Object result = null;
		try
		{
			outputStream.writeObject(command);
			result = inputStream.readObject();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void closeSocket()
	{
		try
		{
			inputStream.close();
			outputStream.close();
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Error closing clientSocket" + e);
			e.printStackTrace();
		}
	}

	//Auxiliary Constants, Attributes, and Methods
	private static final String SERVER_HOST = "localhost";
}
