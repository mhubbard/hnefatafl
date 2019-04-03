package io.tafl.engine.board.impl;

import io.tafl.engine.board.Board;
import io.tafl.engine.board.PieceType;
import io.tafl.engine.board.Point;
import io.tafl.engine.rules.Rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HnefataflBoard extends Board {

    private static final int NUM_ATTACKERS = 24;
    private static final int NUM_DEFENDERS = 11;

    public HnefataflBoard(Rules rules) {
        super(rules);
        dimension = 11;
        edges = new HashSet<>(dimension * 4);
        cornerPoints = new HashSet<>(4);
        attackers = new HashMap<>(NUM_ATTACKERS);
        defenders = new HashMap<>(NUM_DEFENDERS);
        throne = new Point(6, 6);
        king = throne;

        for(int i = 1; i <= dimension; i++) {
            edges.add(new Point(i, 1));
            edges.add(new Point(i, dimension));
            edges.add(new Point(1, i));
            edges.add(new Point(dimension, i));
        }

        cornerPoints.add(new Point(1, 1));
        cornerPoints.add(new Point(1, dimension));
        cornerPoints.add(new Point(dimension, 1));
        cornerPoints.add(new Point(dimension, dimension));

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
}
