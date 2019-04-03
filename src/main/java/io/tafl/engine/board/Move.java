package io.tafl.engine.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move {

    private Point from;
    private Point to;
    private Collection<Point> captures;
}
