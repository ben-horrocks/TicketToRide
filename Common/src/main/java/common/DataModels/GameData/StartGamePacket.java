package common.DataModels.GameData;

import java.io.Serializable;

import common.DataModels.*;

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
