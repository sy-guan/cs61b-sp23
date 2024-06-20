package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    public Node sentinel;
    public int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        size = 0;
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node current;
        public LinkedListIterator() {
            current = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return current.item != null;
        }

        @Override
        public T next() {
            T nextItem = current.item;
            current = current.next;
            return nextItem;
        }
    }

    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    //  Add item to the front of the List. If the list is empty, set the previous of the sentinel to the item;
    @Override
    public void addFirst(T x) {
        sentinel.next = new Node(sentinel, x, sentinel.next);
        if (size == 0){
            sentinel.prev = sentinel.next;
        }
        size += 1;
    }
    //  Add item to the end of the list. If the list is empty, same to add first.
    @Override
    public void addLast(T x) {
        Node last = sentinel.prev;
        if (last == null){
            addFirst(x);
        } else {
            last.next = new Node(last, x, sentinel);
            sentinel.prev = last.next;
            size += 1;
        }
    }
    //  Get the items in the list and put them into array list for test purpose.
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node start = sentinel.next;
        for (int i = 0; i < size; i++){
            T itemOfIndex = start.item;
            returnList.add(itemOfIndex);
            start = start.next;
        }

        return returnList;
    }
    //  Check if the list is empty by checking the size.
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
    //  Remove the first item in the list.
    @Override
    public T removeFirst() {
        if (size == 0){
            return null;
        }

        Node first = sentinel.next;
        Node second = first.next;
        T firstItem = first.item;

        second.prev = sentinel;
        sentinel.next = second;

        size -= 1;
        return firstItem;
    }
    //  Remove the last item in the list.
    @Override
    public T removeLast() {
        if (size == 0){
            return null;
        }

        Node last = sentinel.prev;
        Node beforeLast = last.prev;
        T lastItem = last.item;

        sentinel.prev = beforeLast;
        beforeLast.next = sentinel;

        size -= 1;
        return lastItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size -1){
            return null;
        }

        Node nodeOfIndex = sentinel.next;
        for (int i = 0; i < index; i++){
            nodeOfIndex = nodeOfIndex.next;
        }
        T itemOfIndex = nodeOfIndex.item;
        return itemOfIndex;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index > size -1){
            return null;
        } else if (index == 0){
            return sentinel.next.item;
        } else {
            this.removeFirst();
            return getRecursive(index - 1);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(1);
        lld.addLast(2);
        lld.addFirst(5);
        lld.addLast(7);
        lld.removeFirst();
        lld.removeLast();
        lld.get(1);
        lld.size();
    }

}
