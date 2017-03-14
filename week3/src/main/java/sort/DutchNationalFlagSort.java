package sort;

/**
 * Sorts the array so that sorted array is monotonically increasing and totally ordered with
 * RED preceding, preceding WHITE, preceding BLUE.
 *
 * E.g. for input {1, 1, 2, 1, 0, 1, 0, 0}, sorted output is {0, 0, 0, 1, 1, 1, 1, 2}
 *
 * 0 == RED
 * 1 == WHITE
 * 2 == BLUE
 */
public final class DutchNationalFlagSort {

    public static int[] sortedArray(int[] array) {
        int red = 0, white = 0;
        int blue = array.length - 1;

        while (white <= blue){
            if (array[white] == 0 && white <= blue) {
                swap(red, white, array);
                red++;
                white++;
            } else if (array[white] == 1 && white <= blue) {
                white++;
            } else if (array[white] == 2 && white <= blue) {
                swap(white, blue, array);
                blue--;
            } else throw new IllegalStateException();
        }
        return array;
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
