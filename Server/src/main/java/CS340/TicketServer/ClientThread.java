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
import common.DataModels.User;
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
	private boolean close = false;
	private Signal signalFromClient = null;

	private Signal getSignalFromClient() { return signalFromClient; }

	private void setSignalFromClient(Signal signalFromClient) { this.signalFromClient = signalFromClient; }

	private void setClose(boolean close) { this.close = close; }

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
							if (close)
							{
								currentThread().interrupt();
							}
							Object message = messages.take();
							Signal result;
							if (message instanceof CommandParams)
							{
								CommandParams commandParams = (CommandParams) message;
								ServerCommand serverCommand = new ServerCommand(commandParams);
								System.out.println("Recieved \"" + commandParams.getMethodName()
									+ "\" command");
								result = (Signal) serverCommand.execute();
								if (result == null)
								{
									System.out.println("Result of command.execute() was null");
								}
								else if (result.getObject() instanceof User)
								{
									Username username = ((User) result.getObject()).getUsername();
									socketOwners.add(username);
									ServerCommunicator.getThreads().put(username, parent);
								}
							}
							else if (message instanceof Signal)
							{
								result = (Signal) message;
								setSignalFromClient(result);
							}
							else
							{
								return;
							}
							push(result);
						}
						catch (InterruptedException e)
						{
							System.out.println(e + " when receiving in ClientThread");
							closeThread();
							break;
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
						if (close)
						{
							currentThread().interrupt();
						}
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
	 * This function "pushes" or sends Objects from the Server to the Client.
	 * @param object The object to be sent to the Client.
	 */
	public void push(Object object)
	{
		try
		{
			out.writeObject(object);
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

	public Object send(Object object) throws IOException
	{
		Signal result = null;
		push(object);
		while (result == null)
		{
			result = getSignalFromClient();
		}
		setSignalFromClient(null);
		return result;
	}

	private void closeThread()
	{
		Map<Username, ClientThread> threads = ServerCommunicator.getThreads();
		for (Username username : socketOwners)
		{
			if (threads.containsKey(username))
			{
				threads.get(username).setClose(true);
				threads.remove(username);
			}
		}
	}
}
