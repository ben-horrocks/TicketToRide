package cs340.TicketClient.Communicator;

import java.io.IOException;

import common.CommandParams;

/**
 * Created by Vibro on 2/26/2018.
 *
 * Automatically adds the following to a server running on an ip address passed in on the command line
 * Users:
 *      Tester1: Username "Tester1", Password "test", ScreenName "Tester1"
 *      Tester2: Username "Tester2", Password "test", ScreenName "Tester2"
 *      Tester3: Username "Tester3", Password "test", ScreenName "Tester3"
 *      Tester4: Username "Tester4", Password "test", ScreenName "Tester4"
 *      Tester5: Username "Tester5", Password "test", ScreenName "Tester5"
 * Games:
 *      Game1 (created by Tester1)
 *      Game2 (created by Tester1)
 *      Game3 (created by Tester2)
 *
 * To set the IP Address for the program to use. (Assuming Android Studio)
 *      1) Go to Run->Edit Configurations
 *      2) Under "Application" see if there is a "Populator" profile. If not create one.
 *          a) Set "Main Class" to: "cs340.TicketClient.Communicator.Populator"
 *          b) Set "Use classpath of module" to "app"
 *      3) In the "Program Arguments" box type the IP Address that you want to use (127.0.0.1 is localhost)
 *      4) Hit "Apply" and then "OK" to exit.
 */

public class Populator {
    public static void main(String[] args){
        String ipAddress = args[0];
        CommandParams params = new CommandParams( "populate" ,new String[0], new Object[0]);
        ClientCommunicator.setSERVER_HOST(ipAddress);
        try {
            ClientCommunicator.getSingleton().send(params);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
