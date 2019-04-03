package io.tafl.engine.board.impl;

import io.tafl.engine.board.Board;
import io.tafl.engine.board.PieceType;
import io.tafl.engine.board.Point;
import io.tafl.engine.rules.Rules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class BrandubhBoard extends Board {

    private static final int NUM_ATTACKERS = 8;
    private static final int NUM_DEFENDERS = 5;

    public BrandubhBoard(Rules rules) {
        super(rules);
        dimension = 7;
        edges = new HashSet<>(dimension * 4);
        cornerPoints = new HashSet<>(Arrays.asList(new Point(1, 1),
                                                   new Point(1, dimension),
                                                   new Point(dimension, 1),
                                                   new Point(dimension, dimension)));
        attackers = new HashMap<>(NUM_ATTACKERS);
        defenders = new HashMap<>(NUM_DEFENDERS);
        throne = new Point(4, 4);
        king = throne;

        for(int i = 1; i <= dimension; i++) {
            edges.add(new Point(i, 1));
            edges.add(new Point(i, dimension));
            edges.add(new Point(1, i));
            edges.add(new Point(dimension, i));
        }

        attackers.put(new Point(1, 4), PieceType.ATTACKER);
        attackers.put(new Point(2, 4), PieceType.ATTACKER);

        attackers.put(new Point(4, 1), PieceType.ATTACKER);
        attackers.put(new Point(4, 2), PieceType.ATTACKER);

        attackers.put(new Point(4, 6), PieceType.ATTACKER);
        attackers.put(new Point(4, 7), PieceType.ATTACKER);

        attackers.put(new Point(6, 4), PieceType.ATTACKER);
        attackers.put(new Point(7, 4), PieceType.ATTACKER);

        defenders.put(new Point(4, 3), PieceType.DEFENDER);
        defenders.put(new Point(4, 5), PieceType.DEFENDER);
        defenders.put(new Point(3, 4), PieceType.DEFENDER);
        defenders.put(new Point(5, 4), PieceType.DEFENDER);
        defenders.put(new Point(4, 4), PieceType.KING);
    }
}
