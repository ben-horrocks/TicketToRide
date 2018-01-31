package CS340.TicketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Command;

/**
 * Created by Kavika F.
 */

public class CommandThread extends Thread
{
	private Socket clientSocket;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;

	public CommandThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
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
				in = new ObjectInputStream(clientSocket.getInputStream());
				Object object = in.readObject();
				Object result = null;
				if (object instanceof Command)
				{
					Command command = (Command)in.readObject();
					result = command.execute();
				}
				else // can be changed to switch statement in future for other object cases
				{
					return;
				}
				out = new ObjectOutputStream(clientSocket.getOutputStream());
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
