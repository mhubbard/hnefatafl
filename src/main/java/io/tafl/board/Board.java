package io.tafl.board;

import io.tafl.rules.Rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Board {

    protected Rules rules;
    protected HashMap<Point, PieceType> attackers;
    protected HashMap<Point, PieceType> defenders;
    protected Point king;

    public abstract int getDimension();
    public abstract Set<Point> getEdges();
    public abstract Set<Point> getCornerPoints();
    public abstract Point getThrone();

    protected Board(Rules rules) {
        this.rules = rules;
    }

    public HashMap<Point, PieceType> getAttackers() {
        return attackers;
    }

    public HashMap<Point, PieceType> getDefenders() {
        return defenders;
    }

    public Point getKing() {
        return king;
    }

    public void setKing(Point king) {
        this.king = king;
    }

    /**
     * Check if the opposingSide is encircled.
     * <p>
     *     A side is encircled if there is no possible path that any of their
     *     pieces may take to reach an edge, regardless of number of steps.
     * </p>
     * @param encirclingSide The pieces to check if they have encircled their enemy.
     * @param opposingSide The pieces to check if they are encircled.
     * @return true if the opposingSide is encircled, false otherwise.
     */
    public boolean checkEncircle(HashMap<Point, PieceType> encirclingSide, HashMap<Point, PieceType> opposingSide) {
        LinkedHashSet<Point> queue = new LinkedHashSet<>();
        Set<Point> checked = new HashSet<>(getDimension() * getDimension());
        queue.addAll(getEdges());

        while(!queue.isEmpty()) {
            Point point = queue.iterator().next();
            if(opposingSide.containsKey(point))
                return false;

            if(!encirclingSide.containsKey(point)) {
                Set<Point> adjacentPoints = point.adjacentPoints(getDimension());
                adjacentPoints.forEach(adjacentPoint -> {
                    if(!queue.contains(adjacentPoint) && !checked.contains(adjacentPoint))
                        queue.add(adjacentPoint);
                });
            }

            queue.remove(point);
            checked.add(point);
        }
        return true;
    }

    /**
     * Check if the given piece has a open spot to move to.
     * @return true if there's at least one open space adjacent to the give point.
     */
    public boolean pieceCanMove(Point point) {
        if(getKing().equals(point)) {
            return getKing().adjacentPoints(getDimension()).stream()
                    .filter(adjacentPoint -> getDefenders().containsKey(adjacentPoint)
                                             || getAttackers().containsKey(adjacentPoint))
                    .collect(Collectors.toSet()).isEmpty();
        } else {
            return point.adjacentPoints(getDimension()).stream()
                    .filter(adjacentPoint -> getDefenders().containsKey(adjacentPoint)
                                             || getAttackers().containsKey(adjacentPoint)
                                             || getCornerPoints().contains(adjacentPoint)
                                             || getThrone().equals(adjacentPoint))
                    .collect(Collectors.toSet()).isEmpty();
        }
    }

    public void printBoard() {
        for(int i = getDimension(); i > 0; i--) {
            for(int j = 1; j <= getDimension(); j++) {
                System.out.print("+-");
            }
            System.out.print("+\n");
            for(int j = 1; j <= getDimension(); j++) {
                System.out.print('|');
                char print = ' ';
                Point point = new Point(j, i);

                if(getCornerPoints().contains(point)) {
                    print = 'X';
                }

                if(attackers.containsKey(point))
                    print = 'A';

                if(defenders.containsKey(point)) {
                    PieceType type = defenders.get(point);
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
        for(int j = 1; j <= getDimension(); j++) {
            System.out.print("+-");
        }
        System.out.print("+\n");
    }
}
