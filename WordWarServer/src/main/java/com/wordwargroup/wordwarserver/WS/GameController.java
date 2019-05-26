package com.wordwargroup.wordwarserver.WS;

import Actions.ServerToClient;
import Models.Player;
import Models.User;
import Requests.FindMatchData;
import Requests.Request;
import Responses.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final GameServer server = new GameServer();
    @MessageMapping("/find")
    @SendTo("/topic/findmatch")
    public Response findmatch(Request message) throws Exception {
        switch (message.getAction()) {
            case SEARCH_GAME:
                User user = (User)message.getData();

                return server.findMockMatch(user);
            default:
                return null;
        }

        // Thread.sleep(1000); // simulated delay

    }
}
