import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * C2 = 8210 % 2 = 0 => List
 * C3 = 8210 % 3 = 2 => Double linked List
 */

class DoubleLinkedList<T extends GrasslandTransport> implements List<T> {
    private Node head;
    private Node tail;
    private int size;

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public DoubleLinkedList(T someObj) {
        this.head = new Node(someObj);
        this.tail = this.head;
        this.size = 1;
    }

    public DoubleLinkedList(List<T> someList) {
        Node prev, next;
        prev = new Node((GrasslandTransport) someList.toArray()[0]);
        this.head = prev;
        this.size = someList.size();

        for (T smth : someList.subList(1, this.size)) {
            next = new Node(smth);
            prev.setNext(next);
            next.setPrevious(prev);
            prev = next;
        }

        this.tail = prev;
    }

    private class Node {
        private GrasslandTransport data;
        private Node next = null;
        private Node previous = null;

        public Node(GrasslandTransport grasslandTransport) {
            this.data = grasslandTransport;
        }

        public GrasslandTransport getData() {
            return this.data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DoubleLinkedList<?>.Node)) return false;
            Node node = (Node) o;
            return Objects.equals(getData(), node.getData());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getData());
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() {
            return this.next;
        }

        public Node getPrevious() {
            return this.previous;
        }

        @Override
        public String toString() { return this.getData().toString(); }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o)
            throws NullPointerException {
        if (this.isEmpty()) { throw new NullPointerException(); }
        if (this.getHead().equals(o)) { return true; }

        Node next = head.getNext();
        while (next != null) {
            if (next.equals(o)) { return true; }
            next = next.getNext();
        }
        return false;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        class DLLIterator implements Iterator {
            Node current;

            // initialize pointer to head of the list for iteration
            public DLLIterator(DoubleLinkedList<T> list) {
                current = list.getHead();
            }

            // returns false if next element does not exist
            public boolean hasNext() {
                return current != null;
            }

            // return current data and update pointer
            public T next() {
                T data = (T) current.getData();
                current = current.getNext();
                return data;
            }

            public void remove() {}
        }
        return new DLLIterator(this);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Node next = this.getHead().getNext();
        Object[] arr = new Object[this.size()];
        arr[0] = next;
        int i = 1;

        while (next != null) {
            arr[i] = next.getNext();
            next = next.getNext();
            ++i;
        }
        return arr;
    }

    /**
     * Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     *
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to {@code null}.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any null elements.)
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose {@code x} is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of {@code String}:
     *
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     * <p>
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        Node newTail = (new Node(t));

        this.getTail().setNext(newTail);
        newTail.setPrevious(tail);
        this.setTail(newTail);

        this.setSize(this.size() + 1);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (! this.contains(o)) { return false; }

        Node next = this.getHead();
        while (! next.equals(o)) { next = next.getNext(); }

        Node newNext = next.getNext();
        Node newPrev = next.getPrevious();
        newNext.setPrevious(newPrev);
        newPrev.setNext(newNext);

        this.setSize(this.size() - 1);
        return true;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object obj : c) {
            if (! this.contains(obj)) { return false; }
        }

        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        for (Object obj : c) { this.add((T) obj); }
        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c)
            throws IllegalArgumentException {
        if (index > this.size()) { throw new IllegalArgumentException(); }

        Node from = this.getHead();
        for (int i = 0; i < index; ++i) { from = from.getNext(); }

        Node temp = this.getTail();
        this.setTail(from);
        this.addAll(c);

        this.getTail().setNext(temp);
        temp.setPrevious(this.getTail());
        this.setTail(temp);

        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        for (Object obj : c) {
            if (! this.remove(obj)) { return false; }
            this.remove(obj);
        }
        return true;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        for (Object obj : c) { this.remove(obj); }
        return true;
    }

    @Override
    public void clear() {
        this.getHead().setNext(null);
        this.getTail().setPrevious(null);
        this.setSize(0);
    }

    // TODO should delete which from DLL?
    @Override
    public T get(int index)
            throws IndexOutOfBoundsException {
        if (index > this.size()) { throw new IndexOutOfBoundsException(); }

        Node which = this.getHead();
        for (int i = 0; i < index; ++i) { which = which.getNext(); }

        // this.remove(which);

        return (T) which;
    }


    @Override
    public T set(int index, T element)
            throws IndexOutOfBoundsException {
        if (index > this.size()) { throw new IndexOutOfBoundsException(); }
        if (index == this.size()) { this.add(element); }

        Node from = this.getHead();
        for (int i = 0; i < index; ++i) { from = from.getNext(); }

        Node temp = this.getTail();
        this.setTail(from);
        this.add(element);

        this.getTail().setNext(from.getNext());
        from.getNext().setPrevious(this.getTail());
        this.setTail(temp);

        return (T) from.getData();
    }

    @Override
    public void add(int index, T element)
            throws IndexOutOfBoundsException {
        if (index > this.size()) { throw new IndexOutOfBoundsException(); }
        if (index == this.size()) { this.add(element); }

        Node where = this.getTail();
        for (int i = 0; i < index; ++i) { where = where.getPrevious(); }

        Node temp = this.getTail();
        this.setTail(where);
        this.add(element);

        temp.setPrevious(where.getNext());
        where.getNext().setNext(temp);
        this.setTail(temp);
    }

    @Override
    public T remove(int index) {
        T which = this.get(index);
        this.remove(which);

        return which;
    }

    @Override
    public int indexOf(Object o) {
        Node node = this.head;
        for (int i = 0; i < this.size(); ++i) {
            if (node.equals(o)) { return i; }
            node = node.getNext();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node node = this.getTail();
        for (int i = 0; i < this.size(); ++i) {
            if (node.equals(o)) { return i; }
            node = node.getPrevious();
        }
        return -1;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     * <p>
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>{@code
     *      list.subList(from, to).clear();
     * }</pre>
     * Similar idioms may be constructed for {@code indexOf} and
     * {@code lastIndexOf}, and all of the algorithms in the
     * {@code Collections} class can be applied to a subList.<p>
     * <p>
     * The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *                                   ({@code fromIndex < 0 || toIndex > size ||
     *                                   fromIndex > toIndex})
     */
    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex)
            throws IndexOutOfBoundsException {
        if (fromIndex > toIndex && toIndex > this.size() && fromIndex < 0) {
            throw new IndexOutOfBoundsException();
        }

        DoubleLinkedList<T> sublist = new DoubleLinkedList<>();
        sublist.setHead( (Node) this.get(fromIndex) );
        sublist.setTail( ((Node) this.get(toIndex)).getPrevious() );

        int sublistSize = 0;
        Node next = sublist.getHead();
        while (! next.equals(sublist.getTail())) {
            next = next.getNext();
            ++sublistSize;
        }

        sublist.setSize(sublistSize);
        return sublist;
    }

    private void setHead(Node node) { this.head = node; }
    private void setTail(Node node) { this.tail = node; }
    private void setSize(int size)  { this.size = size; }

    private Node getHead() { return this.head; }
    private Node getTail() { return this.tail; }

    @Override
    public String toString() {
        return "Current node HEAD: " + this.getHead()
                + "\nCurrent node TAIL: " + this.getTail() +
                 "\nSize: " + this.size();
    }
}


