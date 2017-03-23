package io.tafl.rules;


public class FetlarRules implements Rules {

    @Override
    public boolean allowEncircle() {
        return false;
    }

    @Override
    public boolean isWeaponlessKing() {
        return false;
    }

    @Override
    public boolean canKingReenterThrone() {
        return true;
    }
}
