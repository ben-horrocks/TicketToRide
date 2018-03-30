package cs340.TicketClient.card_fragments.deck_fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import common.cards.*;
import common.communication.Signal;
import common.player_info.Player;
import common.request.DrawDeckRequest;
import common.request.DrawFaceUpRequest;
import cs340.TicketClient.R;
import cs340.TicketClient.async_task.TurnEndedTask;
import cs340.TicketClient.communicator.ServerProxy;
import cs340.TicketClient.game.GameActivity;
import cs340.TicketClient.game.GameModel;

import static common.communication.SignalType.OK;

public class DeckFragmentPresenter
{

    private DeckFragment fragment;
    private GameActivity activity;
    private GameModel model;
    private static DeckFragmentPresenter singleton;

    public static DeckFragmentPresenter getInstance()
    {
        if(singleton == null)
        {
            singleton = new DeckFragmentPresenter();
        }
        return singleton;
    }

    private DeckFragmentPresenter()
    {
    }

    public void setFragment(DeckFragment fragment)
    {
        this.fragment = fragment;
        this.activity = (GameActivity) fragment.getActivity();
        this.model = GameModel.getInstance();
    }

    ArrayList<Integer> getFaceUpCards()
    {
        ArrayList<TrainCard> faceUp =
                (ArrayList<TrainCard>) GameModel.getInstance().getGameData().getFaceUp();
        ArrayList<Integer> graphics = new ArrayList<>();
        for (TrainCard t : faceUp)
        {
            switch (t.getType())
            {
                case RED:
                    graphics.add(R.drawable.traincard_red);
                    break;
                case BLUE:
                    graphics.add(R.drawable.traincard_blue);
                    break;
                case GRAY:
                    graphics.add(R.drawable.traincard_locomotive);
                    break;
                case PINK:
                    graphics.add(R.drawable.traincard_pink);
                    break;
                case BLACK:
                    graphics.add(R.drawable.traincard_black);
                    break;
                case GREEN:
                    graphics.add(R.drawable.traincard_green);
                    break;
                case WHITE:
                    graphics.add(R.drawable.traincard_white);
                    break;
                case ORANGE:
                    graphics.add(R.drawable.traincard_orange);
                    break;
                case YELLOW:
                    graphics.add(R.drawable.traincard_yellow);
                    break;
                default:
                    System.out.println("No TrainCardImage for that Color");
                    break;
            }
        }
        return graphics;
    }

    TrainCard getCardByID(int id)
    {
        switch (id)
        {
            case R.drawable.traincard_black:
                return new TrainCard(TrainColor.BLACK);
            case R.drawable.traincard_blue:
                return new TrainCard(TrainColor.BLUE);
            case R.drawable.traincard_green:
                return new TrainCard(TrainColor.GREEN);
            case R.drawable.traincard_locomotive:
                return new TrainCard(TrainColor.GRAY);
            case R.drawable.traincard_orange:
                return new TrainCard(TrainColor.ORANGE);
            case R.drawable.traincard_pink:
                return new TrainCard(TrainColor.PINK);
            case R.drawable.traincard_red:
                return new TrainCard(TrainColor.RED);
            case R.drawable.traincard_white:
                return new TrainCard(TrainColor.WHITE);
            case R.drawable.traincard_yellow:
                return new TrainCard(TrainColor.YELLOW);
            default:
                System.err.println("ERROR: No drawable with id " + id);
                return null;
        }
    }

    boolean isMyTurn()
    {
        return model.isMyTurn();
    }

    public void DrawDeck()
    {
        DrawDeckRequest request = new DrawDeckRequest(GameModel.getInstance().getGameID(),
                                                      GameModel.getInstance().getPlayer().getUser()
                                                              .getUsername());
        DrawDeckTask task = new DrawDeckTask(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);

        //change turn info
    }

    private Integer getTrainDrawable(TrainCard card)
    {
        switch (card.getType())
        {
            case RED:
                return R.drawable.traincard_red;
            case BLUE:
                return R.drawable.traincard_blue;
            case GRAY:
                return R.drawable.traincard_locomotive;
            case PINK:
                return R.drawable.traincard_pink;
            case BLACK:
                return R.drawable.traincard_black;
            case GREEN:
                return R.drawable.traincard_green;
            case WHITE:
                return R.drawable.traincard_white;
            case ORANGE:
                return R.drawable.traincard_orange;
            case YELLOW:
                return R.drawable.traincard_yellow;
            default:
                System.out.println("No TrainCardImage for that Color");
                return 0;
        }
    }

