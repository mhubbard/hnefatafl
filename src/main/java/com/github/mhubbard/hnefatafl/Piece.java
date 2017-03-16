package com.github.mhubbard.hnefatafl;

import lombok.Data;

@Data
public class Piece {

    private Point point;
    private PieceType type;

    public Piece(Point point, PieceType type) {
        this.point = point;
        this.type = type;
    }
}
