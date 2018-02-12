/**
 * LobbyActivity.java
 * Author: Ben Horrocks
 * Date of Last Commit: 11 February, 2018
 * Notes: Need to thoroughly test
 */
package cs340.TicketClient.Lobby;

import android.app.DialogFragment;
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
 * Abstract: The View of the lobby, where players will be prompted to join games
 *
 * @domain mSearchGameText  EditText                    Search bar to check lobby for specific games
 * mClearSearch     ImageView                   Button to clear search parameters
 * mGameList        RecyclerView                View to display filtered game lists
 * mGameListAdapter GameListAdapter             adapter to display game lists
 * mLayoutManager   RecyclerView.LayoutManager  Layout Manager for the RecyclerView
 * mNewGameButton   Button                      Button to add new game/Start new game when new game is added
 */
public class LobbyActivity extends AppCompatActivity
        implements ILobbyActivity, CreateGameDialog.CreateGameDialogListener
{
  private EditText mSearchGameText;
  private ImageView mClearSearch;
  private RecyclerView mGameList;
  private GameListAdapter mGameListAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private Button mNewGameButton;

  @Override
  protected void onCreate(final Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_lobby);

    //Initalize Lobby Presenter Singleton with reference to this activity for callbacks.
    LobbyPresenter.setActivity(this);
    LobbyPresenter.getInstance().getModel()
            .setPlayer((Player) this.getIntent().getExtras().get("player"));

    //VIEW BINDING
    mSearchGameText = (EditText) this.findViewById(R.id.SearchText);
    mClearSearch = (ImageView) this.findViewById(R.id.ClearSearch);
    mGameList = (RecyclerView) this.findViewById(R.id.GameList);
    mLayoutManager = new LinearLayoutManager(this);
    mGameList.setLayoutManager(mLayoutManager);
    mGameListAdapter = new GameListAdapter();
    mGameList.setAdapter(mGameListAdapter);
    mNewGameButton = findViewById(R.id.newGameButton);
    //END VIEW BINDING

    //LISTENERS
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
          List<GameInfo> games = LobbyPresenter.getInstance().searchGames(charSequence.toString());
          mGameListAdapter.addGames(games);
        }
      }

      @Override
      public void afterTextChanged(Editable editable)
      {
      }
    });
    mClearSearch.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        mSearchGameText.setText("");
      }
    });
    mNewGameButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        CreateGameDialog dialog = new CreateGameDialog();
        dialog.show(getFragmentManager(), "New Game");
      }
    });
    //END LISTENERS
  }

  /**
   * Abstract: Callback from presenter to update Game List.
   *
   * @pre Server has successfully sent nerw gameList
   * @post The game list will be displayed with the Search filter still applied
   */
  public void updateGameList()
  {
    mGameListAdapter.clear();
    List<GameInfo> games =
            LobbyPresenter.getInstance().searchGames(mSearchGameText.getText().toString());
    mGameListAdapter.addGames(games);
  }


  /**
   * Abstract: Callback from presenter to start GameActivity
   *
   * @pre User has successfully started a game
   * @post GameActivity will be inflated and started, player info will be stored in "player" extra in intent.
   */
  public void startGame()
  {
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra("player", LobbyPresenter.getInstance().getModel().getPlayer());
    startActivity(intent);
  }

  /**
   * Abstract: Callback from presenter to update view when a New game is added.
   *
   * @pre User has successfully added a game
   * @post Add game button will change to start game button instead, new listener will be bound.
   */
  public void gameAdded()
  {
//    mNewGameButton.setText(R.string.start_game);
  }

  /**
   * Abstract: Function to return data from Dialog and start newGameTask to send to server.
   *
   * @pre User has just asked for a new game t be created via the dialog, newGame.length >0
   * @post A new AddGameTask will have been executed to add a new game on the server.
   */
  @Override
  public void onAddGame(DialogFragment frag, String newGame)
  {
    LobbyPresenter.getInstance().addGame(newGame);
  }
}