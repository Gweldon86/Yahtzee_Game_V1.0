package com.example.yahtzeegame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.yahtzeegame.Die.rollCount;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected ImageView dice_image_slot1, dice_image_slot2, dice_image_slot3, dice_image_slot4, dice_image_slot5;
    protected ImageView[] diceImageViewArray = {dice_image_slot1, dice_image_slot2, dice_image_slot3, dice_image_slot4, dice_image_slot5};
    protected Button ones_button, twos_button, threes_button, fours_button, fives_button, sixes_button;
    protected Button three_kind_button, four_kind_button, yeetzah_button, small_straight_button, large_straight_button, chance_button, full_house_button, dice_roll_button, reset_button;
    protected Button[] upperScoreButtonArray = {ones_button, twos_button, threes_button, fours_button, fives_button, sixes_button};
    protected TextView ones_label, twos_label, threes_label, fours_label, fives_label, sixes_label;
    protected TextView three_kind_score, four_kind_score, yeetzah_score, yeetzah_bonus_score, small_straight_score, large_straight_score, chance_score, full_house_score, upper_total_score, upper_bonus_score, upper_with_bonus_score, lower_total_score, grand_total;
    protected TextView[] upperScoreLabelArray = {ones_label, twos_label, threes_label, fours_label, fives_label, sixes_label};
    protected int[] diceImageIDArray = {R.id.dice_image1, R.id.dice_image2, R.id.dice_image3, R.id.dice_image4, R.id.dice_image5};
    protected int[] upperScoreButtonIDArray = {R.id.ones_button, R.id.twos_button, R.id.threes_button, R.id.fours_button, R.id.fives_button, R.id.sixes_button};
    protected int[] upperScoreLabelIDArray = {R.id.ones_score, R.id.twos_score, R.id.threes_score, R.id.fours_score, R.id.fives_score, R.id.sixes_score};
    protected Die[] AllDice = new Die[5];
    protected UpperSectionScore[] UpperScores = new UpperSectionScore[6];
    protected int upperTotal = 0, upperBonus = 0, lowerTotal = 0, grandTotal = 0, yeetzahBonusTotal = 0, upperBonusGoal = 63;
    protected boolean turnButtonsOn = true, turnButtonsOff = false;

    OfAKind ThreeOfAKind = new OfAKind(three_kind_button, three_kind_score, AllDice, 3);
    OfAKind FourOfAKind = new OfAKind(four_kind_button, four_kind_score, AllDice, 4);
    Yeetzah yeetzah = new Yeetzah(yeetzah_button, yeetzah_score, AllDice, 5);
    Straight SmallStraight = new Straight(small_straight_button, small_straight_score, AllDice, 4);
    Straight LargeStraight = new Straight(large_straight_button, large_straight_score, AllDice, 5);
    Chance chance = new Chance (chance_button, chance_score, AllDice);
    FullHouse fullHouse = new FullHouse(full_house_button, full_house_score, AllDice);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < AllDice.length; i++) {
            Die die = new Die(diceImageViewArray[i]);
            AllDice[i] = die;
            AllDice[i].image_slot = findViewById(diceImageIDArray[i]);
        }

        for (int i = 0; i < UpperScores.length; i++) {
            UpperSectionScore u = new UpperSectionScore(upperScoreButtonArray[i], upperScoreLabelArray[i], AllDice, i+1);
            UpperScores[i] = u;
            UpperScores[i].scoreButton = findViewById(upperScoreButtonIDArray[i]);
            UpperScores[i].scoreLabel = findViewById(upperScoreLabelIDArray[i]);
        }
        ThreeOfAKind.dieList = AllDice;
        ThreeOfAKind.scoreButton = findViewById(R.id.three_kind_button);
        ThreeOfAKind.scoreLabel = findViewById(R.id.three_kind_score);
        ThreeOfAKind.scoreButton.setOnClickListener(LowerScoreClick);
        FourOfAKind.dieList = AllDice;
        FourOfAKind.scoreButton = findViewById(R.id.four_kind_button);
        FourOfAKind.scoreLabel = findViewById(R.id.four_kind_score);
        FourOfAKind.scoreButton.setOnClickListener(LowerScoreClick);
        yeetzah.dieList = AllDice;
        yeetzah.scoreButton = findViewById(R.id.yeetzah_button);
        yeetzah.scoreLabel = findViewById(R.id.yeetzah_score);
        yeetzah.scoreButton.setOnClickListener(LowerScoreClick);
        SmallStraight.dieList = AllDice;
        SmallStraight.scoreButton = findViewById(R.id.small_straight_button);
        SmallStraight.scoreLabel = findViewById(R.id.small_straight_score);
        SmallStraight.scoreButton.setOnClickListener(LowerScoreClick);
        LargeStraight.dieList = AllDice;
        LargeStraight.scoreButton = findViewById(R.id.large_straight_button);
        LargeStraight.scoreLabel = findViewById(R.id.large_straight_score);
        LargeStraight.scoreButton.setOnClickListener(LowerScoreClick);
        chance.dieList = AllDice;
        chance.scoreButton = findViewById(R.id.chance_button);
        chance.scoreLabel = findViewById(R.id.chance_score);
        chance.scoreButton.setOnClickListener(LowerScoreClick);
        fullHouse.dieList = AllDice;
        fullHouse.scoreButton = findViewById(R.id.full_house_button);
        fullHouse.scoreLabel = findViewById(R.id.full_house_score);
        fullHouse.scoreButton.setOnClickListener(LowerScoreClick);

        yeetzah_bonus_score = findViewById(R.id.yeetzah_bonus_score);
        grand_total = findViewById(R.id.grand_total_score);
        upper_total_score = findViewById(R.id.upper_total_score);
        upper_bonus_score = findViewById(R.id.upper_bonus_score);
        upper_with_bonus_score = findViewById(R.id.upper_with_bonus_score);
        lower_total_score = findViewById(R.id.lower_total_score);
        dice_roll_button = findViewById(R.id.dice_roll_button);
        dice_roll_button.setOnClickListener(DiceRollClick);
        reset_button = findViewById(R.id.reset_button);

        for (Die i : AllDice) {
            i.image_slot.setOnClickListener(this);
        }
        for (UpperSectionScore i : UpperScores) {
            i.scoreButton.setOnClickListener(UpperScoreClick);
        }
    }

    public void generateDieValue(Die die) {
        Random r = new Random();
        int diceNumber =  1 + r.nextInt(6);
        die.setDieValue(diceNumber);
    }

    public int displayDie(Die die) {
        int d = 0;

        switch (die.getDieValue()) {
            case 1:
                d = R.drawable.dice_one;
                break;
            case 2:
                d = R.drawable.dice_two;
                break;
            case 3:
                d = R.drawable.dice_three;
                break;
            case 4:
                d = R.drawable.dice_four;
                break;
            case 5:
                d = R.drawable.dice_five;
                break;
            case 6:
                d = R.drawable.dice_six;
                break;
        }
        return d;
    }

    public void rollDie(Die die) {
        generateDieValue(die);
        die.image_slot.setImageResource(displayDie(die));
    }

    public void toggleButtonsOnOff (boolean OnOff) {
        Button[] testArray = {UpperScores[0].scoreButton, UpperScores[1].scoreButton, UpperScores[2].scoreButton, UpperScores[3].scoreButton,
                UpperScores[4].scoreButton, UpperScores[5].scoreButton, ThreeOfAKind.scoreButton, FourOfAKind.scoreButton, yeetzah.scoreButton,
                SmallStraight.scoreButton, LargeStraight.scoreButton, chance.scoreButton, fullHouse.scoreButton};

        for (Button b : testArray) {
            if (OnOff) {
                b.setEnabled(true);
            } else {
                b.setEnabled(false);
            }
        }
    }

    public void resetRollCount() {
        rollCount = 1;
        dice_roll_button.setEnabled(true);
        for (Die die : AllDice) {
            die.resetFlagStates();
            die.image_slot.setImageResource(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dice_image1:
                AllDice[0].toggleFlagState();
                break;
            case R.id.dice_image2:
                AllDice[1].toggleFlagState();
                break;
            case R.id.dice_image3:
                AllDice[2].toggleFlagState();
                break;
            case R.id.dice_image4:
                AllDice[3].toggleFlagState();
                break;
            case R.id.dice_image5:
                AllDice[4].toggleFlagState();
                break;
        }
    }

    public void setDiceImage() {
        for (Die i : AllDice) {
            if (!i.getFlagState()) {
                rollDie(i);
            }
        }
        rollCount++;
        if (rollCount > 3) {
            dice_roll_button.setEnabled(false);
        }
        toggleButtonsOnOff(turnButtonsOn);
    }

    public boolean yeetzahBonus(boolean yeetzahFlag) {
        int bonusScore = 50;
        boolean yeetzahBonusCheck = false;
        ArrayList<Integer> yeetzahBonusCheckList = new ArrayList<>();
        for (Die i : AllDice) {
            if (!yeetzahBonusCheckList.contains(i.getDieValue())) {
                yeetzahBonusCheckList.add(i.getDieValue());
            }
        }
        if (yeetzahBonusCheckList.size() == 1 && yeetzahFlag) {
            yeetzahBonusCheck = true;
            yeetzahBonusTotal += bonusScore;
        }
        return yeetzahBonusCheck;
    }

    public void updateYeetzahBonus(boolean yeetzahFlag) {
        if (yeetzahBonus(yeetzahFlag)) {
            yeetzah_bonus_score.setText(String.valueOf(yeetzahBonusTotal));
        }
    }

    public void updateUpperScores(Score score) {
        upperTotal += score.getScore();
        if (upperTotal >= upperBonusGoal) {
            upperBonus = 35;
            upper_bonus_score.setText(String.valueOf(upperBonus));
        }
        upper_total_score.setText(String.valueOf(upperTotal));
        upper_with_bonus_score.setText(String.valueOf(upperTotal));
        updateGrandTotal();
        toggleButtonsOnOff(turnButtonsOff);
        resetRollCount();
    }

    public void updateLowerScores(Score score) {
        lowerTotal += score.getScore();
        lower_total_score.setText(String.valueOf(lowerTotal));
        updateGrandTotal();
        toggleButtonsOnOff(turnButtonsOff);
        resetRollCount();
    }

    public void updateGrandTotal() {
        grandTotal = 0;
        grandTotal = upperTotal + lowerTotal + upperBonus;
        grand_total.setText(String.valueOf(grandTotal));
    }

    public View.OnClickListener DiceRollClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setDiceImage();
        }
    };

    public View.OnClickListener UpperScoreClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ones_button:
                    UpperScores[0].displayScore();
                    updateYeetzahBonus(UpperScores[0].getYeetzahFlagState());
                    updateUpperScores(UpperScores[0]);
                    break;
                case R.id.twos_button:
                    UpperScores[1].displayScore();
                    updateYeetzahBonus(UpperScores[1].getYeetzahFlagState());
                    updateUpperScores(UpperScores[1]);
                    break;
                case R.id.threes_button:
                    UpperScores[2].displayScore();
                    updateYeetzahBonus(UpperScores[2].getYeetzahFlagState());
                    updateUpperScores(UpperScores[2]);
                    break;
                case R.id.fours_button:
                    UpperScores[3].displayScore();
                    updateYeetzahBonus(UpperScores[3].getYeetzahFlagState());
                    updateUpperScores(UpperScores[3]);
                    break;
                case R.id.fives_button:
                    UpperScores[4].displayScore();
                    updateYeetzahBonus(UpperScores[4].getYeetzahFlagState());
                    updateUpperScores(UpperScores[4]);
                    break;
                case R.id.sixes_button:
                    UpperScores[5].displayScore();
                    updateYeetzahBonus(UpperScores[5].getYeetzahFlagState());
                    updateUpperScores(UpperScores[5]);
                    break;
                default:
                    break;
            }
        }
    };

    public View.OnClickListener LowerScoreClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.three_kind_button:
                    ThreeOfAKind.displayScore();
                    updateYeetzahBonus(ThreeOfAKind.getYeetzahFlagState());
                    updateLowerScores(ThreeOfAKind);
                    break;
                case R.id.four_kind_button:
                    FourOfAKind.displayScore();
                    updateYeetzahBonus(FourOfAKind.getYeetzahFlagState());
                    updateLowerScores(FourOfAKind);
                    break;
                case R.id.chance_button:
                    chance.displayScore();
                    updateYeetzahBonus(chance.getYeetzahFlagState());
                    updateLowerScores(chance);
                    break;
                case R.id.yeetzah_button:
                    yeetzah.displayScore();
                    updateLowerScores(yeetzah);
                    break;
                case R.id.small_straight_button:
                    SmallStraight.displayScore();
                    updateLowerScores(SmallStraight);
                    break;
                case R.id.large_straight_button:
                    LargeStraight.displayScore();
                    updateLowerScores(LargeStraight);
                    break;
                case R.id.full_house_button:
                    fullHouse.displayScore();
                    updateLowerScores(fullHouse);
                    break;
            }
        }
    };

    public void resetGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to reset the game?");
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog resetConfirm = builder.create();
        resetConfirm.show();
    }
}
