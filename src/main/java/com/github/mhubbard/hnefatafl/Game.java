package com.github.mhubbard.hnefatafl;

import com.github.mhubbard.hnefatafl.board.Board;
import com.github.mhubbard.hnefatafl.board.HnefataflBoard;
import com.github.mhubbard.hnefatafl.board.PieceType;
import com.github.mhubbard.hnefatafl.board.Point;

import java.util.Map;

public class Game {

    private Board board;
    private GameState state;

    Game() {
        board = new HnefataflBoard();
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
                if(board.checkEncircle(board.getAttackers(), board.getDefenders())
                        || !board.getDefenders().values().contains(PieceType.KING))
                    state = GameState.ATTACKERS_WIN;
            } else if(state == GameState.DEFENDERS_TURN) {
                state = GameState.ATTACKERS_TURN;
                Point king = board.getDefenders().entrySet().stream()
                                  .filter(entry -> entry.getValue() == PieceType.KING)
                                  .map(Map.Entry::getKey)
                                  .findFirst().orElseThrow(RuntimeException::new);
                if(board.getCornerPoints().contains(king)
                   || board.checkEncircle(board.getDefenders(), board.getAttackers()))
                    state = GameState.DEFENDERS_WIN;
            }
        }
        return success;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.board.printBoard();
        System.out.print("\n\n");
        game.movePiece(new Point(1, 8), new Point(5, 8));
        game.movePiece(new Point(6, 4), new Point(6, 3));
        game.movePiece(new Point(11, 8), new Point(7, 8));
        game.board.printBoard();
    }
}
