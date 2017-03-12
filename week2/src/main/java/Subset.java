import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        int x = Integer.valueOf(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) // use CTRL-D in IDEA to signal empty stdin
            q.enqueue(StdIn.readString());

        for (int i = 0; i < x; i++)
            StdOut.print(q.dequeue() + "\n");
    }
}