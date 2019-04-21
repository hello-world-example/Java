package xyz.kail.demo.java.se.sort;

/**
 * Created by kail on 2018/4/16.
 */
public class SortUtil {

    public static <T> void swap(T[] data, int i, int j) {
        if (i == j) {
            return;
        }
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
