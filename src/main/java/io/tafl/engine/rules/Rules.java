package io.tafl.engine.rules;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Rules {

    // Rule presets
    public static final Rules BRANDUB = new Rules(false, false, false, false, false, false);
    public static final Rules TABLUT = new Rules(false, false, false, false, false, false);
    public static final Rules COPENHAGEN = new Rules(false, true, false, false, true,false);
    public static final Rules FETLAR = new Rules(false, false, false, false, true, false);

    private boolean edgeEscape;
    private boolean allowEncircle;
    private boolean weaponlessKing;
    private boolean weakKing;
    private boolean kingCanReenterThrone;
    private boolean singleMoveKing;

    public Rules(boolean edgeEscape,
                 boolean allowEncircle,
                 boolean weaponlessKing,
                 boolean weakKing,
                 boolean kingCanReenterThrone,
                 boolean singleMoveKing) {
        this.edgeEscape = edgeEscape;
        this.allowEncircle = allowEncircle;
        this.weaponlessKing = weaponlessKing;
        this.weakKing = weakKing;
        this.kingCanReenterThrone = kingCanReenterThrone;
        this.singleMoveKing = singleMoveKing;
    }

    public boolean isEdgeEscape() { return this.edgeEscape; }
    public boolean isAllowEncircle() { return this.allowEncircle; }
    public boolean isWeaponlessKing() { return this.weaponlessKing; }
    public boolean isWeakKing() { return this.weakKing; }
    public boolean isKingCanReenterThrone() { return this.kingCanReenterThrone; }
    public boolean isSingleMoveKing() { return this.singleMoveKing; }
}
