package common.DataModels;

import java.io.Serializable;
import java.util.List;

import common.DataModels.GameData.ClientGameData;

public class StartGamePacket implements Serializable{

    private List<Object> initialDestinationCards;
    private List<Object> initialTrainCards;
    private ClientGameData clientGameData;

    public StartGamePacket(List<Object> initialDestinationCards, List<Object> initialTrainCards, ClientGameData clientGameData)
    {
        this.initialDestinationCards = initialDestinationCards;
        this.initialTrainCards = initialTrainCards;
        this.clientGameData = clientGameData;
    }

    public List<Object> getInitialDestinationCards() { return initialDestinationCards; }

    public List<Object> getInitialTrainCards() { return initialTrainCards; }

    public Object getClientGameData() { return clientGameData; }
}
