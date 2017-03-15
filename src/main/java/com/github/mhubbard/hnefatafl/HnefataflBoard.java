package com.github.mhubbard.hnefatafl;

import java.util.HashSet;

public class HnefataflBoard extends Board {

    public HnefataflBoard() {
        attackers = new HashSet<>(24);
        defenders = new HashSet<>(11);

        attackers.add(new Point(1, 4, PointType.ATTACKER));
        attackers.add(new Point(1, 5, PointType.ATTACKER));
        attackers.add(new Point(1, 6, PointType.ATTACKER));
        attackers.add(new Point(1, 7, PointType.ATTACKER));
        attackers.add(new Point(1, 8, PointType.ATTACKER));
        attackers.add(new Point(2, 6, PointType.ATTACKER));

        attackers.add(new Point(4, 1, PointType.ATTACKER));
        attackers.add(new Point(5, 1, PointType.ATTACKER));
        attackers.add(new Point(6, 1, PointType.ATTACKER));
        attackers.add(new Point(7, 1, PointType.ATTACKER));
        attackers.add(new Point(8, 1, PointType.ATTACKER));
        attackers.add(new Point(6, 2, PointType.ATTACKER));

        attackers.add(new Point(4, 11, PointType.ATTACKER));
        attackers.add(new Point(5, 11, PointType.ATTACKER));
        attackers.add(new Point(6, 11, PointType.ATTACKER));
        attackers.add(new Point(7, 11, PointType.ATTACKER));
        attackers.add(new Point(8, 11, PointType.ATTACKER));
        attackers.add(new Point(6, 10, PointType.ATTACKER));

        attackers.add(new Point(11, 4, PointType.ATTACKER));
        attackers.add(new Point(11, 5, PointType.ATTACKER));
        attackers.add(new Point(11, 6, PointType.ATTACKER));
        attackers.add(new Point(11, 7, PointType.ATTACKER));
        attackers.add(new Point(11, 8, PointType.ATTACKER));
        attackers.add(new Point(10, 6, PointType.ATTACKER));

        defenders.add(new Point(6, 4, PointType.DEFENDER));
        defenders.add(new Point(5, 5, PointType.DEFENDER));
        defenders.add(new Point(6, 5, PointType.DEFENDER));
        defenders.add(new Point(7, 5, PointType.DEFENDER));
        defenders.add(new Point(4, 6, PointType.DEFENDER));
        defenders.add(new Point(5, 6, PointType.DEFENDER));
        defenders.add(new Point(7, 6, PointType.DEFENDER));
        defenders.add(new Point(8, 6, PointType.DEFENDER));
        defenders.add(new Point(5, 7, PointType.DEFENDER));
        defenders.add(new Point(6, 7, PointType.DEFENDER));
        defenders.add(new Point(7, 7, PointType.DEFENDER));
        defenders.add(new Point(6, 8, PointType.DEFENDER));
        defenders.add(new Point(6, 6, PointType.KING));
    }

    @Override
    public int getDimension() {
        return 11;
    }

    public static void main(String[] args) {
        Board board = new HnefataflBoard();

        board.printBoard();
    }
}
