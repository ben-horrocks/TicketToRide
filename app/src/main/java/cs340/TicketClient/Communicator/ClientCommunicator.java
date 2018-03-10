package cs340.TicketClient.Communicator;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import common.CommandParams;
import common.DataModels.GameData.ServerGameData;
import common.DataModels.GameInfo;
import common.DataModels.Signal;
import common.DataModels.SignalType;

/**
 * Created by Kavika F.
 */

public class ClientCommunicator
{
	/**
	 * Singleton object to access the ClientCommunicator
	 */
	private static ClientCommunicator SINGLETON = null;
	public static ClientCommunicator getSingleton()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new ClientCommunicator();
		}
		return SINGLETON;
	}

	private ConnectionToServer server;
	private Thread receiver;
	private Socket socket;
	private LinkedBlockingQueue<Object> messages;
	private Signal signalFromServer = null;
	private static String SERVER_HOST = "localhost";

	public static void setSERVER_HOST(String ip) { SERVER_HOST = ip; }

	private Signal getSignalFromServer() { return signalFromServer; }

	private void setSignalFromServer(Signal signalFromServer) { this.signalFromServer = signalFromServer; }

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
			messages = new LinkedBlockingQueue<>();
			server = new ConnectionToServer(socket);

			receiver = new Thread()
			{
				/**
				 * While this thread is running, take Objects from the blockingQueue, cast them
				 * as signals (because we're assuming that everything coming in is a Signal),
				 * and deal with each one depending on the object's type.
				 */
				public void run()
				{
					boolean keepRunning = true;
					while(keepRunning)
					{
						try
						{
							Object received = messages.take();
							messages.remove(received);
							if (received instanceof Signal)
							{
								Signal signal = (Signal) received;
								if (signal.getSignalType() == SignalType.UPDATE &&
										signal.getObject() instanceof List)
								{
									// Hope that the List of type UPDATE has GameInfo Objects
									@SuppressWarnings("unchecked")
									List<GameInfo> infoList = (List<GameInfo>) signal.getObject();
									ClientFacade.getSingleton().updateGameList(infoList);
								}else if (signal.getSignalType() == SignalType.START_GAME){
									ClientFacade c = new ClientFacade();
									//c.startGame(((ServerGameData)signal.getObject()).getId());
								}
								else
								{
									setSignalFromServer(signal);
								}
							}
							else if (received instanceof CommandParams)
							{
								System.out.println("Signal received on client side");
								CommandParams params = (CommandParams) received;
								ClientCommand command = new ClientCommand(params);
								Signal result = (Signal) command.execute();
								server.write(result);
								System.out.println("Signal sent to server");
							}

						}
						catch (InterruptedException e)
						{
							System.out.println("InterruptedException when receiving data from server: " + e);
						}
					}
				}
			};

			receiver.setDaemon(true);
		}
		catch (IOException e)
		{
			System.out.println("IOException in ClientCommunicator: " + e);
			e.printStackTrace();
		}
	}

	private class ConnectionToServer
	{
		private Socket socket;
		private ObjectInputStream inputStream;
		private ObjectOutputStream outputStream;
		private Thread read;

		private ConnectionToServer(Socket socket) throws IOException
		{
			this.socket = socket;
			inputStream = new ObjectInputStream(this.socket.getInputStream());
			outputStream = new ObjectOutputStream(this.socket.getOutputStream());

			read = new Thread()
			{
				/**
				 * While this thread is running, constantly listen for incoming objects from the
				 * server. Once something has come in, assign it to an Object, and push that Object
				 * onto the blockingQueue. Continue listening.
				 */
				public void run()
				{
					while(true)
					{
						try
						{
							Object object = inputStream.readObject();
							messages.put(object);
						}
						catch (IOException e)
						{
							System.out.println("IOException in read Thread: " + e);
							e.printStackTrace();
							// if SocketException, like a problem with Server, stop listening
							if (e instanceof SocketException || e instanceof EOFException)
							{
								closeSocket();
								break;
							}
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
					System.out.println("You have disconnected from the Server!");
				}
			};

			read.setDaemon(true);
		}

		/**
		 * Function to output objects from the client to the server.
		 * @param object The object to be sent.
		 */
		private void write(Object object)
		{
			try
			{
				outputStream.writeObject(object);
			}
			catch (IOException e)
			{
				System.out.println("IOException when writing object to Server: " + e);
			}
		}
	}

	/**
	 * Sends an object from the client to the server.
	 * @param object The object to be sent to the server.
	 * @return Return a result object from the server. May or may not be an error object.
	 * @throws IOException Can throw an IOException if there is an issue with the input/output streams.
	 */
	public Object send(Object object) throws IOException
	{
		if (server.read.getState() == Thread.State.NEW)
		{
			server.read.start();
			receiver.start();
		}
		setSignalFromServer(null);
		Signal result = null;
		server.write(object);
		while (result == null)
		{
			result = getSignalFromServer();
		}
		setSignalFromServer(null);
		return result;

	}

	/**
	 * Closes the socket held by the ClientCommunicator.
	 */
	private void closeSocket()
	{
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Error closing clientSocket" + e);
			e.printStackTrace();
		}
	}
}
