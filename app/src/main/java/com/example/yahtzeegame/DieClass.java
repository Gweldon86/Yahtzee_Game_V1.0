package com.example.yahtzeegame;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.ImageView;


class Die {
    private int value = 0;
    public ImageView image_slot;
    private boolean is_Flagged = false;
    public static int rollCount = 1;

    public Die (ImageView imageSlot) {
        image_slot = imageSlot;
    }

    public void setDieValue (int dieValue) {
        value = dieValue;
    }

    public int getDieValue () {
        return value;
    }

    /*This seems confusing but I wanted
    to ensure that selected dice to
    hold will be flagged as true.*/
    public void toggleFlagState() {
        if (!is_Flagged) {
            is_Flagged = true;
            image_slot.setColorFilter(Color.argb(100, 100, 181, 246), PorterDuff.Mode.DARKEN);
        } else {
            is_Flagged = false;
            image_slot.clearColorFilter();
        }
    }

    public void resetFlagStates() {
        is_Flagged = false;
        image_slot.clearColorFilter();
    }

    public boolean getFlagState() {
        return is_Flagged;
    }
}