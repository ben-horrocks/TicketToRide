package cs340.TicketClient.Communicator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import cs340.TicketClient.Login.LoginModel;

/**
 * Created by Kavika F.
 */

public class ClientCommunicator
{
	private static ClientCommunicator SINGLETON = null;
	public static ClientCommunicator getSingleton()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new ClientCommunicator();
		}
		return SINGLETON;
	}



	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private static String SERVER_HOST = "localhost";

	/**
	 * Initialize a ClientCommunicator object. Create a socket to communicate with the server.
	 * This socket will stay open throughout the program; therefore, the ClientCommunicator object
	 * must stay relevant throughout a client's entire game.
	 */
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

	public static void setSERVER_HOST(String ip)
	{
		SERVER_HOST = ip;
	}

	/**
	 * Sends an object from the client to the server.
	 * @param object The object to be sent to the server.
	 * @return Return a result object from the server. May or may not be an error object.
	 * @throws IOException Can throw an IOException if there is an issue with the input/output streams.
	 */
	public Object send(Object object) throws IOException {
		Object result = null;
		try
		{
			outputStream.writeObject(object);
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
}
