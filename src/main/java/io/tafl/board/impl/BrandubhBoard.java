package io.tafl.board.impl;

import io.tafl.board.Board;
import io.tafl.board.PieceType;
import io.tafl.board.Point;
import io.tafl.rules.Rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BrandubhBoard extends Board {

    private static final int NUM_ATTACKERS = 8;
    private static final int NUM_DEFENDERS = 5;

    private static Set<Point> edges;
    private static Set<Point> cornerPoints;
    private static final Point THRONE = new Point(4, 4);

    public BrandubhBoard(Rules rules) {
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

    @Override
    public int getDimension() {
        return 7;
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
