package com.github.mhubbard.hnefatafl.board;

import lombok.Value;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Value
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

    public boolean sharesAxisWith(Point point) {
        return x == point.x || y == point.y;
    }

    public boolean isOnBoard(int dimension) {
        return x > 0 && x <= dimension && y > 0 && y <= dimension;
    }

    public Set<Point> adjacentPoints(int boardDimension) {
        Set<Point> adjacentPoints = new HashSet<>();
        if(x > 1)
            adjacentPoints.add(new Point(x - 1, y));
        if(y > 1)
            adjacentPoints.add(new Point(x, y -1));
        if(x < boardDimension)
            adjacentPoints.add(new Point(x + 1, y));
        if(y < boardDimension)
            adjacentPoints.add(new Point(x, y + 1));
        return adjacentPoints;
    }

    public List<Point> getPointsBetween(Point point) {
        List<Point> pointsBetween = new LinkedList<>();
        if(x == point.x) {
            if(y > point.y) {
                for(int i = y; point.y < i ; i--)
                    pointsBetween.add(new Point(x, i - 1));
            } else {
                for(int i = point.y; i < y; i++)
                    pointsBetween.add(new Point(x, i + 1));
            }
        } else if (y == point.y) {
            if(x > point.x) {
                for(int i = x; point.x < i ; i--)
                    pointsBetween.add(new Point(i - 1, y));
            } else {
                for(int i = point.x; i < x; i++)
                    pointsBetween.add(new Point(i + 1, y));
            }
        } else return null;
        return pointsBetween;
    }
}
