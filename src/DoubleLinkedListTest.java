import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Iterator;

//TODO check with my exception
class myException extends Exception {
    public myException() {
        super("My Exception has been discovered!");
    }
}

class DoubleLinkedListTest {

    // Create some objects from previous lab to manipulate with
    private Wagon[] PTWagons = {
            new PTWagon(34, 20, 7, "A"),
            new PTWagon(20, 33, 8, "B"),
            new PTWagon(29, 27, 6, "C")
    };

    private Wagon[] HSWagons = {
            new HSWagon(18, 14, 8, "D"),
            new HSWagon(17, 13, 9, "E"),
            new HSWagon(23, 16, 8, "F")
    };

    private Wagon[] ICWagons = {
            new ICWagon(34, 28, 4, "G"),
            new ICWagon(27, 33, 5, "T"),
            new ICWagon(31, 35, 4, "P")
    };

    private ArrayList<Train> trains;

    // Testing main class
    private DoubleLinkedList<Train> empty;
    private DoubleLinkedList<Train> alone;
    private DoubleLinkedList<Train> fromList;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("Start ...");
        trains = new ArrayList<>();

        trains.add(new PassengerTrain(PTWagons));
        trains.add(new HighSpeedTrain(HSWagons));
        trains.add(new InterCityTrain(ICWagons));

