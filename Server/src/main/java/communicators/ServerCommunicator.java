package communicators;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import CS340.TicketServer.ClientThread;
import CS340.TicketServer.PushTimer;
import common.DataModels.Username;

/**
 * Created by Kavika F.
 */

public class ServerCommunicator
{
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final Map<Username, ClientThread> threads = new ConcurrentHashMap<>();

	public ServerCommunicator()
	{
		/**
		 * Runs the server. Initializes a ServerSocket at the designated port number. Then, it enters
		 * an infinite loop where it can constantly be listening for sockets from the port number. If
		 * a socket makes a connection, the server will create a new thread for that socket and
		 * continue listening. The server is not meant to handle input/output streams, only sockets.
		 */
		Thread server = new Thread()
		{
			public void run()
			{
				ServerSocket serverSocket = null;
				Socket socket = null;

				try
				{
					serverSocket = new ServerSocket(getServerPortNumber());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				while (true)
				{
					try
					{
						System.out.println("Waiting for socket connection...");
						socket = serverSocket.accept();
						System.out.println("Connection received from " + socket.getInetAddress());
					}
					catch (IOException e)
					{
						System.out.println("Error in receiving client socket: " + e);
					}
					System.out.println("Thread for " + socket.getInetAddress() + " started");
					ClientThread clientThread = new ClientThread(socket);
					clientThread.start();
				}
			}
		};

		server.setDaemon(true);
		server.start();
	}

	public static int getServerPortNumber()
	{
		return SERVER_PORT_NUMBER;
	}

	public static Map<Username, ClientThread> getThreads()
	{
		return threads;
	}

	public static void main(String[] args)
	{
		new ServerCommunicator();
		new PushTimer();
	}
}
