import java.util.*;

/**
 * C13 = 8210 % 13 = 7 => Train
 */

interface Carriege extends Comparable<Carriege> {
    int peopleCount();

    int baggageCount();

    int getId();

    int comfortCount();

    @Override
    int compareTo(Carriege o);
}

interface GrasslandTransport {
    int getTotalPeopleCount();

    int getTotalBaggageCount();

    Carriege[] sortByComfort();

    // Find all carriages which contains FROM _ TO _ passengers
    Carriege[] findAvailable(int from, int to);
}

class Wagon implements Carriege {
    private int people;     // FROM 0 TO 54
    private int baggageAll; // FROM 0 TO 36 kg per one
    private int comfort;    // FROM 0 TO 10
    private int id;         // Default number of wagon

    Wagon(int people, int baggagePerOne) { this(people, baggagePerOne, 6, 1); }

    Wagon(int people, int baggagePerOne, int comfort, int id) {
        if ( people > 0 && people < 55 &&
                baggagePerOne > 0 && baggagePerOne < 37 &&
                comfort > 0 && comfort < 11) {
            this.people     = people;
            this.baggageAll = baggagePerOne * this.people; // all baggage
            this.comfort    = comfort;
            this.id         = id;
        } else {
            System.out.println("Enter real data!");
        }
    }

    @Override
    public String toString() {
        return "Wagon " + id +
                "\npeople : " + people
                + "\nbaggage : " + baggageAll
                + "\ncomfort : " + comfort + '\n';
    }

    @Override
    public int peopleCount() { return people; }

    @Override
    public int baggageCount() { return baggageAll; }

    @Override
    public int comfortCount() { return comfort; }

    @Override
    public int getId() { return id; }

    @Override
    public int compareTo(Carriege o) {
        return Integer.compare(o.comfortCount(), comfort);
    }
}

class Train implements GrasslandTransport {
    private Wagon[] wagons;
    private int totalBaggage = 0;
    private int totalPeople  = 0;

    Train(Wagon[] wagons) {
        this.wagons = wagons;

        for (Wagon wagon : this.wagons) {
            this.totalPeople  += wagon.peopleCount();
            this.totalBaggage += wagon.baggageCount();
        }
    }

    public int getTotalPeopleCount() { return totalPeople; }

    public int getTotalBaggageCount() { return totalBaggage; }

    public Carriege[] sortByComfort() {
        Carriege[] wagonsToSort = wagons.clone();
        Arrays.sort(wagonsToSort);
        return wagonsToSort;
    }

    public Carriege[] findAvailable(int from, int to) {
        Carriege[] available = new Wagon[wagons.length];
        int i = 0;

        for (Wagon wagon : wagons) {
            int booked = wagon.peopleCount();
            if (booked >= from && booked <= to) {
                available[i] = wagon;
                i++;
            }
        }

        return available;
    }

    @Override
    public String toString() {
        return "people : " + totalPeople
                + "\nbaggage : " + totalBaggage
                + "\nwagons : " + wagons.length + '\n';
    }
}

class PassengerTrain extends Train {
    PassengerTrain(Wagon[] wagons) { super(wagons); }
}

class HighSpeedTrain extends Train {
    HighSpeedTrain(Wagon[] wagons) { super(wagons); }
}

class InterCityTrain extends Train {
    InterCityTrain(Wagon[] wagons) { super(wagons); }
}

class PTWagon extends Wagon {
    PTWagon(int people, int baggagePerOne) { super(people, baggagePerOne); }
    PTWagon(int people, int baggagePerOne, int comfort, int id) { super(people, baggagePerOne, comfort, id); }
}

class HSWagon extends Wagon {
    HSWagon(int people, int baggagePerOne) { super(people, baggagePerOne); }
    HSWagon(int people, int baggagePerOne, int comfort, int id) { super(people, baggagePerOne, comfort, id); }
}

class ICWagon extends Wagon {
    ICWagon(int people, int baggagePerOne) { super(people, baggagePerOne); }
    ICWagon(int people, int baggagePerOne, int comfort, int id) { super(people, baggagePerOne, comfort, id); }
}

public class lab_6 {
    public static void main(String[] args) {
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

        System.out.println("__Base Passengers Train info__\nPassenger Train");
        Train PTTrain = new PassengerTrain(PTWagons);
        System.out.println(PTTrain);
        System.out.println("__Next one__\nHigh Speed Train");

        Train HSTrain = new HighSpeedTrain(HSWagons);
        System.out.println(HSTrain);
        System.out.println("__Next one__\nInter City Train");

        Train ICTrain = new InterCityTrain(ICWagons);
        System.out.println(ICTrain);

        System.out.println("\n__Sorting by comfort__\nPassenger Train");
        for (Carriege wagon : PTTrain.sortByComfort()) {
            System.out.println(wagon);
        }

        System.out.println("__Next one__\nHigh Speed Train");
        for (Carriege wagon : HSTrain.sortByComfort()) {
            System.out.println(wagon);
        }

        System.out.println("__Next one__\nInter City Train");
        for (Carriege wagon : ICTrain.sortByComfort()) {
            System.out.println(wagon);
        }

        final int FROM = 14;
        final int TO   = 31;

        System.out.println("Find all available wagons with people between " + FROM + " to " + TO + " in Passenger train:");
        for (Carriege wagon : PTTrain.findAvailable(FROM, TO)) {
            if (wagon != null ) System.out.println(wagon);
            else break;
        }

        System.out.println("Find all available wagons with people between " + FROM + " to " + TO + " in High Speed train:");
        for (Carriege wagon : HSTrain.findAvailable(FROM, TO)) {
            if (wagon != null ) System.out.println(wagon);
            else break;
        }

        System.out.println("Find all available wagons with people between " + FROM + " to " + TO + " in Inter City train:");
        for (Carriege wagon : ICTrain.findAvailable(FROM, TO)) {
            if (wagon != null ) System.out.println(wagon);
            else break;
        }

        System.out.println("__That's all for today__");
    }
}
