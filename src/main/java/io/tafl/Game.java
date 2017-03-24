package io.tafl;

import io.tafl.board.Board;
import io.tafl.board.BrandubBoard;
import io.tafl.board.HnefataflBoard;
import io.tafl.board.PieceType;
import io.tafl.board.Point;
import io.tafl.rules.FetlarRules;
import io.tafl.rules.Rules;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Game {

    private Board board;
    private Rules rules;
    private GameState state;

    Game() {
        rules = new FetlarRules();
        board = new BrandubBoard(rules);
        state = GameState.ATTACKERS_TURN;
    }

    public boolean movePiece(Point from, Point to) {
        boolean success = false;
        switch(state) {
            case ATTACKERS_TURN:
                if(board.getAttackers().containsKey(from))
                    success = board.movePiece(from, to);
                break;
            case DEFENDERS_TURN:
                if(board.getDefenders().containsKey(from))
                    success = board.movePiece(from, to);
                break;
            case ATTACKERS_WIN:
            case DEFENDERS_WIN:
                return false;
        }
        if(success) {
            if(state == GameState.ATTACKERS_TURN) {
                state = GameState.DEFENDERS_TURN;
                if(!board.getDefenders().values().contains(PieceType.KING)
                        || !canSideMove(board.getDefenders())
                        || (rules.allowEncircle() && board.checkEncircle(board.getAttackers(), board.getDefenders())))
                    state = GameState.ATTACKERS_WIN;
            } else if(state == GameState.DEFENDERS_TURN) {
                state = GameState.ATTACKERS_TURN;
                if(board.getCornerPoints().contains(board.getKing())
                        || !canSideMove(board.getAttackers())
                        || (rules.allowEncircle() && board.checkEncircle(board.getDefenders(), board.getAttackers())))
                    state = GameState.DEFENDERS_WIN;
            }
        }
        return success;
    }

    /**
     * Check if there are any available moves for a side.
     * @param pieces All of a side's pieces.
     * @return true if there are any possible moves for at least one the side's pieces, false otherwise.
     */
    private boolean canSideMove(HashMap<Point, PieceType> pieces) {
        return pieces.keySet().stream().filter(board::pieceCanMove).collect(Collectors.toSet()).isEmpty();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Game game = new Game();
        game.board.printBoard();
        System.out.print("\n\n");
        game.movePiece(new Point(1, 8), new Point(5, 8));
        game.movePiece(new Point(6, 4), new Point(6, 3));
        game.movePiece(new Point(11, 8), new Point(7, 8));
        game.board.printBoard();
        System.out.print("\n\n"+ (System.currentTimeMillis() - start));
    }
}
