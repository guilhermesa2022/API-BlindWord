package com.hotTable.API_BlindWord.logic;

public class Game {

    private int numberOfAttempts = 5;
    private final ManegerWord manegerWord;

    public Game(ManegerWord manegerWord) {
        this.manegerWord = manegerWord;
    }

    public ValidatePlay makeAAttenmpt(String wordsGuess){
        return manegerWord.validateWord(wordsGuess);
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }
}
