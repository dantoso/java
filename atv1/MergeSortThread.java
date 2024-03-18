package atv1;

public class MergeSortThread extends Thread {
    private int[] array;
    private boolean isSorted = false;

    public MergeSortThread(int[] array) {
        this.array = array;
    }

    public int[] getArray() {
        return array;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void run() {        
        if(array.length == 1) {
            isSorted = true;
            return;
        }

        int midIdx = (array.length - 1)/2;
        int[] left = createSubArray(0, midIdx);
        int[] right = createSubArray(midIdx+1, array.length-1);

        MergeSortThread leftThread = new MergeSortThread(left);
        MergeSortThread rightThread = new MergeSortThread(right);

        leftThread.start();
        rightThread.start();

        try {
            leftThread.join();
            rightThread.join();

        } catch(InterruptedException exception) {
            System.out.println(getName() + " one or both of its subthreads got interrupted");
        }

        if(leftThread.isSorted() && rightThread.isSorted()) {
            array = merge(leftThread.getArray(), rightThread.getArray());
            isSorted = true;
        }
    }

    int[] createSubArray(int startIdx, int endIdx) {
        int[] subArray = new int[endIdx - startIdx + 1];
        int index = 0;
        for(int i = startIdx; i <= endIdx; i++) {
            subArray[index] = array[i];
            index++;
        }

        return subArray;
    }

    int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int l = 0;
        int r = 0;

        while(l < left.length && r < right.length) {
            if(left[l] <= right[r]) {
                result[i] = left[l];
                l++;
            } else {
                result[i] = right[r];
                r++;
            }
            i++;
        }

        while(r < right.length) {
            result[i] = right[r];
            i++;
            r++;
        }
        
        while(l < left.length) {
            result[i] = left[l];
            i++;
            l++;
        }

        return result;
    }
 }
