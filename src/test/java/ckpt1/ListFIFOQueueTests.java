package ckpt1;

import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ListFIFOQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListFIFOQueueTests extends WorklistGradingTests {

    @BeforeEach
    public void init() {
        STUDENT_STR = new ListFIFOQueue<>();
        STUDENT_DOUBLE = new ListFIFOQueue<>();
        STUDENT_INT = new ListFIFOQueue<>();
    }

    @Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    public void checkStructure() {
        WorkList<Integer> queue = new ListFIFOQueue<>();

        // Fill
        for (int i = 0; i < 1000; i++) {
            queue.add(i);
            assertEquals(0, queue.peek().intValue());
            assertTrue(queue.hasWork());
            assertEquals((i + 1), queue.size());
        }

        // Empty
        for (int i = 0; i < 999; i++) {
            assertTrue(queue.hasWork());
            assertEquals(i, queue.peek().intValue());
            assertEquals(i, queue.next().intValue());
            assertEquals(999 - i, queue.size());
        }
    }
}
