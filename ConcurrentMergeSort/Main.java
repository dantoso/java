package ConcurrentMergeSort;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        runRecursiveMergeSort();
    }

    static void runConcurrentMergeSort() {
        // maximum is 6400
        int len = 6400;
        int[] list = generateNumList(len);

        if(len > 300) {
            System.out.println("processing...");
        } else {
            print(list);
        }

        MergeSortThread sortThread = new MergeSortThread(list, 0, list.length-1);

        // long start = System.nanoTime();
        sortThread.run();
        // long end = System.nanoTime();
        // long time = end - start;
        // long ms = time/1000000;

        if(sortThread.isSorted()) {
            System.out.println();

            if(len <= 300) {
                print(sortThread.getResult());
            }

            System.out.println();
            // System.out.println("done in " + ms + " ms");

        } else {
            System.out.println("Program ended with isSorted = false");
        }
    }

    static void runRecursiveMergeSort() {
        int len = 50000000;
        int[] list = generateNumList(len);
        MergeSorter ob = new MergeSorter();

        long start = System.nanoTime();
        ob.sort(list, 0, list.length-1);

        long end = System.nanoTime();
        long time = end - start;
        long ms = time/1000000;

        System.out.println("done in " + ms + " ms");
    }

    static int[] generateNumList(int len) {
        int[] arr = new int[len];
        Random gen = new Random();

        for(int i = 0; i<len; i++) {
            arr[i] = gen.nextInt(len);
        }

        return arr;
    }

    static void print(int[] arr) {
        System.out.print("[ ");
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println("]");
    }
}
