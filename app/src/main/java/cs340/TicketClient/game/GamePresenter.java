package cs340.TicketClient.game;

import android.os.AsyncTask;

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
import common.game_data.StartGamePacket;
import common.history.HistoryItem;
import common.map.Edge;
import common.map.EdgeGraph;
import common.request.DestDrawRequest;
import cs340.TicketClient.communicator.ServerProxy;

public class GamePresenter
{
    private static final String TAG = "GAME";
    private GameActivity activity;
    private GameModel model;

    class InsufficientCardsException extends Exception {}

    GamePresenter(GameActivity gameActivity)
    {
        this.activity = gameActivity;
        model = GameModel.getInstance();
    }

    void fillModel(StartGamePacket packet)
    {
        model.setGameData(packet.getClientGameData());
        model.setInitialDCards(packet.getInitialDestinationCards());
    }

    void startClaimRouteOption()
    {
        //get routes
        EdgeGraph allRoutes = model.getGameData().getGameboard();
        //get player's train cards
        HandTrainCards trainCards = model.getGameData().getPlayer().getHand();
        //get player's destinations cards
        HandDestinationCards destinationCards = model.getGameData().getPlayer().getDestinationCards();

        //Routes which can be claimed
        EdgeGraph claimableRoutes = new EdgeGraph();

        //Routes which can be claimed and are a destination card
        EdgeGraph destinationClaimableRoutes = new EdgeGraph();

        //Figure out which routes the player can claim
        for (Edge edge : allRoutes.getAllEdges())
        {
            if (!edge.isClaimed())
            {
                //check player's train cards to see if can claim
                ArrayList<TrainCard> cardsToUse = new ArrayList<>();
                for (TrainCard card : trainCards.getTrainCards())
                {
                    if (card.getType().equals(edge.getColor()))
                    {
                        cardsToUse.add(card);
                    }
                }
                //If the size of cardsToUse is sufficient for the edge, then this route can be claimed
                if (edge.getLength() == cardsToUse.size())
                {
                    //Check whether the city is a destination card the player has
                    for (DestinationCard destinationCard : destinationCards.getDestinationCards())
                    {
                        boolean city1Check = destinationCard.getCity1().equals(edge.getFirstCity()) || destinationCard.getCity1().equals(edge.getSecondCity());
                        boolean city2Check = destinationCard.getCity2().equals(edge.getFirstCity()) || destinationCard.getCity2().equals(edge.getSecondCity());
                        if (city1Check && city2Check)
                        {
                            destinationClaimableRoutes.addEdge(edge);
                        } else {
                            claimableRoutes.addEdge(edge);
                        }
                    }
                }
            }
        }
    }

    void claimRoute(TrainColor color, int number) throws InsufficientCardsException
    {
        // TODO: implement

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
            //GameModel.getInstance().clearDCards();
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
}
