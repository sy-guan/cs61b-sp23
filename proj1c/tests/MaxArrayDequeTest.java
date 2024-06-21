import deque.*;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    @Test
    public void testMaxArrayDequeZero() {
        Comparator<Integer> c = new IntegerComparator();
        Deque<Integer> deque = new MaxArrayDeque<>(c);
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testMaxArrayDequeMax1() {
        Comparator<Integer> c = new IntegerComparator();
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(c);
        deque.addLast(0);
        deque.addLast(2);
        deque.addLast(4);

        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.max()).isEqualTo(4);
    }

    @Test
    public void testMaxArrayDequeMax2() {
        Comparator<String> comp = new StringComparator();
        Comparator<String> c = new StringLengthComparator();

        MaxArrayDeque<String> deque = new MaxArrayDeque<>(c);
        deque.addLast("a");
        deque.addLast("ca");
        deque.addLast("bcd");

        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.max(comp)).isEqualTo("ca");
        assertThat(deque.max()).isEqualTo("bcd");
    }
}
