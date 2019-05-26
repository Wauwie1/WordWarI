package Models;

import Models.User;

public class Player {
    private User user;
    private int lives;
    private String currentWord;
    private char[] typedChars;

    public Player(User user, int lives){
        this.user = user;
        this.lives = lives;
    }
    public void removeLife(int amount) {
        lives -= amount;
    }

    public void giveNewWord(String word) {
        currentWord = word;
    }
}
