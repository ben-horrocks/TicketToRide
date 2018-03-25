package cs340.TicketClient.lobby;

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

import java.util.ArrayList;
import java.util.List;

import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.StartGamePacket;
import common.game_data.GameInfo;
import common.player_info.User;
import cs340.TicketClient.communicator.ServerProxy;
import cs340.TicketClient.game.GameActivity;
import cs340.TicketClient.R;
import cs340.TicketClient.game.GameModel;

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
    private RecyclerView mRunningGameList;
    private GameListAdapter mGameListAdapter;
    private RunningGameListAdapter mRunningGameListAdapter;
    private Button mNewGameButton;
    private LobbyPresenter presenter = LobbyPresenter.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lobby);

        //Initialize Lobby Presenter Singleton with reference to this activity for callbacks.
        LobbyPresenter.setActivity(this);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null)
        {
            User user = (User) extras.get("user");
            presenter.setUser(user);

        }
        //VIEW BINDING
        mSearchGameText = (EditText) this.findViewById(R.id.SearchText);
        mClearSearch = (ImageView) this.findViewById(R.id.ClearSearch);

        mGameList = (RecyclerView) this.findViewById(R.id.OpenGameList);
		mGameList.setLayoutManager(new LinearLayoutManager(this));
		mGameListAdapter = new GameListAdapter();
		mGameList.setAdapter(mGameListAdapter);

        mRunningGameList = (RecyclerView) this.findViewById(R.id.YourGameList);
        mRunningGameList.setLayoutManager(new LinearLayoutManager(this));
        mRunningGameListAdapter = new RunningGameListAdapter();
        mRunningGameList.setAdapter(mRunningGameListAdapter);

        Signal signal = ServerProxy.getInstance().getAvailableGameInfo(GameModel.getInstance().getUserName());
        if (signal.getSignalType().equals(SignalType.OK) && signal.getObject() instanceof List)
		{
			// Hope the list is of GameInfo
			@SuppressWarnings("unchecked")
			List<GameInfo> games = (List<GameInfo>) signal.getObject();
			ArrayList<GameInfo> runningGames = new ArrayList<>();
			ArrayList<GameInfo> openGames = new ArrayList<>();
			for (int i = 0; i < games.size(); i++)
			{
				GameInfo gameInfo = games.get(i);
				if (gameInfo.hasUser(presenter.getUser().getUsername()))
				{
					runningGames.add(gameInfo);
				}
				else
				{
					openGames.add(gameInfo);
				}
			}
			mGameListAdapter.setGames(openGames);
			mRunningGameListAdapter.setGames(runningGames);
		}

        mNewGameButton = findViewById(R.id.newGameButton);
        //END VIEW BINDING

        //LISTENERS
        mSearchGameText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                mGameListAdapter.clear();
                if (charSequence.length() > 0)
                {
                    List<GameInfo> games = presenter.searchGames(charSequence.toString());
                    mGameListAdapter.setGames(games);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mClearSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) { mSearchGameText.setText(""); }
        });
        mNewGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CreateGameDialog dialog = new CreateGameDialog();
                dialog.show(getFragmentManager(), "New ServerGameData");
            }
        });
        //END LISTENERS
    }

    @Override
	public void onResume()
	{
		super.onResume();
		Signal signal = ServerProxy.getInstance().getAvailableGameInfo(GameModel.getInstance().getUserName());
		if (signal.getSignalType().equals(SignalType.OK) && signal.getObject() instanceof List)
		{
			// Hope the list is of GameInfo
			@SuppressWarnings("unchecked")
			List<GameInfo> games = (List<GameInfo>) signal.getObject();
			ArrayList<GameInfo> runningGames = new ArrayList<>();
			ArrayList<GameInfo> openGames = new ArrayList<>();
			for (int i = 0; i < games.size(); i++)
			{
				GameInfo gameInfo = games.get(i);
				if (gameInfo.hasUser(presenter.getUser().getUsername()))
				{
					runningGames.add(gameInfo);
				}
				else
				{
					openGames.add(gameInfo);
				}
			}
			mGameListAdapter.setGames(openGames);
			mRunningGameListAdapter.setGames(runningGames);
		}
	}

    /**
     * Abstract: Callback from presenter to update ServerGameData List.
     *
     * @pre Server has successfully sent new gameList
     * @post The game list will be displayed with the Search filter still applied
     */
    public void updateGameList()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mGameListAdapter.clear();
                List<GameInfo> games = presenter.searchGames(mSearchGameText.getText().toString());
				@SuppressWarnings("unchecked")
				ArrayList<GameInfo> runningGames = new ArrayList<>();
				ArrayList<GameInfo> openGames = new ArrayList<>();
				for (int i = 0; i < games.size(); i++)
				{
					GameInfo gameInfo = games.get(i);
					if (gameInfo.hasUser(presenter.getUser().getUsername()))
					{
						runningGames.add(gameInfo);
					}
					else
					{
						openGames.add(gameInfo);
					}
				}
				mGameListAdapter.setGames(openGames);
				mRunningGameListAdapter.setGames(runningGames);
            }
        });
    }


    /**
     * Abstract: Callback from presenter to start GameActivity
     *
     * @pre User has successfully started a game
     * @post GameActivity will be inflated and started, player info will be stored in "player" extra in intent.
     */
    public void startGame(StartGamePacket packet)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("packet", packet);
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
        presenter.addGame(newGame);
    }
}