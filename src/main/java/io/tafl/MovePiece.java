package io.tafl;

import io.tafl.board.Board;
import io.tafl.board.PieceType;
import io.tafl.board.Point;
import io.tafl.rules.Rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MovePiece {
    
    private Rules rules;
    private Board board;
    
    MovePiece(Rules rules, Board board) {
        this.rules = rules;
        this.board = board;
    }

    /**
     * Move the piece occupying the from point.
     * @param from Point on the board the piece is moving from.
     * @param to Point on the board the piece is moving to.
     * @return True if valid move, false otherwise.
     */
    public boolean movePiece(Point from, Point to) {
        if(from.equals(to) || !from.sharesAxisWith(to)
           || !from.isOnBoard(board.getDimension()) || !to.isOnBoard(board.getDimension()))
            return false;

        boolean isAttacker = false;
        PieceType pieceType;
        if(board.getDefenders().containsKey(from)) {
            pieceType = board.getDefenders().get(from);
        } else if(board.getAttackers().containsKey(from)) {
            isAttacker = true;
            pieceType = board.getAttackers().get(from);
        } else return false;

        if(pieceType == PieceType.KING && rules.singleMoveKing()
           && !board.getKing().adjacentPoints(board.getDimension()).contains(to))
            return false;

        if(pieceType != PieceType.KING && board.getCornerPoints().contains(to))
            return false;

        if(board.getThrone().equals(to) && (pieceType != PieceType.KING || !rules.canKingReenterThrone()))
            return false;

        for(Point point: from.getPathTo(to))
            if(board.getAttackers().containsKey(point) || board.getDefenders().containsKey(point))
                return false;

        if(isAttacker) {
            board.getAttackers().remove(from);
            board.getAttackers().put(to, pieceType);
            checkCaptures(to, board.getDefenders(), board.getAttackers()).forEach(board.getDefenders()::remove);
        } else {
            board.getDefenders().remove(from);
            board.getDefenders().put(to, pieceType);
            checkCaptures(to, board.getAttackers(), board.getDefenders()).forEach(board.getAttackers()::remove);
            if(pieceType == PieceType.KING)
                board.setKing(to);
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
        if(!board.getKing().equals(adjacent)) {
            if (isAllyOrHostile(sandwichingPoint, alliedPieces))
                captures.add(adjacent);
        } else if(checkKingCaptured())
            captures.add(adjacent);
    }

    /**
     * Check if the board.getKing() is captured (board.getAttackers()/hostile spaces surrounding all 4 sides).
     * @return true if the board.getKing() is surrounded, false otherwise.
     */
    private boolean checkKingCaptured() {
        Set<Point> adjacentPoints = board.getKing().adjacentPoints(board.getDimension());
        return adjacentPoints.stream().filter(point -> isAllyOrHostile(point, board.getAttackers()))
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
        return !(board.getKing().equals(point) && rules.isWeaponlessKing())
               && (alliedPieces.containsKey(point)
                   || board.getCornerPoints().contains(point)
                   || (board.getThrone().equals(point) && !board.getThrone().equals(board.getKing())));
    }
}
