package jobinterview;

/**
 * 0 == RED
 * 1 == WHITE
 * 2 == BLUE
 * Created by ekis on 12/02/17.
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
