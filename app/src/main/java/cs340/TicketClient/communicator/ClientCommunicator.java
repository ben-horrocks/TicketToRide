package cs340.TicketClient.communicator;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import common.communication.Command;
import common.communication.CommandParams;
import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameInfo;

public class ClientCommunicator
{
    /**
     * Singleton object to access the ClientCommunicator
     */
    private static ClientCommunicator SINGLETON = null;

    public static ClientCommunicator getSingleton()
    {
        if (SINGLETON == null || !SINGLETON.socket.isConnected() || SINGLETON.socket.isClosed())
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

    public static void setSERVER_HOST(String ip)
    {
        SERVER_HOST = ip;
    }

    private Signal getSignalFromServer()
    {
        return signalFromServer;
    }

    private void setSignalFromServer(Signal signalFromServer)
    {
        this.signalFromServer = signalFromServer;
    }

    /**
     * Initialize a ClientCommunicator object. Create a socket to communicate with the server.
     * This socket will stay open throughout the program; therefore, the ClientCommunicator object
     * must stay relevant throughout a client's entire game.
     */
    private ClientCommunicator()
    {
        try
        {
            socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_HOST, 8080), 5000);
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
                    while (keepRunning)
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
                                    @SuppressWarnings("unchecked") List<GameInfo> infoList =
                                            (List<GameInfo>) signal.getObject();
                                    ClientFacade.getSINGLETON().updateGameList(infoList);
                                } else if (signal.getSignalType() == SignalType.START_GAME)
                                {
                                    //c.startGame(((ServerGameData)signal.getObject()).getId());
                                } else
                                {
                                    System.out.println("Signal received and set on client side: " +
                                                       signal.getSignalType() + " -> " +
                                                       signal.getObject());
                                    setSignalFromServer(signal);
                                }
                            } else if (received instanceof CommandParams)
                            {
                                System.out.println("CommandParams received on client side: " +
                                                   ((CommandParams) received).getMethodName());
                                CommandParams params = (CommandParams) received;
                                Command command = new Command(params, ClientFacade.class.getName());
                                Signal result = (Signal) command.execute();
                                server.write(result); // push(result);
                                System.out.println(
                                        "Signal sent to server: " + result.getSignalType());
                            }

                        } catch (InterruptedException e)
                        {
                            System.out.println(
                                    "InterruptedException when receiving data from server: " + e);
                        }
                    }
                }
            };

            receiver.setDaemon(true);
        } catch (SocketTimeoutException e)
        {
            System.out.print("SocketTimeoutException: No Server found at: " + SERVER_HOST);
        } catch (IOException e)
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
                    while (true)
                    {
                        try
                        {
                            Object object = inputStream.readObject();
                            messages.put(object);
                        } catch (IOException e)
                        {
                            System.out.println("IOException in read Thread: " + e);
                            e.printStackTrace();
                            // there's ever a problem with Server, stop listening
                            closeSocket();
                            break;

                        } catch (ClassNotFoundException e)
                        {
                            System.out.println("ClassNotFoundException in read Thread: " + e);
                            e.printStackTrace();
                        } catch (InterruptedException e)
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
         *
         * @param object The object to be sent.
         */
        private void write(Object object)
        {
            try
            {
                outputStream.writeObject(object);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e)
            {
                System.out.println("IOException when writing object to Server: " + e);
            }
        }
    }

    /**
     * Sends an object from the client to the server.
     *
     * @param object The object to be sent to the server.
     * @return Return a result object from the server. May or may not be an error object.
     * @throws IOException Can throw an IOException if there is an issue with the input/output streams.
     */
    public synchronized Object send(Object object) throws IOException
    {
        if (socket.isConnected())
        {
            setSignalFromServer(null);
            if (server.read.getState() == Thread.State.NEW)
            {
                server.read.start();
                receiver.start();
            }
            Signal result = null;
            server.write(object);
            while (result == null)
            {
                result = getSignalFromServer();
            }
            setSignalFromServer(null);
            return result;
        } else
        {
            return new Signal(SignalType.ERROR, "Not connected to server.");
        }
    }

    /**
     * This function "pushes" or sends Objects from the Server to the Client.
     *
     * @param object The object to be sent to the Client.
     */
    public synchronized void push(Object object)
    {
        server.write(object);
    }

    /**
     * Closes the socket held by the ClientCommunicator.
     */
    private void closeSocket()
    {
        try
        {
            socket.close();
        } catch (IOException e)
        {
            System.out.println("Error closing clientSocket" + e);
            e.printStackTrace();
        }
    }
}
