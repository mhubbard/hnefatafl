package io.tafl.engine.board;

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

    /**
     * Check if this point shares an x or y value with the given point.
     * @param point Point to check x and y values of.
     * @return true if the two points share an axis, false otherwise.
     */
    public boolean sharesAxisWith(Point point) {
        return x == point.x || y == point.y;
    }

    /**
     * Does this point exist on the board, given it's dimensions.
     * @param dimension The dimension of the board.
     * @return true if this point is on the board, false otherwise.
     */
    public boolean isOnBoard(int dimension) {
        return x > 0 && x <= dimension && y > 0 && y <= dimension;
    }

    /**
     * Retrieves all adjacent points to this one so long as their on the board.
     * @param boardDimension The dimension of the board.
     * @return A set off all adjacent points.
     */
    public Set<Point> adjacentPoints(int boardDimension) {
        Set<Point> adjacentPoints = new HashSet<>(4);
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

    /**
     * Retrieve the path of points from this point to the one passed, inclusive.
     * @param point The destination point.
     * @return An ordered list of points from this to the destination, including the destination.
     */
    public List<Point> getPathTo(Point point) {
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
        }
        return pointsBetween;
    }
}
