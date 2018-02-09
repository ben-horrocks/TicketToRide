package cs340.TicketClient.Lobby;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.GameInfo;
import cs340.TicketClient.R;
import cs340.TicketClient.common.DataModels.Game;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyActivity extends AppCompatActivity {
    private EditText filter;
    private ListView list;
    private GameListAdapter adapter;

    private LobbyPresenter presenter = new LobbyPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lobby);

        filter = this.findViewById(R.id.filter);
        list = this.findViewById(R.id.game_list);

        List<GameInfo> g = presenter.getAllGames();
        adapter = new GameListAdapter(this, R.layout.game_list_item, g);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO attempt to join the game that was clicked
            }
        });

        //updates the list of shown games when the filter box loses focus
        filter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                List<GameInfo> filteredList = presenter.getFilteredGames(filter.getText().toString());
                updateGameList(filteredList);
            }
        });
    }

    public void updateGameList(List<GameInfo> games){
        adapter.openGames = games;
        adapter.notifyDataSetChanged();
    }

    public String getFilter(){
        return this.filter.getText().toString();
    }

    /**
     * The List adapter that handles formatting the Game objects in the ListView for display
     */
    private class GameListAdapter extends ArrayAdapter<GameInfo> {
        private int layout;
        private List<GameInfo> openGames;
        private GameListAdapter(Context context, int resource, List<GameInfo> games) {
            super(context, resource, games);
            openGames = games;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            GameListItem mainItem;
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                GameListItem item = new GameListItem();
                item.gameName = (TextView) convertView.findViewById(R.id.game_name);
                item.hostPlayer = (TextView) convertView.findViewById(R.id.host_player_name);
                item.playerCount = (TextView) convertView.findViewById(R.id.player_count);
                convertView.setTag(item);
            }

            GameInfo g = getItem(position);
            mainItem = (GameListItem) convertView.getTag();
            mainItem.gameName.setText(g.getName());
            mainItem.hostPlayer.setText(g.getCreatorName());
            mainItem.playerCount.setText(Integer.toString(g.getPlayerCount()) + "/5");
            return convertView;
        }
    }

    /** A class that represents an entry on the lobby game list
     * @inv The text in playerCount must always be a String formatted as \"*\\5\"
     */
    private class GameListItem {
        TextView gameName;
        TextView hostPlayer;
        TextView playerCount;
    }

}
