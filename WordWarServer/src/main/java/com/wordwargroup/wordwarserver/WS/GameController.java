package com.wordwargroup.wordwarserver.WS;

import Actions.ServerToClient;
import Models.Player;
import Models.User;
import Requests.FindMatchData;
import Requests.Request;
import Responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final GameServer server = new GameServer();
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

        // Thread.sleep(1000); // simulated delay

    }

    @MessageMapping("/play/{id}")
    @SendTo("/topic/game/{id}")
    public Response game(Request message, @DestinationVariable String id) throws Exception {

        switch (message.getAction()) {
            case LETTER_TYPED:

                String letter =  mapper.convertValue(message.getData(), String.class) ;
                System.out.println(letter + " : " + id);
                return null;
            default:
                return null;
        }

        // Thread.sleep(1000); // simulated delay

    }
}
