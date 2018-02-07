package cs340.TicketClient.Lobby;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.Game;
import cs340.TicketClient.R;
import cs340.TicketClient.common.DataModels.Game;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyActivity extends AppCompatActivity {
    private EditText    mSearchGameText;
    private ImageView   mClearSearch;
    private RecyclerView mGameList;
    private GameListAdapter mGameListAdapter;

    private LobbyPresenter presenter = new LobbyPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lobby);

        mSearchGameText = (EditText) this.findViewById(R.id.SearchText);
        mClearSearch = (ImageView) this.findViewById(R.id.ClearSearch);
        mGameList = (RecyclerView) this.findViewById(R.id.GameList);
        mSearchGameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                ((GameListAdapter)mGameListAdapter).clear();
                if(charSequence.length()>0)
                {
                    //Call Lobby Model Search Function here
//                    ArrayList<GameInfo>
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        })

//        adapter = new GameListAdapter(this, R.layout.game_list_item, g);
//        list.setAdapter(adapter);
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //TODO attempt to join the game that was clicked
//            }
//        });
//
//        //updates the list of shown games when the filter box loses focus
//        filter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                List<Game> filteredList = presenter.getFilteredGames(filter.getText().toString());
//                updateGameList(filteredList);
//            }
//        });
    }

//    public void updateGameList(List<Game> games){
//        adapter.openGames = games;
//        adapter.notifyDataSetChanged();
//    }

//    public String getFilter(){
//        return this.filter.getText().toString();
//    }
    public static void updateGameList(List<Game> games) {

    }
    /**
     * The List adapter that handles formatting the Game objects in the ListView for display
     */
}
