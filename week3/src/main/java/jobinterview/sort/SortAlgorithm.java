package jobinterview.sort;

import jobinterview.sort.heapsort.MyHeapsort;
import jobinterview.sort.mergesort.MyBottomUpMergeSort;
import jobinterview.sort.mergesort.MyTopDownMergeSort;
import jobinterview.sort.quicksort.MyQuickSort;
import jobinterview.sort.quicksort.MyQuickSort3Way;
import jobinterview.sort.quicksort.MyQuickSortEntropyOptimal;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum SortAlgorithm {

    SELECTION(MySelection::sort, MySelection::sort, "Selection"),
    INSERTION(MyInsertion::sort, MyInsertion::sort, "Insertion"),
    SHELL(MyShell::sort, MyShell::sort, "Shell"),
    SHELL_VIA_ARRAY(MyShellViaArray::sort, MyShellViaArray::sort, "Shell (array-backed)"),
    MERGE_BOTTOM_UP(MyBottomUpMergeSort::sort, MyBottomUpMergeSort::sort, "Merge (Bottom Up)"),
    MERGE_TOP_DOWN(MyTopDownMergeSort::sort, MyTopDownMergeSort::sort, "Merge (Top Down)"),
    QUICK(MyQuickSort::sort, MyQuickSort::sort, "Quick"),
    QUICK_3_WAY(MyQuickSort3Way::sort, MyQuickSort3Way::sort, "Quick 3-way"),
    QUICK_3_WAY_ENTROPY_OPTIMAL(MyQuickSortEntropyOptimal::sort, MyQuickSortEntropyOptimal::sort, "Quick 3-way entropy optimal"),
    HEAP(MyHeapsort::sort, null, "Heap");

    private final Consumer sortF;
    private final BiConsumer sortComparatorF;
    private final String text;

    // each sorting algorithm provides two methods - one relying on Comparable and the other relying on Comparator
    <T> SortAlgorithm(Consumer<Comparable<? super T>[]> sortFunction, BiConsumer<T[], Comparator<? super T>> sortWithComparatorF, String name) {
        sortF = sortFunction;
        sortComparatorF = sortWithComparatorF;
        text = name;
    }

    @SuppressWarnings("unchecked") // there is no good way to prevent this warning as arrays are reified and generic types are not
    public <T extends Comparable<? super T>> void sort(T[] array) {
        sortF.accept(array);
    }

    @SuppressWarnings("unchecked") // there is no good way to prevent this warning as arrays are reified and generic types are not
    public <T> void sort(T[] array, Comparator<? super T> comparator) {
        sortComparatorF.accept(array, comparator);
    }

    public String text() {
        return text;
    }
}
