package io.tafl.engine;

import io.tafl.engine.board.Board;
import io.tafl.engine.board.Move;
import io.tafl.engine.board.PieceType;
import io.tafl.engine.board.Point;
import io.tafl.engine.rules.Rules;
import io.tafl.engine.utils.NotationParser;

import lombok.Data;

import java.util.HashMap;
import java.util.stream.Collectors;

@Data
public class Game {

    private Board board;
    private Rules rules;
    private GameState state;

    public Game(Rules rules, Board board, GameState state) {
        this.rules = rules;
        this.board = board;
        this.state = state;
    }

    public boolean movePiece(String moveNotation) {
        try {
            Move move = NotationParser.parse(moveNotation);
            return movePiece(move.getFrom(), move.getTo());
        } catch(Exception ignored) { }
        return false;
    }

    private boolean movePiece(Point from, Point to) {
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
                        || (rules.isAllowEncircle() && board.checkEncircle(board.getAttackers(), board.getDefenders())))
                    state = GameState.ATTACKERS_WIN;
            } else if(state == GameState.DEFENDERS_TURN) {
                state = GameState.ATTACKERS_TURN;
                if(kingEscaped()
                        || !canSideMove(board.getAttackers())
                        || (rules.isAllowEncircle() && board.checkEncircle(board.getDefenders(), board.getAttackers())))
                    state = GameState.DEFENDERS_WIN;
            }
        }
        return success;
    }

    /**
     * Check if the king has successfully escaped, by checking if it has reaches an
     * edge or corner piece depending on rules
     * @return true if the king escaped, false otherwise
     */
    private boolean kingEscaped() {
        return (rules.isEdgeEscape() && board.getEdges().contains(board.getKing()))
               || board.getCornerPoints().contains(board.getKing());
    }

    /**
     * Check if there are any available moves for a side.
     * @param pieces All of a side's pieces.
     * @return true if there are any possible moves for at least one the side's pieces, false otherwise.
     */
    private boolean canSideMove(HashMap<Point, PieceType> pieces) {
        return pieces.keySet().stream().filter(board::pieceCanMove).collect(Collectors.toSet()).isEmpty();
    }
}
