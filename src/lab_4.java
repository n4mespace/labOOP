import java.util.Arrays;
import java.util.Objects;

/**
 * C11 = 8210 % 11 = 4
 */

class Warship implements Comparable<Warship> {
    private String name;
    private int height;
    private int length;
    private int numberOfStaff;
    private int numberOfPassangers;

    public Warship(String name) {
        this(name, 230, 40, 30, 15);
    }

    public Warship(String name, int hei, int len) {
        this(name, hei, len, 30, 15);
    }

    public Warship(String name, int hei, int len,
                                int staff, int passengers) {
        this.name               = name;
        this.height             = hei;
        this.length             = len;
        this.numberOfStaff      = staff;
        this.numberOfPassangers = passengers;
    }

    public String getName()            {return name;}
    public int getHeight()             {return height;}
    public int getLength()             {return length;}
    public int getNumberOfStaff()      {return numberOfStaff;}
    public int getNumberOfPassangers() {return numberOfPassangers;}

    static public Warship[] sort(Warship[] warships, String sortBy, boolean reverse) {
        if (warships == null) throw new NullPointerException();

        boolean flag = true;
        if (sortBy.equalsIgnoreCase("name")) {
            for (int i = 0; i < warships.length-1; i++) {
                if ( !flag ) break;
                flag = !flag;
                for (int j = 0; j < warships.length-1; j++) {
                    if (warships[j].getName().length() < warships[j+1].getName().length()) {
                        Warship temp = warships[j];
                        warships[j] = warships[j + 1];
                        warships[j + 1] = temp;
                        flag = true;
                    }
                }

            }
        } else if (sortBy.equalsIgnoreCase("staff")) {
            for (int i = 0; i < warships.length-1; i++) {
                if ( !flag ) break;
                flag = !flag;
                for (int j = 0; j < warships.length-1; j++) {
                    if (warships[j].getNumberOfStaff() > warships[j+1].getNumberOfStaff()) {
                        Warship temp = warships[j];
                        warships[j] = warships[j + 1];
                        warships[j + 1] = temp;
                        flag = true;
                    }
                }
            }
        } else {
            System.out.println("Please enter \"name\" or \"staff\"");
            return warships;
        }

        return (reverse) ? reverse(warships) : warships;
    }

    private static Warship[] reverse(Warship[] warships) {
        Warship[] reversedWarships = new Warship[warships.length];

        int j = 0;
        for (int i = warships.length-1; i >= 0; i--) {
            reversedWarships[j] = warships[i];
            j++;
        }
        return reversedWarships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warship)) return false;
        Warship warship = (Warship) o;
        return height == warship.getHeight() &&
                length == warship.getLength();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), getLength());
    }

    @Override
    public String toString() {
        return this.getName() + this.getHeight() + this.getLength();
    }

    @Override
    public int compareTo(Warship o) {
        return Integer.compare(o.hashCode(), this.hashCode());
    }
}

public class lab_4 {
    public static void main(String[] args) {
        Warship[] warships = {
                new Warship("Kursk", 200, 60, 50,0),
                new Warship("Titanic"),
                new Warship("Glory", 160, 100)
        };

        System.out.println("Sort by number of staff (increasing order):");
        Warship[] oneWarships = Warship.sort(warships, "staff", false);
        for (Warship ship : oneWarships) System.out.println(ship.getName());

        System.out.println("\nSort by length of name (decreasing order):");
        Warship[] twoWarships = Warship.sort(warships, "name", true);
        for (Warship ship : twoWarships) System.out.println(ship.getName());

        /* Sorting by name length using built in functions
        Arrays.sort(warships);
        for (Warship ship : warships) System.out.println(ship.getName());
        */
    }
}
