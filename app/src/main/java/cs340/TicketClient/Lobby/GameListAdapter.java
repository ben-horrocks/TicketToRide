package cs340.TicketClient.Lobby;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import java.util.*;

import common.DataModels.Game;
import common.DataModels.GameInfo;
import cs340.TicketClient.R;

/**
 * Created by Ben_D on 2/6/2018.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.viewHolder>
{
  private List<GameInfo> games = new ArrayList<>();

  public GameListAdapter()
  {

  }

  @Override
  public GameListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
    viewHolder vh = new viewHolder((LinearLayout) v);
    return vh;
  }

  @Override
  public void onBindViewHolder(viewHolder holder, int position)
  {
    GameInfo game = games.get(position);
    holder.GameName.setText(game.getName());
    holder.HostPlayerName.setText(game.getCreatorName());
    holder.PlayerCount.setText(game.getPlayerCount() + '/' + '5');

  }

  @Override
  public int getItemCount()
  {
    return games.size();
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView)
  {
    super.onAttachedToRecyclerView(recyclerView);
  }

  public void addGames(List<GameInfo> newgames) {
    games.addAll(newgames);
    notifyDataSetChanged();
  }

  public void clear()
  {
    games.clear();
    notifyDataSetChanged();
  }

  public class viewHolder extends RecyclerView.ViewHolder
  {
    public TextView GameName;
    public TextView HostPlayerName;
    public TextView PlayerCount;
    public LinearLayout mButton;
    String id;
    public viewHolder(View itemView)
    {
      super(itemView);
      GameName = (TextView) itemView.findViewById(R.id.game_name);
      HostPlayerName = (TextView) itemView.findViewById(R.id.host_player_name);
      PlayerCount = (TextView) itemView.findViewById(R.id.player_count);
      mButton = (LinearLayout) itemView.findViewById(R.id.gameButton);
      mButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
          //join game here
        }
      });
    }
  }
}
