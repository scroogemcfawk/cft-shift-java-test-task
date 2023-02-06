package merge_sort.sort;

import merge_sort.config.Config;
import merge_sort.enumTypes.SortOrder;

public class Comparator
{
    public static <T extends Comparable<? super T>> boolean inOrderStatic(T a, T b, Config c) {
        return a.compareTo(b) < 0 == (c.getSortOrder() == SortOrder.ASCENDING);
    }
}
