package Responses;

import Models.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndGameResponse {
    @Getter @Setter private Player winner;
    @Getter @Setter private Player loser;

    public EndGameResponse() {}

}
