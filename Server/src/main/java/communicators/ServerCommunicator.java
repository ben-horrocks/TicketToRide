package communicators;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import CS340.TicketServer.*;
import common.player_info.Username;

public class ServerCommunicator
{
    private static final int SERVER_PORT_NUMBER = 8080;
    private static final Map<Username, ClientThread> threads = new ConcurrentHashMap<>();
    private static final Logger logger = LogKeeper.getSingleton().getLogger();

    private ServerCommunicator()
    {
        logger.entering("ServerCommunicator", "ServerCommunicator()");
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
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                boolean keepRunning = true;
                while (keepRunning)
                {
                    try
                    {
                        logger.fine("Waiting for socket...");
                        socket = serverSocket.accept();
                        if (socket == null)
                        {
                            logger.fine("Incoming socket was null");
                        } else
                        {
                            logger.fine("Connection received from " + socket.getInetAddress());
                            ClientThread clientThread = new ClientThread(socket);
                            clientThread.start();
                            logger.fine("Thread for " + socket.getInetAddress() + " started");
                        }
                    } catch (IOException e)
                    {
                        logger.warning("Error in receiving client socket: " + e);
                    } catch (NullPointerException e)
                    {
                        logger.warning(
                                "NullPointerException when trying to accept incoming socket: " + e);
                    }
                }
            }
        };

        server.setDaemon(true);
        logger.finer("Starting Server...");
        server.start();
        logger.finer("Server Started");
        logger.exiting("ServerCommunicator", "ServerCommunicator()");
    }

    private static int getServerPortNumber()
    {
        return SERVER_PORT_NUMBER;
    }

    public static Map<Username, ClientThread> getThreads()
    {
        return threads;
    }

    public static void main(String[] args)
    {
        if(args.length < 4 || args.length > 5)
        {
            System.out.println("USAGE: java ServerCommunicator ['sql' or 'flat'] [commandnum] [optional: --clean]");
            return;
        }
        String databaseType = args[2];
        int commandNum = Integer.parseInt(args[3]);
        Boolean cleanDatabase = false;
        if(args.length == 5)
        {
            if(args[4].equals("--clean"))
            {
                cleanDatabase = true;
            } else
            {
                System.out.println("USAGE: java ServerCommunicator ['sql' or 'flat'] [commandnum] [optional: --clean]");
                return;
            }
        }
        try
        {
            if(databaseType.equals("sql"))
            {
                ServerFacade.getSINGLETON().setPlugin("SQL", "plugin.DatabasePlugin", commandNum, cleanDatabase);
            } else if(databaseType.equals("flat"))
            {
                ServerFacade.getSINGLETON().setPlugin("FlatFile", "plugin.DatabasePlugin", commandNum, cleanDatabase);
            } else
            {
                System.out.println("USAGE: java ServerCommunicator ['sql' or 'flat'] [commandnum] [optional: --clean]");
                return;
            }
        } catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        new ServerCommunicator();
        new HeartBeat();
    }
}