import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

     //Below, you'll write your own tests for LinkedListDeque.
    @Test
    // This test isEmpty method.
    public void isEmptyNotEmptyTest(){
        Deque<Integer> lld1 = new LinkedListDeque<>();

        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(0);

        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    // In this test, test the size method when size = 0.
    public void testSizeZero(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        int expected = 0;

        assertThat(lld1.size()).isEqualTo(expected);
    }

    @Test
    // In this test, test the size method when size = 1.
    public void testSizeOne(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(5);
        int expected = 1;

        assertThat(lld1.size()).isEqualTo(expected);
    }

    @Test
    // It tests the get method when index is less than 0 or out of index
    public void testGetNotValid(){
        Deque<Integer> lld1 = new LinkedListDeque<>();

        assertThat(lld1.get(-5)).isEqualTo(null);

        lld1.addFirst(5);

        assertThat(lld1.get(1)).isEqualTo(null);
    }

    @Test
    // It tests the get method that get the first item at index 0.
    public void testGetZero(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(5);
        int expected = 5;

        assertThat(lld1.get(0)).isEqualTo(expected);
    }

    @Test
    // It tests the get method that get the 3rd item at index 2.
    public void testGetMultiple(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(2);
        lld1.addLast(4);
        lld1.addFirst(8);
        lld1.addFirst(5);
        int expected = 2;

        assertThat(lld1.get(2)).isEqualTo(expected);
    }

    @Test
    // It tests the getRecursive method that get the first item at index 0.
    public void testGetRecursiveZero(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(5);
        int expected = 5;

        assertThat(lld1.getRecursive(0)).isEqualTo(expected);
    }

    @Test
    // In this test, test getRecursive when index is not valid.
    public void testGetRecursiveNotValid(){
        Deque<Integer> lld1 = new LinkedListDeque<>();

        assertThat(lld1.getRecursive(-5)).isEqualTo(null);

        lld1.addFirst(5);

        assertThat(lld1.getRecursive(1)).isEqualTo(null);
    }

    @Test
    // It tests the getRecursive method that get the 3rd item at index 2.
    public void testGetRecursiveMultiple(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(2);
        lld1.addLast(4);
        lld1.addFirst(8);
        lld1.addFirst(5);
        int expected = 4;

        assertThat(lld1.getRecursive(3)).isEqualTo(expected);
    }

    @Test
    // This test removeFirst when Deque is Empty
    public void removeFirstEmptyTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        assertThat(lld1.removeFirst()).isNull();
    }

    @Test
    // This tests removeFirst with five integers in the list.
    public void removeFirstTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        int firstActual = lld1.removeFirst();
        int firstExpected = -2;

        assertThat(firstActual).isEqualTo(firstExpected);
        assertThat(lld1.toList()).containsExactly(-1, 0, 1, 2).inOrder();
    }

    @Test
    // This test removeLast when Deque is Empty
    public void removeLastEmptyTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        assertThat(lld1.removeLast()).isNull();
    }

    @Test
    // This tests removeLast with five integers in the list.
    public void removeLastTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        int firstActual = lld1.removeLast();
        int firstExpected = 2;

        assertThat(firstActual).isEqualTo(firstExpected);
        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1).inOrder();
    }
}