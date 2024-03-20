package ConcurrentMergeSort;

public class MergeSortThread extends Thread {
    private int[] list;
    private int[] result = null;
    private int startIdx, endIdx;

    public MergeSortThread(int[] list, int startIdx, int endIdx) {
        this.list = list;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    public int[] getResult() {
        return result;
    }

    public boolean isSorted() {
        return result != null;
    }

    public void run() {        
        if(startIdx == endIdx) {
            result = createSubArray();
            return;
        }

        int midIdx = (startIdx + endIdx)/2;
        MergeSortThread leftThread = new MergeSortThread(list, startIdx, midIdx);
        MergeSortThread rightThread = new MergeSortThread(list, midIdx+1, endIdx);

        leftThread.start();
        rightThread.start();

        try {
            leftThread.join();
            rightThread.join();

        } catch(InterruptedException exception) {
            System.out.println(getName() + " one or both of its subthreads got interrupted");
        }

        if(leftThread.isSorted() && rightThread.isSorted()) {
            result = merge(leftThread.getResult(), rightThread.getResult());
        }
    }

    int[] createSubArray() {
        int[] subArray = new int[endIdx - startIdx + 1];
        int index = 0;
        for(int i = startIdx; i <= endIdx; i++) {
            subArray[index] = list[i];
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
