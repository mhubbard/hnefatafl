package io.tafl.rules;

public interface Rules {

    public boolean edgeEscape();
    public boolean allowEncircle();
    public boolean isWeaponlessKing();
    public boolean canKingReenterThrone();
    public boolean singleMoveKing();
}
