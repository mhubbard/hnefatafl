package com.github.mhubbard.hnefatafl;

public class Game {

    private Board board;
    private GameState state;

    Game() {
        board = new HnefataflBoard();
        state = GameState.ATTACKERS_TURN;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.board.printBoard();
    }
}
