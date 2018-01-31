package communicators;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import CS340.TicketServer.CommandThread;

/**
 * Created by Kavika F.
 */

public class ServerCommunicator
{
	private static final int SERVER_PORT_NUMBER = 8080;
	private final List<CommandThread> clientThreads = new ArrayList<>();

	private void run()
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
			CommandThread commandThread = new CommandThread(socket);
			commandThread.start();
			clientThreads.add(commandThread);
		}
	}

	public static int getServerPortNumber() {
		return SERVER_PORT_NUMBER;
	}

	public static void main(String[] args)
	{
		new ServerCommunicator().run();
	}
}
