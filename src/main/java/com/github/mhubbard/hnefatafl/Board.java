package com.github.mhubbard.hnefatafl;

import java.util.Set;

public abstract class Board {

    protected Set<Point> attackers;
    protected Set<Point> defenders;

    public Set<Point> getAttackers() {
        return attackers;
    }

    public Set<Point> getDefenders() {
        return defenders;
    }

    public abstract int getDimension();

    public void printBoard() {
        for(int i = getDimension(); i > 0; i--) {
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+\n");
            for(int j = getDimension(); j > 0; j--) {
                System.out.print('|');
                char print = ' ';
                if((i == 1 && j == 1) || (i == 1 && j == 11)
                   || (i == 11 && j == 1) || (i == 11 && j == 11)
                   || (i == 6 && j == 6)) {
                    print = 'X';
                }

                for(Point point: getAttackers()) {
                    if (i == point.getX() && j == point.getY()) {
                        print = 'A';
                    }
                }

                for(Point point: getDefenders()) {
                    if (i == point.getX() && j == point.getY()) {
                        if (point.getType() == PointType.DEFENDER)
                            print = 'D';
                        else if (point.getType() == PointType.KING)
                            print = 'K';
                    }
                }

                System.out.print(print);
            }
            System.out.print('|');
            System.out.print('\n');
        }
        System.out.print("+-+-+-+-+-+-+-+-+-+-+-+\n");
    }
}
