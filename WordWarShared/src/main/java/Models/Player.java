package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    private User user;
    private int lives;
    private String currentWord;
    private char[] typedChars;

    public Player(){
    }
    public void removeLife(int amount) {
        lives -= amount;
    }

    public void giveNewWord(String word) {
        currentWord = word;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
