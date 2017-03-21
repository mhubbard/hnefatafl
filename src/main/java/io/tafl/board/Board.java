package io.tafl.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Board {

    protected HashMap<Point, PieceType> attackers;
    protected HashMap<Point, PieceType> defenders;
    protected Point king;

    public abstract int getDimension();
    public abstract Set<Point> getEdges();
    public abstract Set<Point> getCornerPoints();
    public abstract Point getThrone();

    public HashMap<Point, PieceType> getAttackers() {
        return attackers;
    }

    public HashMap<Point, PieceType> getDefenders() {
        return defenders;
    }

    public Point getKing() {
        return king;
    }
    
    /**
     * Move the piece occupying the from point.
     * @param from Point on the board the piece is moving from.
     * @param to Point on the board the piece is moving to.
     * @return True if valid move, false otherwise.
     */
    public boolean movePiece(Point from, Point to) {
        if(from.equals(to) || !from.sharesAxisWith(to)
           || !from.isOnBoard(getDimension()) || !to.isOnBoard(getDimension()))
            return false;

        boolean isAttacker = false;
        PieceType pieceType;
        if(defenders.containsKey(from)) {
            pieceType = defenders.get(from);
        } else if(attackers.containsKey(from)) {
            isAttacker = true;
            pieceType = attackers.get(from);
        } else return false;

        if(pieceType != PieceType.KING && (getCornerPoints().contains(to) || getThrone().equals(to)))
            return false;

        for(Point point: from.getPathTo(to))
            if(attackers.containsKey(point) || defenders.containsKey(point))
                return false;

        if(isAttacker) {
            attackers.remove(from);
            attackers.put(to, pieceType);
            checkCaptures(to, defenders, attackers).forEach(defenders::remove);
        } else {
            defenders.remove(from);
            defenders.put(to, pieceType);
            checkCaptures(to, attackers, defenders).forEach(attackers::remove);
            if(pieceType == PieceType.KING)
                king = to;
        }
        return true;
    }

    /**
     * Check if the moved piece has captured any enemy pieces.
     * @param to The point the moved piece moved to.
     * @param enemyPieces All enemies to the moved piece.
     * @param alliedPieces All allies of the moved piece.
     * @return Captured enemies.
     */
    private Set<Point> checkCaptures(Point to, HashMap<Point, PieceType> enemyPieces, HashMap<Point, PieceType> alliedPieces) {
        Set<Point> captures = new HashSet<>(3);
        Point adjacentWest = new Point(to.getX() - 1 , to.getY());
        if(to.getX() > 2 && enemyPieces.containsKey(adjacentWest)) {
            if(!king.equals(adjacentWest)) {
                Point sandwichingPoint = new Point(to.getX() - 2, to.getY());
                if (isAllyOrHostile(sandwichingPoint, alliedPieces))
                    captures.add(adjacentWest);
            } else if(checkKingCaptured())
                captures.add(adjacentWest);
        }
        Point adjacentEast = new Point(to.getX() + 1 , to.getY());
        if(to.getX() < 10 && enemyPieces.containsKey(adjacentEast)) {
            if(!king.equals(adjacentEast)) {
                Point sandwichingPoint = new Point(to.getX() + 2, to.getY());
                if (isAllyOrHostile(sandwichingPoint, alliedPieces))
                    captures.add(adjacentEast);
            } else if(checkKingCaptured())
                captures.add(adjacentEast);
        }
        Point adjacentNorth = new Point(to.getX() , to.getY() + 1);
        if(to.getY() < 10 && enemyPieces.containsKey(adjacentNorth)) {
            if(!king.equals(adjacentNorth)) {
                Point sandwichingPoint = new Point(to.getX(), to.getY() + 2);
                if (isAllyOrHostile(sandwichingPoint, alliedPieces))
                    captures.add(adjacentNorth);
            } else if(checkKingCaptured())
                captures.add(adjacentNorth);
        }
        Point adjacentSouth = new Point(to.getX() , to.getY() - 1);
        if(to.getY() > 2 && enemyPieces.containsKey(adjacentSouth)) {
            if(!king.equals(adjacentSouth)) {
                Point sandwichingPoint = new Point(to.getX(), to.getY() - 2);
                if (isAllyOrHostile(sandwichingPoint, alliedPieces))
                    captures.add(adjacentSouth);
            } else if(checkKingCaptured())
                captures.add(adjacentSouth);
        }
        return captures;
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
        if(king.equals(point)) {
            return !king.adjacentPoints(getDimension()).stream()
                        .filter(adjacentPoint -> defenders.containsKey(adjacentPoint)
                                                 || attackers.containsKey(adjacentPoint))
                        .collect(Collectors.toSet()).isEmpty();
        } else {
            return !point.adjacentPoints(getDimension()).stream()
                         .filter(adjacentPoint -> defenders.containsKey(adjacentPoint)
                         || attackers.containsKey(adjacentPoint)
                         || getCornerPoints().contains(adjacentPoint)
                         || getThrone().equals(adjacentPoint))
                    .collect(Collectors.toSet()).isEmpty();
        }
    }

    /**
     * Check if the king is captured (attackers/hostile spaces surrounding all 4 sides).
     * @return true if the king is surrounded, false otherwise.
     */
    private boolean checkKingCaptured() {
        Set<Point> adjacentPoints = king.adjacentPoints(getDimension());
        return adjacentPoints.stream().filter(point -> isAllyOrHostile(point, attackers))
                       .collect(Collectors.toSet())
                       .size() == 4;
    }

    /**
     * Check if a point is either an ally, corner, or empty throne.
     * @param point Point on the board to check.
     * @param alliedPieces A map of all friendly pieces and their types.
     * @return true if point is an ally or hostile (corner/empty throne), false otherwise.
     */
    private boolean isAllyOrHostile(Point point, HashMap<Point, PieceType> alliedPieces) {
        return alliedPieces.containsKey(point) || getCornerPoints().contains(point)
               || (getThrone().equals(point) && !getThrone().equals(king));
    }

    public void printBoard() {
        for(int i = getDimension(); i > 0; i--) {
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+\n");
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
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+\n");
    }
}
