package CS340.TicketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.CommandParams;
import common.DataModels.Player;
import common.DataModels.Signal;
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

	public void push(Signal signal)
	{
		try
		{
			out.writeObject(signal);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
				while (in.available() == 0)
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
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
				else
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
