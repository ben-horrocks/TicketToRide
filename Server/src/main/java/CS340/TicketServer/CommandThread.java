package CS340.TicketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.CommandParams;
import common.DataModels.Player;
import communicators.ServerCommunicator;

/**
 * Created by Kavika F.
 */

public class CommandThread extends Thread
{
	private Socket clientSocket;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private ServerCommunicator serverCommunicator = null;


	public CommandThread(Socket clientSocket, ServerCommunicator serverCommunicator)
	{
		this.clientSocket = clientSocket;
		this.serverCommunicator = serverCommunicator;
	}

	public void run()
	{
		try
		{
			in = new ObjectInputStream(clientSocket.getInputStream());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			boolean exitThread = false;
			while(!exitThread)
			{
				Object object = in.readObject();
				Object result = null;
				if (object instanceof Player)
				{
					Player player = (Player)object;
					serverCommunicator.getThreads().put(player, this);
				}
				else if (object instanceof CommandParams)
				{
					CommandParams commandParams = (CommandParams)object;
					Command command = new Command(commandParams);
					result = command.execute();
				}
				else // can be changed to switch statement in future for other object cases
				{
					return;
				}
				out.writeObject(result);
				out.flush();
			}
		}
		catch (IOException e)
		{
			System.out.println("Error running Command Thread: " + e);
			e.printStackTrace();
			try
			{
				System.out.println("Closing socket from " + clientSocket.getInetAddress());
				clientSocket.close();
			}
			catch (IOException ex)
			{
				System.out.println("Error trying to close socket: " + ex);
				ex.printStackTrace();
			}
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Class not found exception in CommandThread: " + e);
		}
	}
}
