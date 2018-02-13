package cs340.TicketClient.Game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import common.DataModels.GameInfo;
import cs340.TicketClient.Lobby.GameListAdapter;
import cs340.TicketClient.R;

public class GameActivity extends AppCompatActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    // TODO: Make a "Game Started" Toast
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_game);

  }

}
