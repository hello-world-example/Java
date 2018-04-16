package xyz.kail.javase.demo.sort;

import java.util.Arrays;

/**
 * Created by kail on 2018/4/16.
 */
public class SelectSort {

    private static void sortAsc(Integer[] arr) {
        int dataLen = arr.length;
        for (int i = 0; i < dataLen; i++) {
            Integer maxIndex = dataLen - i - 1;
            for (int j = 0; j < dataLen - i; j++) {
                if (arr[j] > arr[maxIndex]) {
                    maxIndex = j;
                }
            }
            SortUtil.swap(arr, maxIndex, dataLen - i - 1);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 3, 4, 6, 2, 5, 7, 3};

        sortAsc(arr);

        System.out.println(Arrays.asList(arr));
    }

}
