package xyz.kail.javase.demo.sort;

import java.util.Arrays;

/**
 * Created by kail on 2018/4/16.
 */
public class InsertSort {

    /*
      有序区
        |
        6 5 3 1 7 2  -> 5 6 3 1 7 2  -> 3 5 6 1 7 2  -> 1 3 5 6 7 2 -> 1 3 5 6 7 2  -> 1 2 3 5 6 7
          |                 |                 |                 |                |
        无序区            无序区             无序区             无序区           无序区
    */
    static void insertSort(Integer[] arr) {
        int length = arr.length;


        for (int i = 1; i < length; ++i) {
            int j = i - 1;
            int temp = arr[i];          //待插入数据，即无序区的第一个元素

            while ((j >= 0) && (temp < arr[j]))  //跳出循环的两个条件
            {
                arr[j + 1] = arr[j];    //数据后移
                --j;
            }
            arr[j + 1] = temp;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {6, 5, 3, 1, 7, 2};
        insertSort(arr);

        System.out.println(Arrays.asList(arr));
    }

}
