package xyz.kail.javase.demo.sort;

import java.util.Arrays;

/**
 * Created by kail on 2018/4/16.
 */
public class BubbleSort {

    private static void sort(Integer[] arr) {
        int dataLen = arr.length;

        for (int i = 0; i < dataLen - 1; i++) {
            for (int j = 0; j < dataLen - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    Integer temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

        }
    }


    public static void main(String[] args) {
        Integer[] arr = {4, 3, 2, 1};
        sort(arr);
        System.out.println(Arrays.asList(arr));
    }
}
