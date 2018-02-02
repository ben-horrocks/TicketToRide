package cs340.TicketClient.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs340.TicketClient.DataModels.Player;
import cs340.TicketClient.Lobby.LobbyActivity;
import cs340.TicketClient.R;

public class LoginActivity extends AppCompatActivity
{
  EditText username;
  EditText password;
  EditText screenname;
  Button login;
  Button register;
  LoginPresenter presenter = new LoginPresenter();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    username = this.findViewById(R.id.username);
    password = this.findViewById(R.id.password);
    screenname = this.findViewById(R.id.screen_name);
    login = this.findViewById(R.id.login);
    register = this.findViewById(R.id.register);
    login.setEnabled(false);
    register.setEnabled(false);

    username.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

        setLogin();
        setRegister();
      }
    });

    password.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

        setLogin();
        setRegister();
      }
    });

    screenname.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        setLogin();
        setRegister();
      }

      @Override
      public void afterTextChanged(Editable editable) {

        setLogin();
        setRegister();
      }
    });


    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String[] playerDetails = {username.getText().toString(),
                password.getText().toString()};
        login.setEnabled(false);
        LoginTask task = new LoginTask();
        task.execute(playerDetails);

      }
    });

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String[] playerDetails = {username.getText().toString(),
                password.getText().toString(),
                screenname.getText().toString()};
        register.setEnabled(false);
        RegisterTask task = new RegisterTask();
        task.execute(playerDetails);

      }
    });

  }

  void setLogin()
  {
    login.setEnabled(loginReady());
  }

  private boolean loginReady()
  {
    return username.getText().toString().length() > 0 && password.getText().toString().length() > 0;
  }

  void setRegister()
  {
    register.setEnabled(registerReady());
  }

  private boolean registerReady()
  {
    return username.getText().toString().length() > 0 && password.getText().toString().length() > 0
            && screenname.getText().toString().length() > 0;
  }

  void gotoLobby(Player player)
  {
    Intent intent = new Intent(this, LobbyActivity.class);
    intent.putExtra("player", player);
    startActivity(intent);
  }

  void failToast(String s)
  {
    Toast.makeText(this, s+" Failed", Toast.LENGTH_SHORT).show();
  }

  void successToast(String s)
  {
    Toast.makeText(this, s+" Succeeded", Toast.LENGTH_SHORT).show();
  }


  public class LoginTask extends AsyncTask<String, Integer, Player>
  {
    @Override
    protected Player doInBackground(String[] arrayLists) {

      String username = arrayLists[0];
      String password = arrayLists[1];
      return presenter.login(username, password);
    }

    @Override
    protected void onPostExecute(Player player) {
      super.onPostExecute(player);
      if(player != null) {
        //gotoLobby(player);
        successToast("Login");
      }
      else
        failToast("Login");
    }
  }

  public class RegisterTask extends AsyncTask<String, Integer, Player>
  {
    @Override
    protected Player doInBackground(String[] arrayLists) {

      String username = arrayLists[0];
      String password = arrayLists[1];
      String screenname =  arrayLists[2];
      return presenter.register(username, password, screenname);
    }

    @Override
    protected void onPostExecute(Player player) {
      super.onPostExecute(player);
      if (player != null) {
        //gotoLobby(player);
        successToast("Register");
      }
      else
        failToast("Register");
    }
  }




}
