package io.tafl.engine.utils;

import io.tafl.engine.board.Move;
import io.tafl.engine.board.Point;

import java.util.HashSet;
import java.util.regex.Pattern;

public class NotationParser {
    private static final String notationRegex = "[A-R]([1-9]|1[1-8])-[A-R]([1-9]|1[1-8])(x([A-R]([1-9]|1[1-8]))(\\/([A-R]([1-9]|1[1-8])))?(\\/([A-R]([1-9]|1[1-8])))?)?";

    public static boolean isValid(String moveNotation) {
        return Pattern.compile(notationRegex).matcher(moveNotation).matches();
    }

    public static Move parse(String moveNotation) {
        Move move = new Move();
        move.setCaptures(new HashSet<>());
        String[] splitCaptures = moveNotation.split("x");
        String moveString = splitCaptures[0];
        String capturesString;
        if (splitCaptures.length > 1) {
            capturesString = splitCaptures[1];
            String[] captures = capturesString.split("/");
            for (String capture: captures) {
                int captureX = (capture.charAt(0) - 'A') + 1;
                int captureY;
                if(capture.length() > 2) {
                    captureY = Integer.parseInt(capture.substring(1));
                } else {
                    captureY = Character.getNumericValue(capture.charAt(1));
                }
                move.getCaptures().add(new Point(captureX, captureY));
            }
        }
        String[] splitMoves = moveString.split("-");
        String from = splitMoves[0];
        String to = splitMoves[1];
        int fromX = (from.charAt(0) - 'A') + 1;
        int fromY;
        if (from.length() > 2) {
            fromY = Integer.parseInt(from.substring(1));
        } else {
            fromY = Character.getNumericValue(from.charAt(1));
        }
        int toX = (to.charAt(0) - 'A') + 1;
        int toY;
        if (to.length() > 2) {
            toY = Integer.parseInt(to.substring(1));
        } else {
            toY = Character.getNumericValue(to.charAt(1));
        }
        move.setFrom(new Point(fromX, fromY));
        move.setTo(new Point(toX, toY));
        return move;
    }
}
