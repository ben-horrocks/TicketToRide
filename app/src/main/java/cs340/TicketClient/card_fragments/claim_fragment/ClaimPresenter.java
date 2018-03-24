package cs340.TicketClient.card_fragments.claim_fragment;

import common.cards.HandTrainCards;
import cs340.TicketClient.game.GameModel;

/**
 * Created by jhens on 3/24/2018.
 */

public class ClaimPresenter implements IClaimPresenter {

    private ClaimFragment fragment;
    private GameModel model;

    ClaimPresenter(ClaimFragment fragment)
    {
        this.fragment = fragment;
        model = null;
    }

    @Override
    public HandTrainCards getPlayerTrainCards() {
        return GameModel.getInstance().getPlayer().getHand();
    }

    public void cardsAreGood()
    {
        
    }
}
