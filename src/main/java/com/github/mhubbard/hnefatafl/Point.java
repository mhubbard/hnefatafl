package com.github.mhubbard.hnefatafl;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public boolean isOnBoard(int dimension) {
        return x > 0 && x <= dimension && y > 0 && y <= dimension;
    }

    public boolean sharesAxisWith(Point point) {
        return x == point.x || y == point.y;
    }

    public List<Point> getPointsBetween(Point point) {
        List<Point> pointsBetween = new LinkedList<>();
        int from;
        int to;
        if(x == point.x) {
            if(y > point.y) {
                to = y;
                from = point.y;
            } else {
                to = point.y;
                from = y;
            }
            for(int i = from; from <= to; i++)
                pointsBetween.add(new Point(x, i));
        } else if (y == point.y) {
            if(x > point.x) {
                to = x;
                from = point.x;
            } else {
                to = point.x;
                from = x;
            }
            for(int i = from; from <= to; i++)
                pointsBetween.add(new Point(i, y));
        } else return null;
        return pointsBetween;
    }
}
