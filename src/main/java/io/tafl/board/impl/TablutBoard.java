package io.tafl.board.impl;


import io.tafl.board.Board;
import io.tafl.board.PieceType;
import io.tafl.board.Point;
import io.tafl.rules.Rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TablutBoard extends Board {

    private static final int NUM_ATTACKERS = 16;
    private static final int NUM_DEFENDERS = 9;

    private static Set<Point> edges;
    private static Set<Point> cornerPoints;
    private static final Point THRONE = new Point(5, 5);

    public TablutBoard(Rules rules) {
        super(rules);
        edges = new HashSet<>(getDimension() * 4);
        cornerPoints = new HashSet<>(4);
        attackers = new HashMap<>(NUM_ATTACKERS);
        defenders = new HashMap<>(NUM_DEFENDERS);
        king = THRONE;

        for(int i = 1; i <= getDimension(); i++) {
            edges.add(new Point(i, 1));
            edges.add(new Point(i, getDimension()));
            edges.add(new Point(1, i));
            edges.add(new Point(getDimension(), i));
        }

        cornerPoints.add(new Point(1, 1));
        cornerPoints.add(new Point(1, getDimension()));
        cornerPoints.add(new Point(getDimension(), 1));
        cornerPoints.add(new Point(getDimension(), getDimension()));

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

    @Override
    public int getDimension() {
        return 9;
    }

    @Override
    public Set<Point> getEdges() {
        return edges;
    }

    @Override
    public Set<Point> getCornerPoints() {
        return cornerPoints;
    }

    @Override
    public Point getThrone() {
        return THRONE;
    }
}
