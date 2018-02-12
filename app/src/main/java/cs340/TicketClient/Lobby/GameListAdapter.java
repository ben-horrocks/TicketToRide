/**
 * GameListAdapter.java
 * Author: Ben Horrocks
 * Date of Last Commit: 11 February, 2018
 * Notes: Need to thoroughly test
 */

package cs340.TicketClient.Lobby;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.GameID;
import common.DataModels.GameInfo;
import cs340.TicketClient.ASyncTask.JoinGameTask;
import cs340.TicketClient.ASyncTask.StartGameTask;
import cs340.TicketClient.R;

/**
 * GameListAdapter
 * Abstract: Adapter to dynamically filter through the GameList
 *
 * @domain games  List<GameInfo> The list of games being displayed.
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
    View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.game_list_item, parent, false);
    viewHolder vh = new viewHolder((LinearLayout) v);
    return vh;
  }

  @Override
  public void onBindViewHolder(viewHolder holder, int position)
  {
    GameInfo game = games.get(position);
    holder.isMyGame = game.getCreatorName().equals(
            LobbyPresenter.getInstance().getModel().getPlayer().getName().getName());

    //Implement HasJoinedGame here
    holder.GameName.setText(
            (holder.isMyGame ? "Start" : (holder.hasJoinedGame ? "Joined" : "Join")) +
            game.getName());
    holder.HostPlayerName.setText(game.getCreatorName());
    holder.PlayerCount.setText(game.getPlayerCount() + '/' + '5');
    holder.id = game.getID();

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

  /**
   * Abstract: Function to add a collection of games to the list.
   *
   * @pre games is not null, newGames.size >0
   * @post games will have the new list of games added to it, games.size += newgames
   */
  public void addGames(List<GameInfo> newgames)
  {
    games.addAll(newgames);
    notifyDataSetChanged();
  }

  /**
   * Abstract: Function to clear all games from the list.
   *
   * @pre games is not null
   * @post games.size == 0
   */
  public void clear()
  {
    games.clear();
    notifyDataSetChanged();
  }

  /**
   * ViewHolder
   * Abstract: view to dynamically display games
   *
   * @domain GameName TextView      name of the game being displayed
   * HostPlayerName   TextView      name of the host of the game
   * PlayerCount      TextView      number of players
   * mButton          LinearLayout  Layout of the whole thing - used to set listener
   * id               GameID        id of the game being displayed
   */
  public class viewHolder extends RecyclerView.ViewHolder
  {
    public TextView GameName;
    public TextView HostPlayerName;
    public TextView PlayerCount;
    public LinearLayout mButton;
    public GameID id;
    public Boolean isMyGame;
    public Boolean hasJoinedGame;

    public viewHolder(View itemView)
    {
      //VIEW BINDING
      super(itemView);
      GameName = (TextView) itemView.findViewById(R.id.game_name);
      HostPlayerName = (TextView) itemView.findViewById(R.id.host_player_name);
      PlayerCount = (TextView) itemView.findViewById(R.id.player_count);
      mButton = (LinearLayout) itemView.findViewById(R.id.gameButton);
      //END VIEW BINDING

      //LISTENERS
      if (isMyGame)
      {
        mButton.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            GameID id = LobbyPresenter.getInstance().getJoinedGameID();
            LobbyPresenter.getInstance().startGame(id);
          }
        });

      } else if (!hasJoinedGame)
      {
        mButton.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            LobbyPresenter.getInstance().joinGame(id);
          }
        });
      } else
      {
        //implement remove thyself from game here.
      }
      //END LISTENERS
    }
  }
}
