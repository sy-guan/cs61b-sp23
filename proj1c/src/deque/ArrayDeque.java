package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque <T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int position;
        public ArrayDequeIterator() {
            // If nextFirst is at the end of the array, posistion starts from 0.
            // Else starts from nextFirst + 1
            position = nextFirst == items.length -1 ? 0 : nextFirst + 1;
        }

        @Override
        public boolean hasNext() {
            return items[position] != null;
        }

        @Override
        public T next() {
            T nextItem = items[position];
            position = position == items.length - 1 ? 0 : position + 1;
            return nextItem;
        }
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length){
            resizeUp(size*2);
        }

        items[nextFirst] = x;

        if (nextFirst == 0){
            nextFirst = items.length - 1;
        } else{
            nextFirst -= 1;
        }
        size += 1;
    }

    private void resizeUp(int capacity) {
        T[] items_new = (T[]) new Object[capacity];
        for (int i = 0; i < items.length - nextLast; i++){
            items_new[i] = get(nextLast + i);
        }
        for (int i = 0; i < nextLast; i++){
            items_new[i + items.length - nextLast] = get(i);
        }
        int lenBefore = items.length;
        items = items_new;
        nextFirst = capacity - 1;
        nextLast = capacity - lenBefore;
    }

    // Add the item to the last position of the list.
    @Override
    public void addLast(T x) {
        if (size == items.length){
            resizeUp(size*2);
        }

        items[nextLast] = x;

        if (nextLast == items.length - 1){
            nextLast = 0;
        } else{
            nextLast += 1;
        }
        size += 1;
    }

    //  Get the items in the list and put them into array list for test purpose.
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            T itemOfIndex = get(i);
            returnList.add(itemOfIndex);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size < items.length/4 && items.length >= 16){
            sizeDown(items.length/2);
        }
        if (size == 0) {
            return null;
        } else {
            int indexFirst;
            if (nextFirst == items.length - 1){
                indexFirst = 0;
            } else{
                indexFirst = nextFirst + 1;
            }

            T removed  = get(indexFirst);
            items[indexFirst] = null;
            nextFirst = indexFirst;
            size -= 1;
            return removed;
        }
    }

    @Override
    public T removeLast() {
        // For arrays of length 16 or more, usage factor should always be at least 25%.
        if (size < items.length/4 && items.length >= 16){
            sizeDown(items.length/2);
        }
        if (size == 0) {
            return null;
        } else {
            int indexLast;
            if (nextLast == 0){
                indexLast = items.length - 1;
            } else{
                indexLast = nextLast - 1;
            }

            T removed  = get(indexLast);
            items[indexLast] = null;
            nextLast = indexLast;
            size -= 1;
            return removed;
        }
    }

    public void sizeDown(int capacity) {
        T[] items_new = (T[]) new Object[capacity];
        if (nextFirst < nextLast){
            for (int i = 0; i < size - 1; i++){
                items_new[i] = get(nextFirst + i + 1);
            }
        } else{
            for (int i = 0; i < items.length - nextFirst - 1; i++){
                items_new[i] = get(nextFirst + i + 1);
            }
            for (int i = 0; i < nextLast; i++){
                items_new[items.length - nextFirst - 1 + i] = get(i);
            }
        }

        items = items_new;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > items.length - 1){
            return null;
        }
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    @Override
    public String toString(){
        return this.toList().toString();
    }

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addLast(5);
        ad.addLast(8);
        ad.addLast(10);
        ad.toList();

    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof ArrayDeque oad) {
            // check sets are of the same size
            if (oad.size != this.size) {
                return false;
            }

            // check that all of MY items are in the other array set
            Iterator<T> t1 = this.iterator();
            Iterator<T> t2 = oad.iterator();
            while (t1.hasNext() && t2.hasNext()) {
                if (!t1.next().equals(t2.next())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

