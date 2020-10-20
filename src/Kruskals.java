import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Kruskals {
    public static void main(String[] args) {
        //Read from cvs file
        ArrayList<Edge> EdgeList = new ArrayList<>();
        ArrayList<String> VertexList = new ArrayList<>();
        try {
            File file = new File("assn9_data.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] part = line.split(",");
                System.out.println(line);

                //Read all the first vertex and put in Vertex Array
                VertexList.add(part[0]);

                //Loop through the whole line to read the rest vertices and distance between them
                for (int i = 1; i < part.length; i++) {
                    EdgeList.add(new Edge(Integer.parseInt(part[i + 1]), part[0], part[i]));
                    i++; //Next edge
                }
            }
            System.out.println();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        DisjSets sets = new DisjSets(VertexList.size());

        //Populate the priority queue
        PriorityQueue<Edge> queue = new PriorityQueue<>(EdgeList);

        int sumDistance=0;
        int index = 0;
        while (index < VertexList.size() - 1)
        {
            Edge e = queue.remove();
            //set up to check if adding will create cycle
            int x=sets.find(VertexList.indexOf(e.vertex1));
            int y=sets.find(VertexList.indexOf(e.vertex2));

            if (x !=y) {
                sets.union(x, y);
                System.out.println(e.vertex1 + " to " + e.vertex2 + " : " + e.distance);
                sumDistance+=e.distance;
                index++;
            }
        }
        System.out.println("\nSum of all the distances: "+sumDistance);
    }

    public static class Edge implements Comparable<Edge> {
        int distance;
        String vertex1, vertex2;

        Edge(int distance, String vertex1, String vertex2) {
            this.distance = distance;
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        /*   Compares this object with the specified object for order.  Returns a
             negative integer, zero, or a positive integer as this object is less
             than, equal to, or greater than the specified object.
        */
        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.distance, o.distance);
        }
    }

    public static class DisjSets {
        /**
         * Construct the disjoint sets object.
         * @param numElements the initial number of disjoint sets.
         */
        public DisjSets( int numElements )
        {
            s = new int [ numElements ];
            for( int i = 0; i < s.length; i++ )
                s[ i ] = -1;
        }

        /**
         * Union two disjoint sets using the height heuristic.
         * For simplicity, we assume root1 and root2 are distinct
         * and represent set names.
         * @param root1 the root of set 1.
         * @param root2 the root of set 2.
         */
        public void union( int root1, int root2 )
        {
            if( s[ root2 ] < s[ root1 ] )  // root2 is deeper
                s[ root1 ] = root2;        // Make root2 new root
            else
            {
                if( s[ root1 ] == s[ root2 ] )
                    s[ root1 ]--;          // Update height if same
                s[ root2 ] = root1;        // Make root1 new root
            }
        }

        /**
         * Perform a find with path compression.
         * Error checks omitted again for simplicity.
         * @param x the element being searched for.
         * @return the set containing x.
         */
        public int find( int x )
        {
            if( s[ x ] < 0 )
                return x;
            else
                return s[ x ] = find( s[ x ] );
        }

        private int [ ] s;
    }


}
