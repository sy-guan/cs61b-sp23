import org.junit.Test;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDequeTest {
    @Test
    public void testIteratorHasNext() {
        Deque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(5);

        Iterator<Integer> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    public void testIteratorNext() {
        Deque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(5);

        Iterator<Integer> dequeIterator = deque.iterator();
        assertThat(dequeIterator.next()).isEqualTo(2);
        assertThat(dequeIterator.next()).isEqualTo(1);
        assertThat(dequeIterator.next()).isEqualTo(5);
    }

    @Test
    // Test hasNext and Next together.
    public void testIterator() {
        Deque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(5);

        Iterator<Integer> dequeIterator = deque.iterator();
        for (int i = 0; i < 3; i++) {
            dequeIterator.next();
        }
        assertThat(dequeIterator.hasNext()).isFalse();
    }

}
