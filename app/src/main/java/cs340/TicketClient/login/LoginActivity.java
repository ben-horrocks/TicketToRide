package cs340.TicketClient.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import common.player_info.User;
import cs340.TicketClient.communicator.ClientCommunicator;
import cs340.TicketClient.lobby.LobbyActivity;
import cs340.TicketClient.R;

public class LoginActivity extends AppCompatActivity implements ILoginActivity
{
    /**
     * Field for players username
     */
    EditText username;
    /**
     * Field for player's password
     */
    EditText password;
    /**
     * ip address for server
     */
    EditText ip;
    /**
     * Button for logging in
     */
    Button login;
    /**
     * Button for registering
     */
    Button register;
    /**
     * presenter used for login and register processes
     */
    LoginPresenter presenter = new LoginPresenter(this);


    /**
     * Method called when activity starts. Sets up three text fields and impliments their addTextChangedListers.
     * Additionally it sets up two buttons and their onClickListeners
     *
     * @param savedInstanceState A bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = this.findViewById(R.id.username);
        password = this.findViewById(R.id.password);
        ip = this.findViewById(R.id.ipAddress);
        login = this.findViewById(R.id.login);
        register = this.findViewById(R.id.register);
        login.setEnabled(false);
        register.setEnabled(false);

        username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                setLogin();
                setRegister();
            }
        });

        ip.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                setLogin();
                setRegister();
            }
        });

        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                setLogin();
                setRegister();
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClientCommunicator.setSERVER_HOST(ip.getText().toString());
                presenter.login(username.getText().toString(), password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClientCommunicator.setSERVER_HOST(ip.getText().toString());
                presenter.register(username.getText().toString(), password.getText().toString());

            }
        });

    }

    /**
     * Sets login button to enabled if it meets the requirements of loginReady()
     */
    @Override
    public void setLogin()
    {
        login.setEnabled(loginReady());
    }

    /**
     * Checks if username and password text fields are filled in
     *
     * @return true if both username and password are filled, false otherwise
     */
    @Override
    public boolean loginReady()
    {
        return username.getText().toString().length() > 0 &&
               password.getText().toString().length() > 0 && ip.getText().toString().length() > 0;
    }

    /**
     * Sets register button to enabled if it meets the requirements of registerReady()
     */
    @Override
    public void setRegister()
    {
        register.setEnabled(registerReady());
    }

    /**
     * Checks if username, password, and screenname fields are filled in
     *
     * @return true if username, password, and screenname are filled, false otherwise
     */
    @Override
    public boolean registerReady()
    {
        return username.getText().toString().length() > 0 &&
               password.getText().toString().length() > 0 &&
				ip.getText().toString().length() > 0;
    }

    /**
     * Creates intent and starts lobby activity for the user
     *
     * @param user the user that just logged in or registered
     */
    @Override
    public void gotoLobby(User user)
    {
        Intent intent = new Intent(this, LobbyActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
