package cs340.TicketClient.end_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;

import java.util.List;

import common.game_data.EndGame;
import common.player_info.Player;
import common.player_info.User;
import cs340.TicketClient.R;
import cs340.TicketClient.lobby.LobbyActivity;

public class EndGameActivity extends AppCompatActivity
{
    private RecyclerView mPlayerList;
    private TextView mWinnername;
    private EndPlayerAdapter mPlayerListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mReturnToLobbyButton;
    private User user;
    private EndGamePresenter presenter = new EndGamePresenter(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.end_game);
        mPlayerList = this.findViewById(R.id.end_game_players);
        mLayoutManager = new LinearLayoutManager(this);
        mPlayerList.setLayoutManager(mLayoutManager);
        mPlayerListAdapter = new EndPlayerAdapter();
        mPlayerList.setAdapter(mPlayerListAdapter);
        mReturnToLobbyButton = findViewById(R.id.return_to_lobby_button);
        EndGame players;
        Bundle extras = this.getIntent().getExtras();
        if(extras != null) {
            players = (EndGame) extras.get("players");
            user = (User) extras.get("user");
            mPlayerListAdapter.addPlayers(players);
        } else {
            //log that we didn't get the data we need
            Toast.makeText(this, "CANNOT GET PLAYERS", Toast.LENGTH_LONG).show();
        }

        mReturnToLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                presenter.returnToLobby(user.getUsername());;
            }
        });

    }

    public void goToLobby()
    {
        Intent intent= new Intent(this, LobbyActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
