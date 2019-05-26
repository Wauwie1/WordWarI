package com.wordwargroup.wordwarserver.WS;

import Actions.ClientToServer;
import Actions.ServerToClient;
import Models.Player;
import Requests.FindMatchRequest;
import com.wordwargroup.wordwarserver.REST.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final GameServer server = new GameServer();
    @MessageMapping("/find")
    @SendTo("/topic/findmatch")
    public ServerToClient findmatch(FindMatchRequest message) throws Exception {
        Player player = new Player(message.getUser(), 100);
        ServerLobby lobby = server.findMockMatch(player);
        if(lobby.isFull()){
            System.out.println("Opponent found");
            return ServerToClient.GAME_FOUND;
        }else {
            System.out.println("Searching...");
            return ServerToClient.SEARCHING;
        }
        // Thread.sleep(1000); // simulated delay

    }
}
