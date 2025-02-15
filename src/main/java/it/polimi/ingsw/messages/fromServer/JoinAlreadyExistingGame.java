package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.GameInfo;

/**
 * Message sent by the server communicating the join of an already created game.
 */

public class JoinAlreadyExistingGame implements FromServerMessage {

    private final GameInfo gameInfo;


    public JoinAlreadyExistingGame(int getGameID, int numOfWaitingPlayers, int numOfTotalPlayers, boolean isExpertGame){
        gameInfo = new GameInfo(getGameID,numOfWaitingPlayers,numOfTotalPlayers,isExpertGame);
    }

    public GameInfo getGameInfo(){
        return gameInfo;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}