package common.DataModels.GameData;

import java.io.Serializable;
import java.util.List;

import common.DataModels.DestinationCard;
import common.DataModels.GameData.ClientGameData;
import common.DataModels.TrainCard;
import common.DataModels.Username;

public class StartGamePacket implements Serializable{

    private List<DestinationCard> initialDestinationCards;
    private List<TrainCard> initialTrainCards;
    private ClientGameData clientGameData;

    public StartGamePacket(List<DestinationCard> initialDestinationCards, List<TrainCard> initialTrainCards, ClientGameData clientGameData)
    {
        this.initialDestinationCards = initialDestinationCards;
        this.initialTrainCards = initialTrainCards;
        this.clientGameData = clientGameData;
    }

    public List<DestinationCard> getInitialDestinationCards() { return initialDestinationCards; }

    public ClientGameData getClientGameData() {
        return clientGameData;
    }

    public Username getUser()
    {
        return clientGameData.getPlayer().getUser().getUsername();
    }
    public List<TrainCard> getInitialTrainCards() { return initialTrainCards; }
}
