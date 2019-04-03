package io.tafl.engine.board.impl;

import io.tafl.engine.board.Board;
import io.tafl.engine.board.PieceType;
import io.tafl.engine.board.Point;
import io.tafl.engine.rules.Rules;

import java.util.HashMap;
import java.util.HashSet;

public class TablutBoard extends Board {

    private static final int NUM_ATTACKERS = 16;
    private static final int NUM_DEFENDERS = 9;


    public TablutBoard(Rules rules) {
        super(rules);
        dimension = 9;
        edges = new HashSet<>(dimension * 4);
        cornerPoints = new HashSet<>(4);
        attackers = new HashMap<>(NUM_ATTACKERS);
        defenders = new HashMap<>(NUM_DEFENDERS);
        throne = new Point(5, 5);
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
        attackers.put(new Point(2, 5), PieceType.ATTACKER);

        attackers.put(new Point(4, 1), PieceType.ATTACKER);
        attackers.put(new Point(5, 1), PieceType.ATTACKER);
        attackers.put(new Point(6, 1), PieceType.ATTACKER);
        attackers.put(new Point(5, 2), PieceType.ATTACKER);

        attackers.put(new Point(4, 9), PieceType.ATTACKER);
        attackers.put(new Point(5, 9), PieceType.ATTACKER);
        attackers.put(new Point(6, 9), PieceType.ATTACKER);
        attackers.put(new Point(5, 8), PieceType.ATTACKER);

        attackers.put(new Point(9, 4), PieceType.ATTACKER);
        attackers.put(new Point(9, 5), PieceType.ATTACKER);
        attackers.put(new Point(9, 6), PieceType.ATTACKER);
        attackers.put(new Point(8, 5), PieceType.ATTACKER);

        defenders.put(new Point(5, 3), PieceType.DEFENDER);
        defenders.put(new Point(5, 4), PieceType.DEFENDER);
        defenders.put(new Point(5, 6), PieceType.DEFENDER);
        defenders.put(new Point(5, 7), PieceType.DEFENDER);
        defenders.put(new Point(3, 5), PieceType.DEFENDER);
        defenders.put(new Point(4, 5), PieceType.DEFENDER);
        defenders.put(new Point(6, 5), PieceType.DEFENDER);
        defenders.put(new Point(7, 5), PieceType.DEFENDER);
        defenders.put(new Point(5, 5), PieceType.KING);
    }
}