        empty    = new DoubleLinkedList<>();
        alone    = new DoubleLinkedList<>(new PassengerTrain(PTWagons));
        fromList = new DoubleLinkedList<>(trains);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("... End\n");
    }

    @org.junit.jupiter.api.Test
    void size() {
        Assertions.assertEquals(0, empty.size());
        Assertions.assertEquals(1, alone.size());
        Assertions.assertEquals(trains.size(), fromList.size());
        System.out.println("  Size() -> OK");
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        Assertions.assertTrue(empty.isEmpty());
        Assertions.assertFalse(alone.isEmpty());
        Assertions.assertFalse(fromList.isEmpty());
        System.out.println("  isEmpty() -> OK");
    }

    @org.junit.jupiter.api.Test
    void contains() {
        Assertions.assertTrue(alone.contains(new PassengerTrain(PTWagons)));
        Assertions.assertTrue(fromList.contains(new PassengerTrain(PTWagons)));

        alone.add(new PassengerTrain(HSWagons));
        Assertions.assertTrue(alone.contains(new PassengerTrain(HSWagons)));
        Assertions.assertFalse(alone.contains(new PassengerTrain(ICWagons)));

        System.out.println("  contains() -> OK");
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        Iterator iter = fromList.iterator();
        Assertions.assertEquals(new PassengerTrain(PTWagons), iter.next());
        Assertions.assertTrue(iter.hasNext());

        // fromList.size() == 3
        iter.next();
        iter.next();
        Assertions.assertFalse(iter.hasNext());

        System.out.println("  iterator() -> OK");
    }

    @org.junit.jupiter.api.Test
    void toArray() {
        Object[] array = { new PassengerTrain(PTWagons) };
        Assertions.assertEquals(array[0], alone.toArray()[0]);
        Assertions.assertEquals(1, alone.toArray().length);

        System.out.println("  toArray() -> OK");
    }

    @org.junit.jupiter.api.Test
    void toArray1() {
        GrasslandTransport[] gsTransport = new GrasslandTransport[3];
        GrasslandTransport[] newGsTransport = fromList.toArray(gsTransport);

        Assertions.assertEquals(newGsTransport.length, fromList.size());
        Assertions.assertEquals(newGsTransport[0], fromList.get(0));

        System.out.println("  toArray1() -> OK");
    }

    @org.junit.jupiter.api.Test
    void add() {
        int before = alone.size();
        alone.add(new PassengerTrain(PTWagons));
        Assertions.assertEquals(before + 1, alone.size());
        Assertions.assertEquals(alone.get(0), new PassengerTrain(PTWagons));

        System.out.println("  add() -> OK");
    }

    @org.junit.jupiter.api.Test
    void remove() {
        int startSize = fromList.size();

        fromList.remove(0);
        Assertions.assertEquals(startSize - 1, fromList.size());
        Assertions.assertFalse(fromList.contains(new PassengerTrain(PTWagons)));

        System.out.println("  remove() -> OK");
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        Assertions.assertTrue(fromList.containsAll(trains));
        Assertions.assertTrue(trains.containsAll(fromList));

        System.out.println("  containsAll() -> OK");
    }

    @org.junit.jupiter.api.Test
    void addAll() {
        ArrayList<Train> array = new ArrayList<>();

        array.add(new PassengerTrain(PTWagons));
        array.add(new HighSpeedTrain(HSWagons));
        array.add(new InterCityTrain(ICWagons));

        alone.addAll(array);

        Assertions.assertEquals(alone.size(), 4);
        Assertions.assertEquals(alone.get(3), array.get(2));

        System.out.println("  addAll() -> OK");
    }

    @org.junit.jupiter.api.Test
    void addAll1() {
        ArrayList<Train> array = new ArrayList<>();

        array.add(new PassengerTrain(PTWagons));
        array.add(new HighSpeedTrain(HSWagons));
        array.add(new InterCityTrain(ICWagons));

        fromList.addAll(1, array);

        Assertions.assertEquals(6, fromList.size());
        Assertions.assertEquals(array.get(0), fromList.get(1));

        System.out.println("  addAll1() -> OK");
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        fromList.removeAll(trains);
        Assertions.assertTrue(fromList.isEmpty());

        System.out.println("  removeAll() -> OK");
    }

    @org.junit.jupiter.api.Test
    void retainAll() {
        fromList.retainAll(trains.subList(0, 2));
        Assertions.assertEquals(fromList.size(), 2);
        Assertions.assertFalse(fromList.contains(trains.get(2)));

        System.out.println("  retainAll() -> OK");
    }

    @org.junit.jupiter.api.Test
    void clear() {
        fromList.clear();
        Assertions.assertEquals(fromList.size(), 0);
        Assertions.assertTrue(fromList.isEmpty());

        System.out.println("  clear() -> OK");
    }

    @org.junit.jupiter.api.Test
    void get() {
        empty.add(new PassengerTrain(PTWagons));
        Assertions.assertEquals(new PassengerTrain(PTWagons), empty.get(0));
        Assertions.assertEquals(new PassengerTrain(ICWagons), fromList.get(2));

        System.out.println("  get() -> OK");
    }

    @org.junit.jupiter.api.Test
    void set() {
        alone.set(0, new PassengerTrain(ICWagons));
        Assertions.assertEquals(alone.get(0), new PassengerTrain(ICWagons));
        Assertions.assertEquals(2, alone.size());

        System.out.println("  set() -> OK");
    }

    @org.junit.jupiter.api.Test
    void indexOf() {
        int i = 0;
        for (GrasslandTransport gr : fromList) {
            if (gr.equals(new InterCityTrain(ICWagons))) {
                break;
            }
            ++i;
        }
        Assertions.assertEquals(i, fromList.indexOf(new InterCityTrain(ICWagons)));

        System.out.println("  indexOf() -> OK");
    }

    @org.junit.jupiter.api.Test
    void lastIndexOf() {
        alone.add(new PassengerTrain(PTWagons));
        Assertions.assertEquals(1, alone.lastIndexOf(new PassengerTrain(PTWagons)));

        System.out.println("  lastIndexOf() -> OK");
    }

    @org.junit.jupiter.api.Test
    void listIterator() {

    }

    @org.junit.jupiter.api.Test
    void listIterator1() {

    }

    @org.junit.jupiter.api.Test
    void subList() {
        DoubleLinkedList<Train> dll = fromList.subList(1, 2);

        Assertions.assertEquals(dll.size(), 1);
        Assertions.assertEquals(fromList.get(1), dll.get(0));

        System.out.println("  sublist() -> OK");
    }
}