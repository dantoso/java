package atv1;
import java.util.Random;

public class Sorter {

    public static void main(String[] args) {
        int[] list = generateNumList(5);
        print(list);

        MergeSortThread sortThread = new MergeSortThread(list);
        sortThread.start();

        try {
            sortThread.join();
        } catch(InterruptedException exception) {
            System.out.println(sortThread.getName() + " was interrupted");
        }

        if(sortThread.isSorted()) {
            print(sortThread.getArray());
        } else {
            System.out.println("Program ended with isSorted = false");
        }
    }

    static int[] generateNumList(int len) {
        int[] arr = new int[len];
        Random gen = new Random();

        for(int i = 0; i<len; i++) {
            arr[i] = gen.nextInt(len*10);
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
