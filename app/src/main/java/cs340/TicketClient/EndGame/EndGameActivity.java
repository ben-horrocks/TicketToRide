package cs340.TicketClient.EndGame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;

import java.util.List;

import common.player_info.Player;
import cs340.TicketClient.R;

public class EndGameActivity extends AppCompatActivity
{
    private RecyclerView mPlayerList;
    private TextView mWinnername;
    private EndPlayerAdapter mPlayerListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mReturnToLobbyButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_end_game);
        mPlayerList = this.findViewById(R.id.end_game_players);
        mLayoutManager = new LinearLayoutManager(this);
        mPlayerList.setLayoutManager(mLayoutManager);
        mPlayerListAdapter = new EndPlayerAdapter();
        mPlayerList.setAdapter(mPlayerListAdapter);
        mReturnToLobbyButton = findViewById(R.id.return_to_lobby_button);
        //get players somehow
        List<Player> players;
        Bundle extras = this.getIntent().getExtras();
        if(extras != null) {
            players = (List<Player>) extras.get("players");
            mPlayerListAdapter.addPlayers(players);
        } else {
            //log that we didn't get the data we need
            Toast.makeText(this, "CANNOT GET PLAYERS", Toast.LENGTH_LONG).show();
        }

        mReturnToLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //transfer to lobby
            }
        });

    }
}
