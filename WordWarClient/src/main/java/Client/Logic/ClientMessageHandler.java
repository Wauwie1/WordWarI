package Client.Logic;

import Models.LetterTyped;
import Models.Player;
import Responses.EndGameResponse;
import Responses.IResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.simp.stomp.StompSession;

@Log4j
public class ClientMessageHandler {

    // WS
    private ObjectMapper mapper = new ObjectMapper();
    @Setter StompSessionHandler sessionHandler;


    @Setter private ClientLogic logic;


    public void handleMessage(IResponse message) throws IllegalArgumentException {

        switch (message.getAction()){
            case SEARCHING:
                System.out.println("Searching for opponent...");
                return;
            case GAME_FOUND:
                ClientLobby lobby = mapper.convertValue(message.getData(), ClientLobby.class);
                logic.foundGame(lobby);
                return;
            case LETTER_TYPED:
                LetterTyped letterTypedMessage = mapper.convertValue(message.getData(), LetterTyped.class);
                Player player = letterTypedMessage.getPlayer();
                logic.letterTyped(player);
                return;
            case NEW_WORD:
                LetterTyped messageNewWord = mapper.convertValue(message.getData(), LetterTyped.class);
                Player playerNewWord = messageNewWord.getPlayer();
                Player playerOpponentNewWord = messageNewWord.getPlayerOpponent();
                logic.newWord(playerNewWord, playerOpponentNewWord);
                return;
            case GAME_OVER:
                EndGameResponse endGameResponse = mapper.convertValue(message.getData(), EndGameResponse.class);
                Player winner = endGameResponse.getWinner();
                logic.endGame(winner);
                return;
            default:
                log.error("Received unknown action.");
                return;
        }
    }

    public void searchGame() {
        logic.searchGame();
    }


    public void setSession(StompSession session) {
        logic.setSession(session);
    }
}
