package common.game_data;

import java.io.Serializable;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.game_data.ClientGameData;
import common.player_info.Username;

public class StartGamePacket implements Serializable
{

    private HandDestinationCards initialDestinationCards;
    private HandTrainCards initialTrainCards;
    private ClientGameData clientGameData;

    public StartGamePacket(HandDestinationCards initialDestinationCards,
                           HandTrainCards initialTrainCards, ClientGameData clientGameData)
    {
        this.initialDestinationCards = initialDestinationCards;
        this.initialTrainCards = initialTrainCards;
        this.clientGameData = clientGameData;
    }

    public HandDestinationCards getInitialDestinationCards()
    {
        return initialDestinationCards;
    }

    public ClientGameData getClientGameData()
    {
        return clientGameData;
    }

    public Username getUser()
    {
        return clientGameData.getPlayer().getUser().getUsername();
    }

    public HandTrainCards getInitialTrainCards()
    {
        return initialTrainCards;
    }
}
