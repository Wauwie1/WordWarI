package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LetterTyped {
    @Getter @Setter private String letter;
    @Getter @Setter private Player player;
    @Getter @Setter private Player playerOpponent;
    @Getter @Setter private String lobbyId;

    public LetterTyped() {

    }

}
