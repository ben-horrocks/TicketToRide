package cs340.TicketClient.game;

import android.app.*;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import common.cards.DestinationCard;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.chat.ChatItem;
import common.cards.HandDestinationCards;
import common.cards.TrainColor;
import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.*;
import common.history.HistoryItem;
import common.player_info.Player;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.Username;
import common.request.ClaimRequest;
import common.request.DestDrawRequest;
import cs340.TicketClient.R;
import cs340.TicketClient.async_task.TurnEndedTask;
//import cs340.TicketClient.card_fragments.claim_fragment.ClaimFragment;
//import cs340.TicketClient.card_fragments.claim_fragment.ClaimPresenter;
import cs340.TicketClient.communicator.ServerProxy;

public class GamePresenter
{
    private static final String TAG = "GAME";
    private GameActivity activity;
    private GameModel model;

    static class InsufficientCardsException extends Exception {}

    GamePresenter(GameActivity gameActivity)
    {
        this.activity = gameActivity;
        model = GameModel.getInstance();
        GameModel.getInstance().setPresenter(this);
    }

    void fillModel(StartGamePacket packet)
    {
        model.setGameData(packet.getClientGameData());
        model.setInitialDCards(packet.getInitialDestinationCards());
    }

    void startClaimRouteOption()
    {
        final Edge selectedEdge = model.getSelectedEdge();
        if (selectedEdge != null)
        {
            if (!selectedEdge.isClaimed())
            {
                if(selectedEdge.getColor() == TrainColor.GRAY)
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle(R.string.claimRouteTitle).setItems(R.array.trainColors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            TrainColor color;
                            switch(i)
                            {
                                case 0:
                                    color = TrainColor.PINK;
                                    break;
                                case 1:
                                    color = TrainColor.WHITE;
                                    break;
                                case 2:
                                    color = TrainColor.BLUE;
                                    break;
                                case 3:
                                    color = TrainColor.YELLOW;
                                    break;
                                case 4:
                                    color = TrainColor.ORANGE;
                                    break;
                                case 5:
                                    color = TrainColor.BLACK;
                                    break;
                                case 6:
                                    color = TrainColor.RED;
                                    break;
                                case 7:
                                    color = TrainColor.GREEN;
                                    break;
                                default:
                                    Toast.makeText(activity, "BAD COLOR SELECTION", Toast.LENGTH_SHORT).show();
                                    color = TrainColor.LIGHT_GRAY;
                            }
                            try
                            {
                                claimRoute(selectedEdge, color);
                            } catch (InsufficientCardsException e)
                            {
                                Toast.makeText(activity, "Not Enough Cards", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                    //make dialog to get color user wants to use
                } else
                {
                    try
                    {
                        claimRoute(selectedEdge, selectedEdge.getColor());
                    } catch (InsufficientCardsException e)
                    {
                        Toast.makeText(activity, "Not Enough Cards", Toast.LENGTH_SHORT).show();
                    }
                }
//                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
//                android.support.v4.app.Fragment fragment = fm.findFragmentByTag(ClaimFragment.class.getSimpleName());
//                if (fragment == null)
//				{
//					fragment = new ClaimFragment();
//					fm.beginTransaction().add(R.id.fragment_map, fragment, ClaimFragment.class.getSimpleName())
//							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//							.addToBackStack(ClaimFragment.class.getSimpleName()).commit();
//				}
            }
            else {
                String message = "This route is already claimed by another player.";
                Toast.makeText(activity.getBaseContext(), message, Toast.LENGTH_SHORT).show();
                //todo: reset player state
            }
        } else {
            String message = "Please select an edge first before claiming the route.";
            Toast.makeText(activity.getBaseContext(), message, Toast.LENGTH_SHORT).show();
            //todo: reset player state
        }
    }

    void claimRoute(Edge selectedEdge, TrainColor selectedColor) throws InsufficientCardsException
    {
        List<TrainCard> cards = model.canClaimRoute(selectedColor, selectedEdge.getLength());
        selectedEdge.setOwner(model.getPlayer());
        model.getPlayer().getPieces().useTrainPieces(selectedEdge.getLength());
        model.getPlayer().addPoints(selectedEdge.computePointValue());
        ClaimRequest req = new ClaimRequest(model.getGameID(), model.getUserName(), selectedEdge, new HandTrainCards(cards));
        ClaimTask task = new ClaimTask(model);
        task.execute(req);
    }


    boolean playerHasRestrictedAction() { return model.getPlayer().hasRestrictedAction(); }

    void refreshMapFragment(Edge edge) {
        //get info for toast
        String name = edge.getOwner().toString();
        String city1 = edge.getFirstCity().getCityName();
        String city2 = edge.getSecondCity().getCityName();
        StringBuilder sb = new StringBuilder(name + " claimed the route from " + city1 + " to " + city2);

        //refresh the map
        android.support.v4.app.Fragment mapFrag = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        if (mapFrag != null) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(mapFrag);
            ft.attach(mapFrag);
            ft.commit();

            //notify user of claimed route
            //Toast.makeText(mapFrag.getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
        }
        else {
            System.out.println("Could not refresh the map");
        }
    }

    public List<ChatItem> getChatMessages()
    {
        return model.getChatMessages();
    }

    public void setChatMessages(List<ChatItem> chatMessages)
    {
        model.setChatMessages(chatMessages);
    }

    public List<HistoryItem> getPlayHistory()
    {
        return model.getPlayHistory();
    }

    public void setPlayHistory(List<HistoryItem> playHistory)
    {
        model.setPlayHistory(playHistory);
    }

    public HandDestinationCards getDestinationCards()
    {
        if (model.getInitialDCards() != null)
        {
            HandDestinationCards cards = model.getInitialDCards();
            GameModel.getInstance().clearDCards();
            return cards;
        } else
        {
            DestDrawRequest request = new DestDrawRequest(GameModel.getInstance().getGameID(),
                                                          GameModel.getInstance().getPlayer()
                                                                  .getUser().getUsername());
            DrawDestinationTask task = new DrawDestinationTask(activity);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
            return null;
        }
    }

    boolean isMyTurn() { return model.isMyTurn(); }

    void nextTurn() { model.nextTurn(); }

	/**
	 * AsyncTask to draw a destination card from the server.
	 */
	static class DrawDestinationTask extends AsyncTask<DestDrawRequest, Integer, Signal>
    {
        WeakReference<GameActivity> activity;

        DrawDestinationTask(GameActivity activity)
        {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Signal doInBackground(DestDrawRequest... destDrawRequests)
        {
            Signal s = ServerProxy.getInstance().drawDestinationCards(destDrawRequests[0].getId(),
                                                                      destDrawRequests[0].getUser());
            return s;
        }

        @Override
        protected void onPostExecute(Signal signal)
        {
            super.onPostExecute(signal);
            GameActivity gameActivity = activity.get();
            if (signal.getSignalType() == SignalType.OK)
            {
                if (gameActivity != null)
                {
                    gameActivity
                            .startDestinationFragment((HandDestinationCards) signal.getObject());
                } else
                {
                    System.err.println(
                            "ERROR: onPostExecute in DrawDestinationTask - game activity is null");
                }
            } else
            {
                System.out.println(signal.getObject());
            }

        }
    }

    public Activity getGameActivity() {
	    if (activity != null) {
            return activity;
        }
        return null;
    }

    public void makeToast(String s)
    {
        getGameActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Last Turn!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void endGame(EndGame players)
    {
        activity.endGame(players);
    }

    public void sendClaimRequest()
    {

        GameID id = model.getGameID();
        Username user = model.getUserName();
        Edge edge = model.getSelectedEdge();
        HandTrainCards cards = new HandTrainCards(model.getQueuedCards());
        edge.setOwner(model.getPlayer());
        ClaimTask claimTask = new ClaimTask(model);
        ClaimRequest request = new ClaimRequest(id, user, edge, cards);
        claimTask.execute(request);
    }


    class ClaimTask extends AsyncTask<ClaimRequest, Integer, Signal>
    {

        ClaimTask(GameModel model)
        {
        }

        @Override
        protected Signal doInBackground(ClaimRequest... claimRequests) {
            ClaimRequest r = claimRequests[0];
            return ServerProxy.getInstance().playerClaimedEdge(r.getId(), r.getUser(),
                                                               r.getEdge(), r.getCards());
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
            if (signal.getSignalType() == SignalType.OK)
            {
                Edge edge = (Edge) signal.getObject();
                model.getGameData().edgeClaimed(edge, model.getQueuedCards());
                TurnEndedTask task = new TurnEndedTask();
                task.execute(GameModel.getInstance().getGameID(), GameModel.getInstance().getUserName());
                //update map
                refreshMapFragment(edge);
            }
            else
            {
                //MAKE CANT CLAIM TOAST
            }
        }
    }

}
