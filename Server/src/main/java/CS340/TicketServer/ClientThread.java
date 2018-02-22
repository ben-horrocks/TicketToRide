package CS340.TicketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import common.CommandParams;
import common.DataModels.Player;
import common.DataModels.Signal;
import common.DataModels.Username;
import communicators.ServerCommunicator;

/**
 * Created by Kavika F.
 */

public class ClientThread extends Thread
{
	private LinkedBlockingQueue<Object> messages;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket clientSocket;
	private Set<Username> socketOwners = new HashSet<>();

	public ClientThread(final Socket clientSocket)
	{
		try
		{
			this.clientSocket = clientSocket;
			messages = new LinkedBlockingQueue<>();
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			final ClientThread thisThread = this;


			Thread receiver = new Thread(thisThread)
			{
				ClientThread parent = thisThread;

				/**
				 * When this thread is running, it blocks when there is nothing to take from
				 * the LinkedBlockingQueue. When there is something in the queue, it removes it from
				 * the queue and looks to see what type of Object it is. What is done with the Object
				 * depends on the Object's type.
				 */
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
								ServerCommand serverCommand = new ServerCommand(commandParams);
								result = (Signal) serverCommand.execute();
								if (result == null)
								{
									System.out.println("Result of command.execute() was null");
								}
								else if (result.getObject() instanceof Player)
								{
									Username username = ((Player) result.getObject()).getUsername();
									socketOwners.add(username);
									ServerCommunicator.getThreads().put(username, parent);
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
							System.out.println("InterruptedException when receiving in ClientThread: " + e);
						}
					}
				}
			};

			receiver.setDaemon(true);
			receiver.start();

			Thread read = new Thread(thisThread)
			{
				/**
				 * When this thread is running, it is constantly listening on the input stream of
				 * the socket. When something is received, it is put into an object and that object
				 * is put into the LinkedBlockingQueue so it can be dealt with. That way, the
				 * inputStream can go back to listening for incoming objects.
				 */
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
						closeThread();
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

	/**
	 * This function "pushes" or sends Signal Objects from the Server to the Client.
	 * @param signal The signal to be sent to the Client.
	 */
	public void push(Signal signal)
	{
		try
		{
			out.writeObject(signal);
			out.flush();
			out.reset();
		}
		catch (IOException e)
		{
			System.out.println(clientSocket.getInetAddress() + " disconnected from the server");
			try
			{
				clientSocket.close();
				closeThread();
			}
			catch (IOException ex)
			{
				System.out.println("Error closing socket " + clientSocket.getInetAddress());
				ex.printStackTrace();
			}
		}
	}

	private void closeThread()
	{
		Map<Username, ClientThread> threads = ServerCommunicator.getThreads();
		for (Username username : socketOwners)
		{
			if (threads.containsKey(username))
			{
				threads.remove(username);
			}
		}
	}
}
