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

    private void setKing(Point king) {
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
        Set<Point> checked = new HashSet<>(getDimension() * getDimension());
        LinkedHashSet<Point> queue = new LinkedHashSet<>(getEdges());

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
        if(getDefenders().containsKey(from)) {
            pieceType = getDefenders().get(from);
        } else if(getAttackers().containsKey(from)) {
            isAttacker = true;
            pieceType = getAttackers().get(from);
        } else return false;

        if(pieceType == PieceType.KING && rules.singleMoveKing()
           && !getKing().adjacentPoints(getDimension()).contains(to))
            return false;

        if(pieceType != PieceType.KING && getCornerPoints().contains(to))
            return false;

        if(getThrone().equals(to) && (pieceType != PieceType.KING || !rules.canKingReenterThrone()))
            return false;

        for(Point point: from.getPathTo(to))
            if(getAttackers().containsKey(point) || getDefenders().containsKey(point))
                return false;

        if(isAttacker) {
            getAttackers().remove(from);
            getAttackers().put(to, pieceType);
            checkCaptures(to, getDefenders(), getAttackers()).forEach(getDefenders()::remove);
        } else {
            getDefenders().remove(from);
            getDefenders().put(to, pieceType);
            checkCaptures(to, getAttackers(), getDefenders()).forEach(getAttackers()::remove);
            if(pieceType == PieceType.KING)
                setKing(to);
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
            checkCapture(adjacentWest, new Point(to.getX() - 2, to.getY()), alliedPieces, captures);
        }
        Point adjacentEast = new Point(to.getX() + 1 , to.getY());
        if(to.getX() < 10 && enemyPieces.containsKey(adjacentEast)) {
            checkCapture(adjacentEast, new Point(to.getX() + 2, to.getY()), alliedPieces, captures);
        }
        Point adjacentNorth = new Point(to.getX() , to.getY() + 1);
        if(to.getY() < 10 && enemyPieces.containsKey(adjacentNorth)) {
            checkCapture(adjacentNorth, new Point(to.getX(), to.getY() + 2), alliedPieces, captures);
        }
        Point adjacentSouth = new Point(to.getX() , to.getY() - 1);
        if(to.getY() > 2 && enemyPieces.containsKey(adjacentSouth)) {
            checkCapture(adjacentSouth, new Point(to.getX(), to.getY() - 2), alliedPieces, captures);
        }
        return captures;
    }

    /**
     * Helper method to checkCaptures, checking if a point is sandwiched between two enemy pieces.
     * Do not call outside of checkCaptures.
     * @param adjacent Potentially captured point.
     * @param sandwichingPoint Point on the other side of the adjacent point.
     * @param alliedPieces Pieces allied to the piece that was moved.
     * @param captures Set of captured points.
     */
    private void checkCapture(Point adjacent, Point sandwichingPoint, HashMap<Point, PieceType> alliedPieces, Set<Point> captures) {
        if(!getKing().equals(adjacent)
           || (rules.isWeakKing()
               && !getThrone().equals(getKing())
               && !getKing().adjacentPoints(getDimension()).contains(getThrone()))) {
            if (isAllyOrHostile(sandwichingPoint, alliedPieces))
                captures.add(adjacent);
        } else if(checkKingSurrounded())
            captures.add(adjacent);
    }

    /**
     * Check if the board.getKing() is captured (board.getAttackers()/hostile spaces surrounding all 4 sides).
     * @return true if the board.getKing() is surrounded, false otherwise.
     */
    private boolean checkKingSurrounded() {
        Set<Point> adjacentPoints = getKing().adjacentPoints(getDimension());
        return adjacentPoints.stream().filter(point -> isAllyOrHostile(point, getAttackers()))
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
        return !(getKing().equals(point) && rules.isWeaponlessKing())
               && (alliedPieces.containsKey(point)
                   || getCornerPoints().contains(point)
                   || (getThrone().equals(point) && !getThrone().equals(getKing())));
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
