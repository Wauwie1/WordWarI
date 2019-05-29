package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    @Getter @Setter private User user;
    @Getter @Setter private int lives;
    @Getter private String currentWord;
    @Getter @Setter private char[] typedChars;

    public Player(){
    }
    public void removeLife(int amount) {
        lives -= amount;
    }

    public void giveNewWord(String word) {
        currentWord = word;
    }

    public String getCurrentWord() {
        return currentWord;
    }

}
