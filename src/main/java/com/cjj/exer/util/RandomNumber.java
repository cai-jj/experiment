package com.cjj.exer.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumber {
    public static void main(String[] args) {
//        Random random = new Random();
//        int[] arr = new int[100];
//        for (int i = 0; i < arr.length; i++) {
//            int num = random.nextInt(100) + 1; // 生成 1 到 100 的随机数
//            boolean isDuplicate = false;
//            for (int j = 0; j < i; j++) {
//                if (arr[j] == num) { // 判断是否重复
//                    isDuplicate = true;
//                    break;
//                }
//            }
//            if (!isDuplicate) { // 如果不重复，则存入数组
//                arr[i] = num;
//            } else { // 否则，重新生成随机数
//                i--;
//            }
//        }
//
//        for (int i : arr) { // 输出数组中的每个数
//            System.out.println(i);
//        }
//        System.out.println(arr.length);
        List<Integer> list = randomNumber(100);
        list.forEach(s-> System.out.println(s));
        System.out.println(list.size());
    }

    public static List<Integer> randomNumber(int count) {
        Random random = new Random();
        int[] arr = new int[count];
        for (int i = 0; i < arr.length; i++) {
            int num = random.nextInt(count); // 生成 0 到 79 的随机数
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (arr[j] == num) { // 判断是否重复
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) { // 如果不重复，则存入数组
                arr[i] = num;
            } else { // 否则，重新生成随机数
                i--;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for(int i : arr) {
            ans.add(i);
        }
        return ans;
    }

}
