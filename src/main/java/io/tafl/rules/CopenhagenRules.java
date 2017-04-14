package io.tafl.rules;

public class CopenhagenRules implements Rules {

    @Override
    public boolean edgeEscape() {
        return false;
    }

    @Override
    public boolean allowEncircle() {
        return true;
    }

    @Override
    public boolean isWeaponlessKing() {
        return false;
    }

    @Override
    public boolean canKingReenterThrone() {
        return true;
    }

    @Override
    public boolean singleMoveKing() {
        return false;
    }
}
