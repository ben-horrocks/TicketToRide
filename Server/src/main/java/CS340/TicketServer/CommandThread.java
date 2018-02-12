package CS340.TicketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import common.CommandParams;
import common.DataModels.Player;
import common.DataModels.Signal;
import communicators.ServerCommunicator;

/**
 * Created by Kavika F.
 */

public class CommandThread extends Thread
{
	private LinkedBlockingQueue<Object> messages;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public CommandThread(final Socket clientSocket)
	{
		try
		{
			messages = new LinkedBlockingQueue<>();
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			final CommandThread thisThread = this;
			Thread receiver = new Thread(thisThread)
			{
				CommandThread parent = thisThread;

				public void run()
				{
					while(true)
					{
						try
						{
							Object message = messages.take();
							Signal result;
							if (message instanceof CommandParams)
							{
								CommandParams commandParams = (CommandParams) message;
								Command command = new Command(commandParams);
								result = (Signal) command.execute();
								if (result.getObject() instanceof Player)
								{
									Player player = (Player) result.getObject();
									ServerCommunicator.getThreads().put(player, parent);
								}
							}
							else
							{
								return;
							}
							push(result);
						}
						catch (InterruptedException e)
						{
							System.out.println("InterruptedException when receiving in CommandThread: " + e);
						}
					}
				}
			};

			receiver.setDaemon(true);
			receiver.start();

			Thread read = new Thread()
			{
				public void run()
				{
					boolean keepRunning = true;
					while (keepRunning)
					{
						try
						{
							Object object = in.readObject();
							messages.put(object);
						}
						catch (IOException e)
						{
							/* Quietly Ignore EOF exceptions */
						}
						catch (ClassNotFoundException e)
						{
							System.out.println("ClassNotFoundException in read Thread: " + e);
							e.printStackTrace();
						}
						catch (InterruptedException e)
						{
							System.out.println("InterruptedException in read Thread: " + e);
							e.printStackTrace();
						}
					}
					try
					{
						clientSocket.close();
					}
					catch (IOException e)
					{
						System.out.println("Error closing socket in read thread: " + e);
						e.printStackTrace();
					}
				}
			};

			read.setDaemon(true);
			read.start();
		}
		catch (IOException e)
		{
			System.out.println("IOException in read Thread: " + e);
			e.printStackTrace();
		}
	}

	public void push(Signal signal)
	{
		try
		{
			out.writeObject(signal);
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
