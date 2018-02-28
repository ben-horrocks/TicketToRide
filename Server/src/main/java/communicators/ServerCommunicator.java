package communicators;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import CS340.TicketServer.ClientThread;
import CS340.TicketServer.PushTimer;
import common.DataModels.Username;

public class ServerCommunicator
{
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final Map<Username, ClientThread> threads = new ConcurrentHashMap<>();

	private ServerCommunicator()
	{
		Thread server = new Thread()
		{
			/**
			 * Runs the server. Initializes a ServerSocket at the designated port number. Then, it enters
			 * an infinite loop where it can constantly be listening for sockets from the port number. If
			 * a socket makes a connection, the server will create a new thread for that socket and
			 * continue listening. The server is not meant to handle input/output streams, only sockets.
			 */

			@SuppressWarnings("ConstantConditions")
			/* SuppressWarning for the .accept() method. I'm handling NullableExceptions */
			public void run()
			{
				ServerSocket serverSocket = null;
				Socket socket;

				try
				{
					serverSocket = new ServerSocket(getServerPortNumber());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				boolean keepRunning = true;
				while (keepRunning)
				{
					try
					{
						System.out.println("Waiting for socket connection...");
						socket = serverSocket.accept();
						if (socket == null)
						{
							System.out.println("Incoming socket was null!");
						}
						else
						{
							System.out.println("Connection received from " + socket.getInetAddress());
							System.out.println("Thread for " + socket.getInetAddress() + " started");
							ClientThread clientThread = new ClientThread(socket);
							clientThread.start();
						}
					}
					catch (IOException e)
					{
						System.out.println("Error in receiving client socket: " + e);
					}
					catch (NullPointerException e)
					{
						System.out.println("NullPointerException when trying to accept incoming socket: " + e);
					}
				}
			}
		};

		server.setDaemon(true);
		server.start();
	}

	private static int getServerPortNumber() { return SERVER_PORT_NUMBER; }

	public static Map<Username, ClientThread> getThreads() { return threads; }

	public static void main(String[] args)
	{
		new ServerCommunicator();
		new PushTimer();
	}
}
