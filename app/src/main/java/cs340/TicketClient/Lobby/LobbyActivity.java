/**
 * LobbyActivity.java
 * Author: Ben Horrocks
 * Date of Last Commit: 7 February, 2018
 * Notes: Need to finish documentation
 */
package cs340.TicketClient.Lobby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import java.util.List;

import common.DataModels.*;
import cs340.TicketClient.ASyncTask.AddGameTask;
import cs340.TicketClient.ASyncTask.StartGameTask;
import cs340.TicketClient.Game.GameActivity;
import cs340.TicketClient.R;

/**
 * LobbyActivity
 * Abstract: The View of our lobby
 * @domain
 * mSearchGameText ( Search bar to check lobby for specific games
 * mClearSeatch
 *
 */
public class LobbyActivity extends AppCompatActivity implements ILobbyActivity
{
  private EditText mSearchGameText;
  private ImageView mClearSearch;
  private RecyclerView mGameList;
  private GameListAdapter mGameListAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private Button mNewGameButton;
  private Player player;
  @Override
  protected void onCreate(final Bundle savedInstanceState)
  {
    player = (Player) this.getIntent().getExtras().get("player");
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_lobby);

    mSearchGameText = (EditText) this.findViewById(R.id.SearchText);
    mClearSearch = (ImageView) this.findViewById(R.id.ClearSearch);
    mGameList = (RecyclerView) this.findViewById(R.id.GameList);
    mLayoutManager = new LinearLayoutManager(this);
    mGameList.setLayoutManager(mLayoutManager);
    mGameListAdapter = new GameListAdapter();
    mGameList.setAdapter(mGameListAdapter);
    mSearchGameText.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {
        mGameListAdapter.clear();
        if (charSequence.length() > 0)
        {
          //Call Lobby Model Search Function here
          List<GameInfo> games = LobbyPresenter.getInstance().searchGames(charSequence.toString());
          mGameListAdapter.addGames(games);
        }
      }

      @Override
      public void afterTextChanged(Editable editable)
      {
      }
    });
    mNewGameButton = findViewById(R.id.newGameButton);
    mNewGameButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {
        CreateGameDialog dialog = new CreateGameDialog();
        dialog.getArguments().putSerializable("player", player);
        dialog.show(getFragmentManager(), "New Game");
      }
    });
  }
  public void updateGameList(List<GameInfo> newgames)
  {
    mGameListAdapter.clear();
    mGameListAdapter.addGames(newgames);
  }

  public void startGame()
  {
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra("player", player);
    startActivity(intent);
  }

  public void gameAdded()
  {
    mNewGameButton.setText(R.string.start_game);
    mNewGameButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {
        GameID id = LobbyPresenter.getInstance().getJoinedGameID();
        StartGameTask task = new StartGameTask(getBaseContext());
        task.execute(id);
      }
    });
  }

}
