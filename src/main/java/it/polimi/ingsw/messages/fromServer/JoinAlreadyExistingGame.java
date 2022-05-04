package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInfo;

public class JoinAlreadyExistingGame implements Message {

    private final GameInfo gameInfo;


    public JoinAlreadyExistingGame(int getGameID, int numOfWaitingPlayers, int numOfTotalPlayers, boolean isExpertGame){
        gameInfo = new GameInfo(getGameID,numOfWaitingPlayers,numOfTotalPlayers,isExpertGame);
    }

    public GameInfo getGameInfo(){
        return gameInfo;
    }

}