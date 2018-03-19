package CS340.TicketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.Command;
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
	private static final Logger logger = LogKeeper.getSingleton().getLogger();

	private LinkedBlockingQueue<Object> messages;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket clientSocket;
	private Set<Username> socketOwners = new HashSet<>();
	private boolean close = false;
	private Signal signalFromClient = null;

	private Signal getSignalFromClient() { return signalFromClient; }

	private void setSignalFromClient(Signal signalFromClient) { this.signalFromClient = signalFromClient; }

	private void close() { close = true; }

	public ClientThread(final Socket clientSocket)
	{
		logger.entering("ClientThread", "<init>", clientSocket);
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
					boolean keepRunning = true;
					while(keepRunning)
					{
						try
						{
							if (close)
							{
								keepRunning = false;
								currentThread().interrupt();
							}
							logger.finer("Taking message from blocking queue");
							Object message = messages.take();
							Signal result;
							if (message instanceof CommandParams)
							{
								CommandParams commandParams = (CommandParams) message;
								Command serverCommand = new Command(commandParams, ServerFacade.class.getName());
								logger.fine("Received \"" + commandParams.getMethodName()
									+ "\" command");
								result = (Signal) serverCommand.execute();
								if (result == null)
								{
									logger.fine("Result of command.execute() was null");
								}
								else if (result.getObject() instanceof User)
								{
									Username username = ((User) result.getObject()).getUsername();
									socketOwners.add(username);
									ServerCommunicator.getThreads().put(username, parent);
								}
								ServerFacade.getSINGLETON().reportCommand(commandParams);
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
							logger.severe(e + " when receiving in ClientThread");
							keepRunning = false;
							closeThread();
						}
					}
				}
			};

			receiver.setDaemon(true);
			receiver.start();
			logger.finer("Receiver Thread Started");

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
							keepRunning = false;
							this.interrupt();
						}
						try
						{
							logger.finer("Reading object stream on server side");
							Object object = in.readObject();
							if(object != null)
								logger.finer("Putting object in message blocking queue");
								messages.put(object);
						}
						catch (IOException e)
						{
							/* Quietly Ignore EOF exceptions */
							logger.log(Level.FINEST, "IOException in read thread on server side");
						}
						catch (ClassNotFoundException | InterruptedException e)
						{
							logger.severe(e + " in read Thread");
							e.printStackTrace();
						}
					}
					closeThread();
				}
			};

			read.setDaemon(true);
			read.start();
			logger.finer("Read Thread Started");
		}
		catch (IOException e)
		{
			logger.severe(e + " in read Thread");
			e.printStackTrace();
		}
		logger.exiting("ClientThread", "<init>");
	}

	/**
	 * This function "pushes" or sends Objects from the Server to the Client.
	 * @param object The object to be sent to the Client.
	 */
	synchronized void push(Object object)
	{
		logger.entering("ClientThread", "push", object);
		try
		{
			logger.finest("Writing object to output stream");
			out.writeObject(object);
			out.flush();
			out.reset();
		}
		catch (IOException e)
		{
			logger.severe(clientSocket.getInetAddress() + " disconnected from the server");
			closeThread();
		}
		logger.exiting("ClientThread", "push");
	}

	public synchronized Object send(Object object) throws IOException
	{
		logger.entering("ClientThread", "send", object);
		Signal result = null;
		push(object);
		while (result == null)
		{
			result = getSignalFromClient();
		}
		setSignalFromClient(null);
		logger.exiting("ClientThread", "send", result);
		return result;
	}

	private void closeThread()
	{
		logger.entering("ClientThread", "closeThread");
		Map<Username, ClientThread> threads = ServerCommunicator.getThreads();
		for (Username username : socketOwners)
		{
			if (threads.containsKey(username))
			{
				threads.get(username).close();
				threads.remove(username);
				logger.finer("Removed thread reference from thread map");
			}
		}
		try
		{
			logger.finer("Closing socket from server side");
			clientSocket.close();
			logger.finer("Socket closed on server side");
		}
		catch (IOException e)
		{
			logger.severe(e + " when closing socket from server side");
			e.printStackTrace();
		}
		logger.exiting("ClientThread", "closeThread");
	}
}