    public void DrawFaceUp(int cardNumber)
    {
        DrawFaceUpRequest request =
                new DrawFaceUpRequest(model.getGameID(), model.getUserName(), cardNumber);
        DrawFaceUpTask task = new DrawFaceUpTask(cardNumber, this, model);
        task.execute(request);

        //change turn info
    }

    public void replaceTrainCard(int index, TrainCard card)
    {
        int imageID = getTrainDrawable(card);
		int width = 600;
		int height = 400;
		Drawable cardDrawable = activity.getResources().getDrawable(imageID);
		Bitmap bitmap = ((BitmapDrawable) cardDrawable).getBitmap();
		Drawable cardResized = new BitmapDrawable(fragment.getActivity().getResources(),
				Bitmap.createScaledBitmap(bitmap, width, height, true));
        switch (index)
        {
            case 0:
                fragment.tCard1.setImageDrawable(cardResized);
                break;
            case 1:
                fragment.tCard2.setImageDrawable(cardResized);
                break;
            case 2:
                fragment.tCard3.setImageDrawable(cardResized);
                break;
            case 3:
                fragment.tCard4.setImageDrawable(cardResized);
                break;
            case 4:
                fragment.tCard5.setImageDrawable(cardResized);
                break;
            default:
                Log.d("Error", "replaceTrainCard: Bad Index");
                break;
        }
    }

    public void refreshFaceUpCards(final List<TrainCard> cards)
    {
        if(activity != null)
        {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int x = 0; x < cards.size(); x++) {
                        TrainCard card = cards.get(x);
                        DeckFragmentPresenter.getInstance().replaceTrainCard(x, card);
                    }
                }
            });
        }

    }

    static class DrawFaceUpTask extends AsyncTask<DrawFaceUpRequest, Integer, Signal>
    {
        int index;
        DeckFragmentPresenter presenter;
        GameModel model;

        DrawFaceUpTask(int index, DeckFragmentPresenter presenter, GameModel model)
        {
            this.index = index;
            this.presenter = presenter;
            this.model = model;
        }

        @Override
        protected Signal doInBackground(DrawFaceUpRequest... drawFaceUpRequests)
        {
            return ServerProxy.getInstance()
                    .drawFaceUp(drawFaceUpRequests[0].getId(), drawFaceUpRequests[0].getUsername(),
                                drawFaceUpRequests[0].getIndex());
        }

        @Override
        protected void onPostExecute(Signal response)
        {

            super.onPostExecute(response);
            if (response.getSignalType() == OK)
            {
                TrainCard card = (TrainCard) response.getObject();
                model.getPlayer().drewFaceUpCard(card);
                if (!model.getPlayer().getTurnState().canTakeAction()) // TODO: shorten
				{
					TurnEndedTask task = new TurnEndedTask();
					task.execute(model.getGameID(), model.getUserName());
					presenter.activity.getSupportFragmentManager().popBackStack(); // TODO: shorten
				}
            }
            else
            {
                Log.d("ERROR", "onPostExecute: Error signal on draw face up");
            }
        }
    }

    class DrawDeckTask extends AsyncTask<DrawDeckRequest, Integer, Signal>
    {
    	DeckFragmentPresenter presenter;

		DrawDeckTask(DeckFragmentPresenter presenter) { this.presenter = presenter; }

        @Override
        protected Signal doInBackground(DrawDeckRequest... drawDeckRequests)
        {
            return ServerProxy.getInstance()
                    .drawDeck(drawDeckRequests[0].getId(), drawDeckRequests[0].getUsername());
        }

        @Override
        protected void onPostExecute(Signal signal)
        {
        	GameModel model = GameModel.getInstance();
            super.onPostExecute(signal);
            if (signal.getSignalType() == OK)
            {
                TrainCard card = (TrainCard) signal.getObject();
                model.getPlayer().drewDeckCard(card);
				if (!model.getPlayer().getTurnState().canTakeAction()) // TODO: shorten
				{
					TurnEndedTask task = new TurnEndedTask();
					task.execute(model.getGameID(), model.getUserName());
					presenter.activity.getSupportFragmentManager().popBackStack(); // TODO: shorten
				}

            } else
            {
                Log.d("ERROR", "onPostExecute: Error Signal on draw face up");
            }
        }
    }
}
