package com.hotTable.API_BlindWord.logic;

import com.hotTable.API_BlindWord.repository.WordRepository;
import java.util.concurrent.ThreadLocalRandom;

public class ManegerWord {

    private final WordRepository wordRepository;
    private String secretWord;

    public ManegerWord(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public ValidatePlay validateWord(String wordsGuess){
        String currentSecretWord = getSecretWord();

        ValidatePlay results = new ValidatePlay();

        for(int position = 0; position < wordsGuess.length(); position++){
            results.addResult(evaluateLetter(wordsGuess.charAt(position), currentSecretWord, position));
        }
        return results;
    }

    public void reloadSecretWord() {
        secretWord = null;
    }

    private String getSecretWord() {
        if (secretWord == null) {
            long maxId = wordRepository.findMaxId()
                    .orElseThrow(() -> new IllegalStateException("No active words found in database."));

            long randomId = ThreadLocalRandom.current().nextLong(maxId + 1);

            secretWord = wordRepository.findRandomWordFromId(randomId)
                    .orElseThrow(() -> new IllegalStateException("No active words found in database."));
        }

        return secretWord;
    }

    private LetterAssessment evaluateLetter(char guessedLetter, String secretWord, int position) {

        if (secretWord.charAt(position) == guessedLetter) {
            return new LetterAssessment(guessedLetter, status.green);
        }

        if (secretWord.indexOf(guessedLetter) != -1) {
            return new LetterAssessment(guessedLetter, status.yellow);
        }

        return new LetterAssessment(guessedLetter, status.gray);
    }
}
