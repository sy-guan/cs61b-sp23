import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic(){
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addFirst("back"); // after this call we expect: [null, null, null, null, "back", null, null, null]
        assertThat(lld1.toList()).containsExactly(null, null, null, null, "back", null, null, null).inOrder();

        lld1.addFirst("middle"); // after this call we expect: [null, null, null, "middle", "back", null, null, null]
        assertThat(lld1.toList()).containsExactly(null, null, null, "middle", "back", null, null, null).inOrder();

        lld1.addFirst("front"); // after this call we expect: [null, null, "front", "middle", "back", null, null, null]
        assertThat(lld1.toList()).containsExactly(null, null, "front", "middle", "back", null, null, null).inOrder();
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("front"); // after this call we expect: [null, null, null, null, null, "front", null, null]
        lld1.addLast("middle"); // after this call we expect: [null, null, null, null, null, "front", "middle", null]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly(null, null, null, null, null, "front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addLast(0);   // [null, null, null, null, null, 0, null, null]
        lld1.addLast(1);   // [null, null, null, null, null, 0, 1, null]
        lld1.addFirst(-1); // [null, null, null, null, -1, 0, 1, null]
        lld1.addLast(2);   // [null, null, null, null, -1, 0, 1, 2]
        lld1.addFirst(-2); // [null, null, null, -2, -1, 0, 1, 2]
        lld1.addLast(3); // [3, null, null, -2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(3, null, null, -2, -1, 0, 1, 2).inOrder();
    }

    @Test
    /** This test check the correctness of resize function. */
    public void resizeUpTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addLast(0);   // [null, null, null, null, null, 0, null, null]
        lld1.addLast(1);   // [null, null, null, null, null, 0, 1, null]
        lld1.addFirst(-1); // [null, null, null, null, -1, 0, 1, null]
        lld1.addLast(2);   // [null, null, null, null, -1, 0, 1, 2]
        lld1.addFirst(-2); // [null, null, null, -2, -1, 0, 1, 2]
        lld1.addLast(3); // [3, null, null, -2, -1, 0, 1, 2]
        lld1.addFirst(-3); // [3, null, -3, -2, -1, 0, 1, 2]
        lld1.addLast(4); // [3, 4, -3, -2, -1, 0, 1, 2]
        lld1.addLast(5); // [-3, -2, -1, 0, 1, 2, 3, 4, 5, null, null, null, null, null, null, null]

        assertThat(lld1.toList()).containsExactly(-3, -2, -1, 0, 1, 2, 3, 4, 5, null, null, null, null, null, null, null).inOrder();
    }

    @Test
    // This test isEmpty method.
    public void isEmptyNotEmptyTest(){
        Deque<Integer> lld1 = new ArrayDeque<>();

        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(0);

        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    // In this test, test the size method when size = 0.
    public void testSizeZero(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        int expected = 0;

        assertThat(lld1.size()).isEqualTo(expected);
    }

    @Test
    // In this test, test the size method when size = 1.
    public void testSizeOne(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(5);
        int expected = 1;

        assertThat(lld1.size()).isEqualTo(expected);
    }

    @Test
    // It tests the get method when index is less than 0 or out of index
    public void testGetNotValid(){
        Deque<Integer> lld1 = new ArrayDeque<>();

        assertThat(lld1.get(-5)).isEqualTo(null);

        lld1.addFirst(5);

        assertThat(lld1.get(8)).isEqualTo(null);
    }

    @Test
    // It tests the get method that get the first item at index 0.
    public void testGetZero(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(5); // [null, null, null, null, 5, null, null, null]
        int expected = 5;

        assertThat(lld1.get(4)).isEqualTo(expected);
    }

    @Test
    // It tests the get method that get the 3rd item at index 2.
    public void testGetMultiple(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(2); // [null, null, null, null, 2, null, null, null]
        lld1.addLast(4); // [null, null, null, null, 2, 4, null, null]
        lld1.addFirst(8); // [null, null, null, 8, 2, 4, null, null]
        lld1.addFirst(5); // [null, null, 5, 8, 2, 4, null, null]
        int expectedOne = 5;
        int expectedTwo = 4;

        assertThat(lld1.get(2)).isEqualTo(expectedOne);
        assertThat(lld1.get(5)).isEqualTo(expectedTwo);
    }

    @Test
    // It tests the simple case of removeFirst method.
    public void removeFirstBasicTest(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(2); // [null, null, null, null, 2, null, null, null]
        lld1.removeFirst();

        assertThat(lld1.get(4)).isEqualTo(null);
    }

    @Test
    // It tests the multiple calls of removeFirst method.
    public void removeFirstMultipleTest(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(2); // [null, null, null, null, 2, null, null, null]
        lld1.addLast(4); // [null, null, null, null, 2, 4, null, null]
        lld1.addFirst(8); // [null, null, null, 8, 2, 4, null, null]
        lld1.addFirst(5); // [null, null, 5, 8, 2, 4, null, null]
        lld1.addLast(3); // [null, null, 5, 8, 2, 4, 3, null]
        int removedOne = lld1.removeFirst(); // [null, null, null, 8, 2, 4, 3, null]
        int removedTwo =lld1.removeFirst(); // [null, null, null, null, 2, 4, 3, null]
        int removedThree =lld1.removeFirst(); // [null, null, null, null, null, 4, 3, null]

        assertThat(removedOne).isEqualTo(5);
        assertThat(removedTwo).isEqualTo(8);
        assertThat(removedThree).isEqualTo(2);

        assertThat(lld1.toList()).containsExactly(null, null, null, null, null, 4, 3, null).inOrder();
    }

    @Test
    /* Test nothing to remove, after calling removeFirst, it should return null*/
    public void removeFirstNothingToRemove(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        assertThat(lld1.removeFirst()).isEqualTo(null);
    }

    @Test
    // It tests the simple case of removeFirst method.
    public void removeLastBasicTest(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(2); // [null, null, null, null, 2, null, null, null]
        assertThat(lld1.get(5)).isEqualTo(2);

        int remove = lld1.removeLast();
        assertThat(remove).isEqualTo(2);
        assertThat(lld1.get(5)).isEqualTo(null);
    }

    @Test
    // It tests the simple case of removeFirst method.
    public void removeLastMultipleTest(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(2); // [null, null, null, null, 2, null, null, null]
        lld1.addLast(4); // [null, null, null, null, 2, 4, null, null]
        lld1.addFirst(8); // [null, null, null, 8, 2, 4, null, null]
        lld1.addFirst(5); // [null, null, 5, 8, 2, 4, null, null]
        int removedOne = lld1.removeLast(); // [null, null, 5, 8, 2, null, null, null]
        int removedTwo =lld1.removeLast(); // [null, null, 5, 8, null, null, null, null]
        int removedThree =lld1.removeLast(); // [null, null, 5, null, null, null, null, null]

        assertThat(removedOne).isEqualTo(4);
        assertThat(removedTwo).isEqualTo(2);
        assertThat(removedThree).isEqualTo(8);

        assertThat(lld1.toList()).containsExactly(null, null, 5, null, null, null, null, null).inOrder();
    }

    @Test
    /* Test nothing to remove, after calling removeFirst, it should return null*/
    public void removeLastNothingToRemove(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        assertThat(lld1.removeLast()).isEqualTo(null);
    }

    @Test
    /** This test check the correctness of resizeDown function. */
    public void resizeDownDifficultTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        for(int i = 0; i < 18; i++) {
            lld1.addLast(0); //[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,null,null,null,null,null,null,null,null,null,null,null,1,1,1]
        }
        lld1.addFirst(1);
        lld1.addFirst(1);
        lld1.addFirst(1);
        for (int i = 0; i < 14; i++) {
            lld1.removeLast(); //[0,0,0,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,1,1,1]
        }
        lld1.removeFirst();//[0,0,0,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,1,1,1]
        //After resize [1,1,1,0,0,0,0,null,null,null,null,null,null,null,null,null] -> After removeFirst [null,1,1,0,0,0,0,null,null,null,null,null,null,null,null,null]

        assertThat(lld1.toList()).containsExactly(null,1,1,0,0,0,0,null,null,null,null,null,null,null,null,null).inOrder();
    }

    @Test
    /** This test check the correctness of resizeDown function. */
    public void resizeDownBasicTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        for(int i = 0; i < 31; i++) {
            lld1.addLast(0); //[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,null]
        }
        for (int i = 0; i < 13; i++) {
            lld1.removeFirst(); //[null,null,null,null,null,null,null,null,null,null,null,null,null,0,0,0,0,0,null,null,null,null,null,null,null,null,null,null,null,null,null,null]
            lld1.removeLast();
        }

        assertThat(lld1.toList()).containsExactly(null,0,0,0,0,0,null, null,null, null, null, null, null, null, null, null).inOrder();
    }
}
