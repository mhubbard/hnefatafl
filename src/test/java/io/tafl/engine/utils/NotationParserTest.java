package io.tafl.engine.utils;

import static org.junit.Assert.assertEquals;

import io.tafl.engine.board.Move;
import io.tafl.engine.board.Point;

import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NotationParserTest {

    @Test
    public void testIsValue() {
        Map<String, Boolean> tests = Map.of(
                "A1-A3", true,
                "A11-O11", true,
                "E5-E7xD4", true,
                "E5-E7xD7/E8", true,
                "C3-C7xB7/D7/C8", true,
                "A1", false,
                "a1-a3", false,
                "A1-A19", false,
                "A1-A5x", false,
                "A11-O11xO12/", false
        );


        for (Map.Entry<String, Boolean> test: tests.entrySet()) {
            boolean result = NotationParser.isValid(test.getKey());
            assertEquals(test.getKey(), test.getValue(), result);
        }
    }

    @Test
    public void testParse() {
        Map<String, Move> tests = Map.of(
                "A1-A3", new Move(new Point(1, 1), new Point(1, 3), new HashSet<>()),
                "A18-O11", new Move(new Point(1, 18), new Point(15, 11), new HashSet<>()),
                "E5-R7xD4", new Move(new Point(5, 5), new Point(18, 7), Set.of(new Point(4, 4))),
                "C3-C7xB7/D7/C8", new Move(new Point(3, 3), new Point(3, 7), Set.of(new Point(2, 7), new Point(4, 7), new Point(3, 8)))
        );

        for (Map.Entry<String, Move> test: tests.entrySet()) {
            Move result = NotationParser.parse(test.getKey());
            assertEquals(test.getKey(), test.getValue(), result);
        }
    }
}
