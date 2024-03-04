package com.cjj.exer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//每张图片对应一个ip
//每一类图片都在一个服务器ip上
public class FixedTableUtil {
    //本机托管的ip集合
    public static List<String> ipList;

    public static List<String> images;

    //一张图片对应几个ip
    public static HashMap<String, List<String>> table;


    //一个ip有多少图片
    public static HashMap<String, List<String>> reverseTable;

    //统计index的分布
    public static HashMap<Integer, Integer> map = new HashMap<>();

    //一个网页对应的图片
    public static HashMap<Integer, List<String>> webMap = new HashMap<>();

    //图片的数量
    public static final int IMAGE_COUNT = 80;

    static {
        //初始化托管ip
        ipList = new ArrayList<>();
        ipList.add("192.168.100.10");
        ipList.add("192.168.100.11");
        ipList.add("192.168.100.20");
        ipList.add("192.168.100.21");
        ipList.add("192.168.100.30");
        ipList.add("192.168.100.31");
        ipList.add("192.168.100.40");
        ipList.add("192.168.100.41");
//        ipList.add("192.168.100.50");
//        ipList.add("192.168.100.51");
//        ipList.add("192.168.100.60");
//        ipList.add("192.168.100.61");
//        ipList.add("192.168.100.70");
//        ipList.add("192.168.100.71");
//        ipList.add("192.168.100.80");
//        ipList.add("192.168.100.81");
        //初始化图片
        images = new ArrayList<>();
        for (int i = 1; i <= IMAGE_COUNT; i++) {
            images.add(i + ".jpg");
        }

        //初始化映射表
        table = new HashMap<>();
        reverseTable = new HashMap<>();

        for (int i = 0; i < images.size(); i++) {
            int count = i / 10;
            String ip = ipList.get(count);
            String image = images.get(i);
            if(table.containsKey(image)) {
                List<String> list = table.get(image);
                list.add(ip);
                table.put(image, list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(ip);
                table.put(image, list);
            }

            if (reverseTable.containsKey(ip)) {
                List<String> list = reverseTable.get(ip);
                list.add(images.get(i));
                reverseTable.put(ip, list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(images.get(i));
                reverseTable.put(ip, list);
            }

            //服务器图片的数量
            if (map.containsKey(count)) {
                map.put(count, map.get(count) + 1);
            } else {
                map.put(count, 1);
            }
        }

    }

    //根据不同页面获取不同图片
    public static List<String> getImages(int index) {
        //8个页面，100张图片
        List<String> list = new ArrayList<>();
        for (int i = (index - 1) * 10; i < index * 10; i++) {
            list.add(images.get(i));
        }
        return list;
//        List<String> list = webMap.get(index);
//        return list;
    }

    public static void print() {
        table.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("====index========");
        map.forEach((k, v) -> System.out.println(k + ":" + v));

        System.out.println("=========reverseTable==========");
        reverseTable.forEach((k, v) -> System.out.println(k + ":" + v));
    }


    public static void testHashCode() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (String image : images) {
            int index = image.hashCode() & 11;

            if (map.containsKey(index)) {
                map.put(index, map.get(index) + 1);
            } else {
                map.put(index, 1);
            }

        }
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    public static void main(String[] args) {
        FixedTableUtil tableUtil = new FixedTableUtil();
        tableUtil.print();
        for (int i = 1; i <= 8; i++) {
            List<String> images = getImages(i);
            System.out.println("index:" + i);
            images.forEach(s -> System.out.println(s));
        }
//        webMap.forEach((k, v)-> System.out.println(k + "==" +  v));


    }
}
