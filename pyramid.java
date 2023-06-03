package homework.algo;

public class pyramid {
    public static void main(String args[])
    {
        int array[] = {10, 1, 13, 5, 3, 7};
        printArray(array);
        sort(array);
        printArray(array);

    }



public static void sort(int[] array) {
    for (int i = array.length / 2 - 1; i >= 0; i--) {
        heap(array, array.length, i);
    }

    for (int i = array.length - 1; i >= 0; i--) {
        int temp = array[0];
        array[0] = array[i];
        array[i] = temp;

        heap(array, i, 0);
    }
    
} 

public static void heap(int[] array, int heapSize, int rootIndex) {
    int largest = rootIndex;
    int leftChild = 2 * rootIndex + 1;
    int rightChild = 2 * rootIndex + 2;

    if (leftChild < heapSize && array[leftChild] > array[largest]) {
        largest = leftChild;
    }
    
    if (rightChild < heapSize && array[rightChild] > array[largest]) {
        largest = rightChild;
    }

    if (largest != rootIndex) {
        int temp = array[rootIndex];
        array[rootIndex] = array [largest];
        array[largest] = temp;

        heap(array, heapSize, largest);
    }
}

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}

