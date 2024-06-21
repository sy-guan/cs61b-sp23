package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    // Constructor with a comparator input.
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        T largest = null;
        Iterator<T> arrayIterator = this.iterator();
        while(arrayIterator.hasNext()) {
            T element = arrayIterator.next();
            if (largest == null || c.compare(element,largest)>0) {
                largest = element;
            }
        }
        return largest;
    }
}
