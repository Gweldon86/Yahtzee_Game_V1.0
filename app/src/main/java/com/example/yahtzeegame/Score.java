package com.example.yahtzeegame;

import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

interface diceFrequency{
    boolean frequencyOfNumbers();
}

abstract class Score{
    private int score = 0;
    public Button scoreButton;
    public TextView scoreLabel;
    public Die[] dieList;
    public boolean rulesApply;
    protected static boolean canScoreBonus = false;
    protected int[] dieNumberFrequency = new int[6];

    public Score(Button button, TextView textView, Die[] dieList) {
        scoreButton = button;
        scoreLabel = textView;
        this.dieList = dieList;
    }

    public boolean getYeetzahFlagState() {
        return canScoreBonus;
    }

    public void setScore(int value) {
        score = value;
    }

    public int getScore() {
        return score;
    }

    public void displayScore() {
        applyRule();
        scoreLabel.setText(String.valueOf(getScore()));
        scoreButton.setClickable(false);
    }

    public abstract void applyRule();
}

class UpperSectionScore extends Score {
    int sameDiceCount = 0;
    int diceFaceNumber;

    public UpperSectionScore(Button button, TextView textView, Die[] dieList, int diceFaceNumber) {
        super (button, textView, dieList);
        this.diceFaceNumber = diceFaceNumber;
    }

    public void applyRule() {
        for (Die i : dieList) {
            if (i.getDieValue() == diceFaceNumber) {
                sameDiceCount++;
            }
        }
        if (sameDiceCount > 0) {
            rulesApply = true;
            calculateScore();
        } else {
            setScore(0);
        }
    }

    private void calculateScore() {
        setScore(sameDiceCount * diceFaceNumber);
    }
}

class OfAKind extends Score implements diceFrequency{
    private int numberOfDiceToMatch;

    public OfAKind (Button button, TextView textView, Die[] dieList, int diceAmount) {
        super (button, textView, dieList);
        numberOfDiceToMatch = diceAmount;
    }

    public boolean frequencyOfNumbers() {
        boolean meetsRule = false;
        for (int i = 0; i < dieNumberFrequency.length; i++) {
            for (Die j : dieList) {
                if (i + 1 == j.getDieValue()) {
                    dieNumberFrequency[i] += 1;
                }
            }
        }
        for (int i : dieNumberFrequency) {
            if (i >= numberOfDiceToMatch) {
                meetsRule = true;
            }
        }
        return meetsRule;
    }

    public void applyRule() {
        rulesApply = frequencyOfNumbers();
        if (rulesApply) {
            calculateScore();
        }
    }

    private void calculateScore() {
        int temp = 0;
        for (Die i : dieList) {
            temp += i.getDieValue();
        }
        setScore(temp);
    }
}

class Yeetzah extends OfAKind{

    public Yeetzah (Button button, TextView textView, Die[] dieList, int diceAmount) {
        super (button, textView, dieList, diceAmount);
    }

    public void applyRule() {
        int diceNeededForYeetzah = 5;
        rulesApply = frequencyOfNumbers();
        Arrays.sort(dieNumberFrequency);
        int max = dieNumberFrequency[dieNumberFrequency.length-1];
        if (rulesApply && max == diceNeededForYeetzah) {
            canScoreBonus = true;
            calculateScore();
        }
    }

    private void calculateScore() {
        int YEETZAHSCORE = 50;
        setScore(YEETZAHSCORE);
    }
}

class Straight extends Score {
    private int numberOfDiceToLink;

    public Straight(Button button, TextView textView, Die[] dieList, int diceAmount) {
        super (button, textView, dieList);
        numberOfDiceToLink = diceAmount;
    }

    private void calculateScore() {
        int smStraight = 30, lgStraight = 40;
        if (numberOfDiceToLink == 4) {
            setScore(smStraight);
        } else if (numberOfDiceToLink == 5) {
            setScore(lgStraight);
        }
    }

    private boolean checkStraight() {
        boolean meetsRule = false;
        ArrayList<Integer> tempList = new ArrayList<>();
        for (Die i : dieList) {
            tempList.add(i.getDieValue());
        }
        Collections.sort(tempList);
        switch (numberOfDiceToLink) {
            case 4:
                if (tempList.containsAll(Arrays.asList(1, 2, 3, 4)) ||
                        tempList.containsAll(Arrays.asList(2, 3, 4, 5)) ||
                        tempList.containsAll(Arrays.asList(3, 4, 5, 6))) {
                    meetsRule = true;
                }
                break;
            case 5:
                if (tempList.containsAll(Arrays.asList(1, 2, 3, 4, 5)) ||
                        tempList.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
                    meetsRule = true;
                }
                break;
        }
        return meetsRule;
    }

    public void applyRule() {
        rulesApply = checkStraight();
        if (rulesApply) {
            calculateScore();
        }
    }
}

class Chance extends Score {
    public Chance (Button button, TextView textView, Die[] dieList) {
        super (button, textView, dieList);
    }

    private void calculateScore() {
        int temp = 0;
        for (Die i : dieList) {
            temp += i.getDieValue();
        }
        setScore(temp);
    }

    public void applyRule() {
        calculateScore();
    }
}

class FullHouse extends Score {

    public FullHouse (Button button, TextView textView, Die[] dieList) {
        super (button, textView, dieList);
    }

    private void calculateScore() {
        int fullHouseScore = 25;
        setScore(fullHouseScore);
    }

    public void applyRule() {
        rulesApply = checkFullHouse();
        if (rulesApply) {
            calculateScore();
        }
    }

    private boolean checkFullHouse () {
        boolean meetsRule = false;
        int sequenceOfThree = 3, sequenceOfTwo = 2;
        ArrayList<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < dieNumberFrequency.length; i++) {
            for (Die j : dieList) {
                if (i + 1 == j.getDieValue()) {
                    dieNumberFrequency[i] += 1;
                }
            }
        }
        for (int i : dieNumberFrequency) {
            tempList.add(i);
        }
        if (tempList.contains(sequenceOfThree) && tempList.contains(sequenceOfTwo)) {
            meetsRule = true;
        }
        return meetsRule;
    }
}