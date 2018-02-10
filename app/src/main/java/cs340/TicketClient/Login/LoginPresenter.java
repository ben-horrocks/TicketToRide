package cs340.TicketClient.Login;

import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.Player;
import common.DataModels.Signal;
import cs340.TicketClient.Communicator.ServerProxy;


public class LoginPresenter implements ILoginPresenter
{

    private LoginActivity activity;

    public LoginPresenter(LoginActivity activity)
    {
        this.activity = activity;
    }

    private static final String stringClassName = "java.lang.String";
    private static final String playerClassName = "common.DataModels.Player";

    /**
    * Method to login the player given a username and password
    * @param username the player's username
    * @param password the player's password
    * @return A player object representing the logged in player
     */
    @Override
    public void login(String username, String password) {
        if (LoginModel.getInstance().isValidLogin(username, password)) {
            String[] playerDetails = {username, password};
            //login.setEnabled(false);
            LoginTask task = new LoginTask();
            task.execute(playerDetails);
        }
        else
		{
			Toast.makeText(activity, "Invalid Input", Toast.LENGTH_SHORT).show();
		}
    }

    /**
    * Method to register a new player given the relevant information
    * @param username the player's username (must be unique)
    * @param password the player's password
    * @param screenname the name to be displayed for user
    * @return A player object representing the logged in player
    *
     */
    @Override
    public void register(String username, String password, String screenname) {
        if(LoginModel.getInstance().isValidRegister(username, password, screenname)) {
            String[] playerDetails = {username, password, screenname};
            //register.setEnabled(false);
            RegisterTask task = new RegisterTask();
            task.execute(playerDetails);
        }
        else
            Toast.makeText(activity, "Invalid Input", Toast.LENGTH_SHORT).show();
    }

    public class RegisterTask extends AsyncTask<String, Integer, Signal>
    {
        @Override
        protected Signal doInBackground(String[] arrayLists) {

            String username = arrayLists[0];
            String password = arrayLists[1];
            String screenname = arrayLists[2];
            return ServerProxy.getInstance().register(username, password, screenname);
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
            if (signal != null) {
                String signalObjectName = signal.getObject().getClass().getName();
                switch (signalObjectName)
                {
                    case stringClassName:
                        Toast.makeText(activity, (String)signal.getObject(), Toast.LENGTH_SHORT).show();
                        break;
                    case playerClassName:
                        activity.gotoLobby((Player)signal.getObject());
                        break;
                    default:
                        Toast.makeText(activity, "Failed Register::Unknown Signal", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            else
                Toast.makeText(activity, "Failed Register::Null Signal", Toast.LENGTH_SHORT).show();
        }
    }

    public class LoginTask extends AsyncTask<String, Integer, Signal>
    {
        @Override
        protected Signal doInBackground(String[] arrayLists) {

            String username = arrayLists[0];
            String password = arrayLists[1];
            return ServerProxy.getInstance().login(username, password);
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
            if(signal != null) {
            	String signalObjectName = signal.getObject().getClass().getName();
                switch (signalObjectName)
                {
                    case stringClassName:
                        Toast.makeText(activity, (String)signal.getObject(), Toast.LENGTH_SHORT).show();
                        break;
                    case playerClassName:
                        activity.gotoLobby((Player)signal.getObject());
                        break;
                    default:
                        Toast.makeText(activity, "Failed Login::Unknown Signal", Toast.LENGTH_SHORT).show();
                        break;
                }
                //setLogin();
                //setRegister();
                //gotoLobby(player);
                //successToast("Login");
            }
            else
                Toast.makeText(activity, "Failed Login::Null Signal", Toast.LENGTH_SHORT).show();
        }
    }

}
