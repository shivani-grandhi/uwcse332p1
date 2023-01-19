package ckpt1;

import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ListFIFOQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ListFIFOQueueTests {

    @Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    public void test_addPeekNext_manyNumbers_correctStructure() {
        WorkList<Integer> STUDENT_QUEUE = new ListFIFOQueue<>();

        // Add numbers 0 - 999 (inclusive) to the queue
        for (int i = 0; i < 1000; i++) {
            // Add the number
            STUDENT_QUEUE.add(i);
            // Checks if the front of the queue is 0
            assertEquals(0, STUDENT_QUEUE.peek());
            // Checks if the queue is not empty
            assertTrue(STUDENT_QUEUE.hasWork());
            // Checks if the size is correct
            assertEquals((i + 1), STUDENT_QUEUE.size());
        }

        // Empty out the queue
        for (int i = 0; i < 999; i++) {
            // Checks if the queue is not empty
            assertTrue(STUDENT_QUEUE.hasWork());
            // Checks if the top of the queue is the correct number
            assertEquals(i, STUDENT_QUEUE.peek());
            // Removing the top of the queue should be the correct number
            assertEquals(i, STUDENT_QUEUE.next());
            // Checks if the size is correct
            assertEquals(999 - i, STUDENT_QUEUE.size());
        }
    }

    @Test
    public void testHasWork() {
        WorkList<Integer> STUDENT_INT = new ListFIFOQueue<>();
        assertFalse(STUDENT_INT.hasWork());
    }

    @Test
    public void testHasWorkAfterAdd() {
        WorkList<Integer> STUDENT_INT = new ListFIFOQueue<>();
        STUDENT_INT.add(1);
        assertTrue(STUDENT_INT.hasWork());
    }

    @Test
    public void testHasWorkAfterAddRemove() {
        WorkList<Double> STUDENT_DOUBLE = new ListFIFOQueue<>();
        for (int i = 0; i < 1000; i++) {
            STUDENT_DOUBLE.add(Math.random());
        }
        for (int i = 0; i < 1000; i++) {
            STUDENT_DOUBLE.next();
        }
        assertFalse(STUDENT_DOUBLE.hasWork());
    }
    @Test
    public void testPeekHasException() {
        WorkList<Integer> STUDENT_INT = new ListFIFOQueue<>();
        assertTrue(doesPeekThrowException(STUDENT_INT));

        addAndRemove(STUDENT_INT, 42, 10);
        assertTrue(doesPeekThrowException(STUDENT_INT));
    }

    @Test
    public void testNextHasException() {
        WorkList<Integer> STUDENT_INT = new ListFIFOQueue<>();
        assertTrue(doesNextThrowException(STUDENT_INT));

        addAndRemove(STUDENT_INT, 42, 10);
        assertTrue(doesNextThrowException(STUDENT_INT));
    }
    @Test
    public void testClear() {
        WorkList<String> STUDENT_STR = new ListFIFOQueue<>();
        addAll(STUDENT_STR, new String[]{"Beware", "the", "Jabberwock", "my", "son!"});

        assertTrue(STUDENT_STR.hasWork());
        assertEquals(5, STUDENT_STR.size());

        STUDENT_STR.clear();
        assertFalse(STUDENT_STR.hasWork());
        assertEquals(0, STUDENT_STR.size());
        assertTrue(doesPeekThrowException(STUDENT_STR));
        assertTrue(doesNextThrowException(STUDENT_STR));
    }

    // UTILITY METHODS

    protected static <E> void addAll(WorkList<E> worklist, E[] values) {
        for (E value : values) {
            worklist.add(value);
        }
    }

    protected static <E> void addAndRemove(WorkList<E> worklist, E value, int amount) {
        for (int i = 0; i < amount; i++) {
            worklist.add(value);
        }
        for (int i = 0; i < amount; i++) {
            worklist.next();
        }
    }

    protected static <E> boolean doesPeekThrowException(WorkList<E> worklist) {
        try {
            worklist.peek();
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    protected static <E> boolean doesNextThrowException(WorkList<E> worklist) {
        try {
            worklist.next();
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

}
