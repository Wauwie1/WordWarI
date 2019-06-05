package com.wordwargroup.wordwarserver.WS.Logic;

import Actions.ServerToClient;
import Models.Player;
import Responses.EndGameResponse;
import Responses.Response;

public class TypeCharacterLogic {
    private Player player;
    private Player opponent;
    private Response response;
    private ServerLobby lobby;

    public TypeCharacterLogic(Player player, Player opponent, ServerLobby lobby) {
        this.player = player;
        this.opponent = opponent;
        this.lobby = lobby;
    }

    public Response typeCharacter(char letter) {
        response = new Response();

        // Player types character
        player.typeCharacter(letter);
        response.setAction(ServerToClient.LETTER_TYPED);

        if(player.completedWord()) {
            completedWord();
            if(opponent.getLives() <= 0) {
                finishGame();
                return response;
            }
        }

        return response;
    }

    private void completedWord() {
        String  word = lobby.getNextWord(player.getCurrentWord());
        System.out.println(word);
        player.giveNewWord(word);

        opponent.removeLife(10);
        response.setAction(ServerToClient.NEW_WORD);
    }

    private void finishGame() {
        EndGameResponse endGame = new EndGameResponse();
        endGame.setLoser(opponent);
        endGame.setWinner(player);
        response.setAction(ServerToClient.GAME_OVER);
        response.setData(endGame);
        lobby.reset();
    }
}
