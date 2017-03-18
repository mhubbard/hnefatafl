package com.github.mhubbard.hnefatafl.rules;

public class CopenhagenRules implements Rules {

    @Override
    public boolean allowEncircle() {
        return true;
    }

    @Override
    public boolean isWeaponlessKing() {
        return false;
    }

    @Override
    public boolean isStrongKing() {
        return true;
    }
}