public class lab_7 {
    public static void main(String[] args) {
        // Create some objects from previous lab to manipulate with
        Wagon[] PTWagons = {
                new PTWagon(34, 20, 7, 1),
                new PTWagon(20, 33, 8, 2),
                new PTWagon(29, 27, 6, 3)
        };

        Wagon[] HSWagons = {
                new HSWagon(18, 14, 8, 1),
                new HSWagon(17, 13, 9, 2),
                new HSWagon(23, 16, 8, 3)
        };

        Wagon[] ICWagons = {
                new ICWagon(34, 28, 4, 1),
                new ICWagon(27, 33, 5, 2),
                new ICWagon(31, 35, 4, 3)
        };

        ArrayList<Train> trains = new ArrayList<>();
        trains.add(new PassengerTrain(PTWagons));
        trains.add(new HighSpeedTrain(HSWagons));
        trains.add(new InterCityTrain(ICWagons));

        // Testing main class
        DoubleLinkedList<Train> empty    = new DoubleLinkedList<>();
        DoubleLinkedList<Train> alone    = new DoubleLinkedList<>(new PassengerTrain(PTWagons));
        DoubleLinkedList<Train> fromList = new DoubleLinkedList<>(trains);

        fromList.add(1, new Train(HSWagons));
        for (Train train : fromList) {
            System.out.println(train);
        }
    }
}
