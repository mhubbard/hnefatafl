package com.github.mhubbard.hnefatafl;

import java.util.HashMap;
import java.util.Set;

public abstract class Board {

    protected HashMap<Point, PieceType> attackers;
    protected HashMap<Point, PieceType> defenders;

    public abstract int getDimension();
    public abstract Set<Point> getRestrictedPoints();
    public abstract Point getThrone();

    public HashMap<Point, PieceType> getAttackers() {
        return attackers;
    }

    public HashMap<Point, PieceType> getDefenders() {
        return defenders;
    }

    public boolean movePiece(Point from, Point to) {
        if(from.equals(to) || !from.sharesAxisWith(to)
           || !from.isOnBoard(getDimension()) || !to.isOnBoard(getDimension()))
            return false;

        boolean isAttacker = false;
        PieceType pieceType;
        if(getDefenders().containsKey(from)) {
            pieceType = getDefenders().get(from);
        } else if(getAttackers().containsKey(from)) {
            isAttacker = true;
            pieceType = getDefenders().get(from);
        } else return false;

        if(pieceType != PieceType.KING && getRestrictedPoints().contains(to))
            return false;

        for(Point point: from.getPointsBetween(to))
            if(getAttackers().containsKey(point) || getDefenders().containsKey(point))
                return false;

        if(isAttacker) {
            getAttackers().remove(from);
            getAttackers().put(to, pieceType);
        } else {
            getDefenders().remove(from);
            getDefenders().put(to, pieceType);
        }
        return true;
    }

    public void printBoard() {
        Point point = new Point(0, 0);
        for(int i = getDimension(); i > 0; i--) {
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+\n");
            point.setX(i);
            for(int j = getDimension(); j > 0; j--) {
                System.out.print('|');
                char print = ' ';
                point.setY(j);

                if(getRestrictedPoints().contains(point)) {
                    print = 'X';
                }

                if(attackers.containsKey(point))
                    print = 'A';

                if(defenders.containsKey(point)) {
                    PieceType type = getDefenders().get(point);
                    if (type == PieceType.DEFENDER)
                        print = 'D';
                    else if (type == PieceType.KING)
                        print = 'K';
                }

                System.out.print(print);
            }
            System.out.print('|');
            System.out.print('\n');
        }
        System.out.print("+-+-+-+-+-+-+-+-+-+-+-+\n");
    }
}
