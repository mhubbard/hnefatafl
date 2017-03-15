package com.github.mhubbard.hnefatafl;

import lombok.Data;

@Data
public class Point {

    private int x;
    private int y;
    private PointType type;

    public Point(int x, int y, PointType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
        this.type = point.type;
    }
}
