package com.github.mhubbard.hnefatafl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HnefataflBoard extends Board {

    private static final int NUM_RESTRICTED_POINTS = 5;
    private static final int NUM_ATTACKERS = 24;
    private static final int NUM_DEFENDERS = 11;

    private static Set<Point> restrictedPoints;
    private static final Point THRONE = new Point(6, 6);

    public HnefataflBoard() {
        restrictedPoints = new HashSet<>(NUM_RESTRICTED_POINTS);
        attackers = new HashMap<>(NUM_ATTACKERS);
        defenders = new HashMap<>(NUM_DEFENDERS);

        restrictedPoints.add(new Point(1, 1));
        restrictedPoints.add(new Point(1, 11));
        restrictedPoints.add(new Point(11, 1));
        restrictedPoints.add(new Point(11, 11));
        restrictedPoints.add(THRONE);

        attackers.put(new Point(1, 4), PieceType.ATTACKER);
        attackers.put(new Point(1, 5), PieceType.ATTACKER);
        attackers.put(new Point(1, 6), PieceType.ATTACKER);
        attackers.put(new Point(1, 7), PieceType.ATTACKER);
        attackers.put(new Point(1, 8), PieceType.ATTACKER);
        attackers.put(new Point(2, 6), PieceType.ATTACKER);

        attackers.put(new Point(4, 1), PieceType.ATTACKER);
        attackers.put(new Point(5, 1), PieceType.ATTACKER);
        attackers.put(new Point(6, 1), PieceType.ATTACKER);
        attackers.put(new Point(7, 1), PieceType.ATTACKER);
        attackers.put(new Point(8, 1), PieceType.ATTACKER);
        attackers.put(new Point(6, 2), PieceType.ATTACKER);

        attackers.put(new Point(4, 11), PieceType.ATTACKER);
        attackers.put(new Point(5, 11), PieceType.ATTACKER);
        attackers.put(new Point(6, 11), PieceType.ATTACKER);
        attackers.put(new Point(7, 11), PieceType.ATTACKER);
        attackers.put(new Point(8, 11), PieceType.ATTACKER);
        attackers.put(new Point(6, 10), PieceType.ATTACKER);

        attackers.put(new Point(11, 4), PieceType.ATTACKER);
        attackers.put(new Point(11, 5), PieceType.ATTACKER);
        attackers.put(new Point(11, 6), PieceType.ATTACKER);
        attackers.put(new Point(11, 7), PieceType.ATTACKER);
        attackers.put(new Point(11, 8), PieceType.ATTACKER);
        attackers.put(new Point(10, 6), PieceType.ATTACKER);

        defenders.put(new Point(6, 4), PieceType.DEFENDER);
        defenders.put(new Point(5, 5), PieceType.DEFENDER);
        defenders.put(new Point(6, 5), PieceType.DEFENDER);
        defenders.put(new Point(7, 5), PieceType.DEFENDER);
        defenders.put(new Point(4, 6), PieceType.DEFENDER);
        defenders.put(new Point(5, 6), PieceType.DEFENDER);
        defenders.put(new Point(7, 6), PieceType.DEFENDER);
        defenders.put(new Point(8, 6), PieceType.DEFENDER);
        defenders.put(new Point(5, 7), PieceType.DEFENDER);
        defenders.put(new Point(6, 7), PieceType.DEFENDER);
        defenders.put(new Point(7, 7), PieceType.DEFENDER);
        defenders.put(new Point(6, 8), PieceType.DEFENDER);
        defenders.put(new Point(6, 6), PieceType.KING);
    }

    @Override
    public int getDimension() {
        return 11;
    }

    @Override
    public final Set<Point> getRestrictedPoints() {
        return restrictedPoints;
    }

    @Override
    public Point getThrone() {
        return THRONE;
    }
}
