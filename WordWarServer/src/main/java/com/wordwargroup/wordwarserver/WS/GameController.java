package com.wordwargroup.wordwarserver.WS;

import Models.User;
import Requests.Request;
import Responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordwargroup.wordwarserver.REST.Repositories.MySQLRepository;
import com.wordwargroup.wordwarserver.WS.Logic.GameServer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final GameServer server = new GameServer(new MySQLRepository());
    private final ObjectMapper mapper = new ObjectMapper();

    @MessageMapping("/find")
    @SendTo("/topic/findmatch")
    public Response findmatch(Request message) throws Exception {

        switch (message.getAction()) {
            case SEARCH_GAME:
                User user =  mapper.convertValue(message.getData(), User.class) ;
                Response response = server.findMockMatch(user);
                return response;
            default:
                return null;
        }
    }

    @MessageMapping("/play/{id}")
    @SendTo("/topic/game/{id}")
    public Response game(Request message, @DestinationVariable String id) throws Exception {

        switch (message.getAction()) {
            case LETTER_TYPED:
                Response responseTyped = server.letterTyped(message, id);
                return responseTyped;
            case END_GAME:
                server.endGame(id);
                break;
            default:
                break;
        }

        return null;
    }
}
