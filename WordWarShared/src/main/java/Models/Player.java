package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    @Getter @Setter private User user;
    @Getter @Setter private int lives;
    @Getter private String currentWord;
    @Getter @Setter private String typedChars = "";

    public void removeLife(int amount) {
        lives -= amount;
    }

    public void giveNewWord(String word) {
        currentWord = word;
        typedChars = "";
    }

    public void typeCharacter(char typedCharacter) {
        int index = typedChars.length();
        char character = currentWord.charAt(index);

        if(character == typedCharacter) {
            typedChars += typedCharacter;
        }
    }

    public boolean completedWord() {
        return currentWord.compareTo(typedChars) == 0;
    }

}
