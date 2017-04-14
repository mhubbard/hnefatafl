package io.tafl.rules;

public class TablutRules implements Rules {

    @Override
    public boolean edgeEscape() {
        return false;
    }

    @Override
    public boolean allowEncircle() {
        return false;
    }

    @Override
    public boolean isWeaponlessKing() {
        return false;
    }

    @Override
    public boolean isWeakKing() {
        return false;
    }

    @Override
    public boolean canKingReenterThrone() {
        return false;
    }

    @Override
    public boolean singleMoveKing() {
        return false;
    }
}
