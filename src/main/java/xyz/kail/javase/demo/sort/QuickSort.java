package xyz.kail.javase.demo.sort;

import java.util.Arrays;

/**
 * Created by kail on 2018/4/14.
 */
public class QuickSort {

    /**
     * 快速排序 正序
     *
     * @param arr        数据
     * @param beginIndex 起始索引
     * @param endIndex   结束索引
     */
    private static void quickSortAsc(Integer[] arr, int beginIndex, int endIndex) {
        // 起始索引 大于 结束索引，直接返回
        if (beginIndex >= endIndex) {
            return;
        }

        int i = beginIndex; // 起始索引
        int j = endIndex; // 结束索引
        int x = arr[beginIndex]; // 取第一个数作为基准数

        for (; i < j; ) {

            // 从右向左找第一个小于x的数
            while (i < j && arr[j] >= x) {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];

                i++; // 向后移
            }

            // 从左向右找第一个大于等于x的数
            while (i < j && arr[i] < x) {
                i++;
            }
            if (i < j) {
                arr[j] = arr[i];

                j--; // 向前移
            }

        }
        arr[i] = x;
        quickSortAsc(arr, beginIndex, i - 1); // 递归调用
        quickSortAsc(arr, i + 1, endIndex);
    }

    public static void main(String[] args) {

        Integer[] arr = {1, 3, 4, 6, 2, 5, 7, 3};
        quickSortAsc(arr, 0, arr.length - 1);

        System.out.println(Arrays.asList(arr));

    }

}
