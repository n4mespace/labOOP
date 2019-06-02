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
        this.setHead(new Node(someObj));
        this.setTail(this.getHead());
        this.size = 1;
    }

    public DoubleLinkedList(List<T> someList) {
        Node prev, next;
        prev = new Node((GrasslandTransport) someList.toArray()[0]);
        this.setHead(prev);
        this.setSize(someList.size());

        for (T smth : someList.subList(1, this.size())) {
            next = new Node(smth);
            prev.setNext(next);
            next.setPrevious(prev);
            prev = next;
        }

        this.setTail(prev);
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
            if (!(o instanceof GrasslandTransport)) return false;
            return o.equals(this.getData());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getData());
        }

        public void setNext(Node next) { this.next = next; }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() { return this.next; }

        public Node getPrevious() { return this.previous; }

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
        if (this.getHead().getData().equals(o)) { return true; }

        Node next = head.getNext();
        while (next != null) {
            if (next.getData().equals(o)) { return true; }
            next = next.getNext();
        }
        return false;
    }

    private class DLLIterator implements Iterator {
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

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new DLLIterator(this);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Node next = this.getHead();
        Object[] arr = new Object[this.size()];
        int i = 0;

        while (next != null) {
            arr[i] = next.getData();
            next = next.getNext();
            ++i;
        }
        return arr;
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        if (!(a instanceof GrasslandTransport[])) throw new ArrayStoreException();
        if (this.size() > a.length) { return  (T1[]) this.toArray(); }

        int i = 0;
        for (Object obj : this.toArray()) {
            a[i] = (T1) obj;
            ++i;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        if (this.isEmpty()) {
            this.setHead(new Node(t));
            this.setSize(1);
            return true;
        }

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

        if (this.size() == 1) {
            this.getHead().setPrevious(null);
            this.getHead().setNext(null);
            this.getTail().setPrevious(null);
            this.getTail().setNext(null);

            this.setHead(null);
            this.setTail(null);
            this.setSize(0);
            return true;
        }

        if (this.indexOf(o) == 0) {
            this.setHead(this.getHead().getNext());
            this.getHead().setPrevious(null);
            this.setSize(this.size() - 1);
            return true;
        }
        if (this.lastIndexOf(o) == this.size()-1) {
            this.setTail(this.getTail().getPrevious());
            this.getTail().setNext(null);
            this.setSize(this.size() - 1);
            return true;
        }

        Node next = this.getHead();
        while (! next.getData().equals(o)) { next = next.getNext(); }

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
        this.setTail(from.getPrevious());
        this.addAll(c);

        this.getTail().setNext(temp);
        temp.setPrevious(this.getTail());
        this.setTail(temp);

        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        for (Object obj : c) {
            if (obj == null) break;
            this.remove(obj);
        }
        return true;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        for (Object t : this.toArray()) {
            if (! c.contains(t))
                this.remove(t);
        }

        return true;
    }

    @Override
    public void clear() {
        this.getHead().setNext(null);
        this.getTail().setPrevious(null);
        this.setSize(0);
    }

    @Override
    public T get(int index)
            throws IndexOutOfBoundsException {
        if (index >= this.size()) { throw new IndexOutOfBoundsException(); }

        Node which = this.getHead();
        for (int i = 0; i < index; ++i) { which = which.getNext(); }

        return (T) which.getData();
    }

    @Override
    public T set(int index, T element)
            throws IndexOutOfBoundsException {
        if (index > this.size()) { throw new IndexOutOfBoundsException(); }
        if (index == this.size()) {
            this.add(element);
            return (T) this.getTail().getPrevious().getData();
        }
        if (index == 0) {
            Node oldHead = this.getHead();
            this.setHead(new Node(element));
            this.getHead().setNext(oldHead);
            oldHead.setPrevious(this.getHead());

            this.setSize(this.size() + 1);
            return (T) this.getHead().getData();
        }

        Node from = this.getHead();
        for (int i = 1; i < index; ++i) { from = from.getNext(); }

        Node temp = this.getTail();
        Node next = from.getNext();
        this.setTail(from);
        this.add(element);

        this.getTail().setNext(next);
        if (next != null)
            next.setPrevious(this.getTail());
        this.setTail(temp);

        return (T) from.getData();
    }

    @Override
    public void add(int index, T element)
            throws IndexOutOfBoundsException {
        this.set(index+1, element);
    }

    @Override
    public T remove(int index) {
        T which = this.get(index);
        this.remove(which);

        return which;
    }

    @Override
    public int indexOf(Object o) {
        Node node = this.getHead();
        for (int i = 0; i < this.size(); ++i) {
            if (node.getData().equals(o)) { return i; }
            node = node.getNext();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node node = this.getTail();
        for (int i = this.size()-1; i >= 0; --i) {
            if (node.getData().equals(o)) { return i; }
            node = node.getPrevious();
        }
        return -1;
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return new DLLlistIterator();
    }

    private class DLLlistIterator implements ListIterator<T> {

        @Override
        public boolean hasNext() {
            return getHead().getNext() != null;
        }

        @Override
        public T next() {
            if (! this.hasNext()) throw new NoSuchElementException();
            head = getHead().getNext();
            return (T) head.getData();
        }

        @Override
        public boolean hasPrevious() {
            return getHead().getPrevious() != null;
        }

        @Override
        public T previous() {
            if (! this.hasPrevious()) throw new NoSuchElementException();
            head = head.getPrevious();
            return (T) head.getData();
        }

        @Override
        public int nextIndex() {
            if (! this.hasNext()) return size();
            return indexOf(getHead().getNext().getData());
        }

        @Override
        public int previousIndex() {
            if (! this.hasPrevious()) return -1;
            return indexOf(getHead().getPrevious());
        }

        @Override
        public void remove() {
            DoubleLinkedList.this.remove(getHead());
        }

        @Override
        public void set(T t) {
            int index = indexOf(head);
            this.remove();
            DoubleLinkedList.this.set(index, t);
        }

        @Override
        public void add(T t) {
            int index = indexOf(head) + 1;
            DoubleLinkedList.this.set(index, t);
        }
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
        ListIterator<T> listIterator = this.listIterator();
        for (int i = 0; i < index; ++i) listIterator.next();
        return listIterator;
    }

    @NotNull
    @Override
    public DoubleLinkedList<T> subList(int fromIndex, int toIndex)
            throws IndexOutOfBoundsException {
        if (fromIndex > toIndex && toIndex > this.size() && fromIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        int size = toIndex - fromIndex;
        DoubleLinkedList<T> sublist = new DoubleLinkedList<>();

        Node forHead = this.getHead();
        for (int i = 0; i < fromIndex; ++i) forHead = forHead.getNext();

        Node forTail = this.getTail();
        for (int i = this.size() - 1; i > this.size() - fromIndex - size; --i)  forTail = forTail.getPrevious();

        sublist.setHead(forHead);
        sublist.setTail(forTail);
        sublist.getHead().setPrevious(null);
        sublist.getTail().setNext(null);
        sublist.setSize(size);

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
                new PTWagon(34, 20, 7, "A"),
                new PTWagon(20, 33, 8, "B"),
                new PTWagon(29, 27, 6, "C")
        };

        Wagon[] HSWagons = {
                new HSWagon(18, 14, 8, "D"),
                new HSWagon(17, 13, 9, "E"),
                new HSWagon(23, 16, 8, "F")
        };

        Wagon[] ICWagons = {
                new ICWagon(34, 28, 4, "G"),
                new ICWagon(27, 33, 5, "T"),
                new ICWagon(31, 35, 4, "P")
        };

        ArrayList<Train> trains = new ArrayList<>();
        trains.add(new PassengerTrain(PTWagons));
        //trains.add(new HighSpeedTrain(HSWagons));
        trains.add(new InterCityTrain(ICWagons));

        // Testing main class
        DoubleLinkedList<Train> empty    = new DoubleLinkedList<>();
        DoubleLinkedList<Train> alone    = new DoubleLinkedList<>(new PassengerTrain(PTWagons));
        DoubleLinkedList<Train> fromList = new DoubleLinkedList<>(trains);

        for (Train train : fromList) { System.out.println(train); }
        System.out.println("<---------------->");

        fromList.add(1, new Train(HSWagons));
        for (Train train : fromList) { System.out.println(train); }

        System.out.println("<---------------->");
        fromList.add(1, new Train(ICWagons));

        for (Train train : fromList) { System.out.println(train); }

        System.out.println(fromList.indexOf(new Train(ICWagons)));
        System.out.println(fromList.lastIndexOf(new Train(ICWagons)));
        System.out.println(fromList.get(2).equals(new Train(ICWagons)));

        System.out.println("\n<---------------->");
        fromList.remove(1);
        fromList.remove(1);
        for (Train train : fromList) { System.out.println(train); }
        System.out.println(fromList.size());

        System.out.println("\n<---------------->");
        Object[] newList = fromList.toArray();
        for (Object obj : newList) { System.out.println(obj); }

        System.out.println("\n<---------------->");
        GrasslandTransport[] l = new GrasslandTransport[2];
        GrasslandTransport[] n = fromList.toArray(l);
        for (GrasslandTransport i : n) {
            System.out.println(i);
        }
    }
}
