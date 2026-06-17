package com.hotTable.API_BlindWord.logic;

import java.util.ArrayList;

public class ValidatePlay {

    private ArrayList<LetterAssessment> results = new ArrayList<>();
    private boolean correct = true;

    public ArrayList<LetterAssessment> getResults() {
        return results;
    }

    public void addResult(LetterAssessment letterAssessment){
        if(letterAssessment.status() != status.green){
            correct = false;
        }

        results.add(letterAssessment);
    }

    public void setResults(ArrayList<LetterAssessment> results) {
        this.results = results;
    }

    public boolean isCorrect() { return correct; }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
