package com.cjj.exer.util;

import java.util.*;

public class TableUtil {

    //本机托管的ip集合
    public static List<String> ipList;

    public static List<String> images;

    //一张图片对应几个ip
    public static HashMap<String, List<String>> table;
    //一张图片只少映射两个ip(2- 4)
    private static final int COUNT = 3;

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
            Random random = new Random();
            //random.nextInt(COUNT) + 2
            for (int k = 0; k < random.nextInt(COUNT) + 2; k++) {
                String image = images.get(i);
                int index = (image.hashCode() & (ipList.size() - 1)) + k;
                index = index & (ipList.size() - 1);

                if (map.containsKey(index)) {
                    map.put(index, map.get(index) + 1);
                } else {
                    map.put(index, 1);
                }
                String reverseIp = ipList.get(index);
                if (reverseTable.containsKey(reverseIp)) {
                    List<String> list = reverseTable.get(reverseIp);
                    list.add(image);
                    reverseTable.put(reverseIp, list);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(image);
                    reverseTable.put(reverseIp, list);
                }
                String ip = ipList.get(index);
                if (table.containsKey(image)) {
                    List<String> list = table.get(image);
                    list.add(ip);
                    table.put(image, list);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(ip);
                    table.put(image, list);
                }
            }
        }

        //网页与资源初始化 80图片分布8个网页
        List<Integer> list = RandomNumber.randomNumber(images.size());
        int i = 1, j = 0;
//        ArrayList<String> tempList = new ArrayList<>();
//        for(; i < ipList.size(); i++) {
//            while(true) {
//                tempList.add(images.get(list.get(j)));
//                j++;
//                if(tempList.size() >= 12) {
//                    break;
//                }
//            }
//            System.out.println("i:" + i);
//            webMap.put(i, new ArrayList<>(tempList));
//            tempList.clear();
//        }
//        while(j < images.size()) {
//            tempList.add(images.get(list.get(j)));
//            j++;
//        }
//        webMap.put(i, new ArrayList<>(tempList));

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
        TableUtil tableUtil = new TableUtil();
        tableUtil.print();
        for (int i = 1; i <= 8; i++) {
            List<String> images = getImages(i);
            System.out.println("index:" + i);
            images.forEach(s -> System.out.println(s));
        }
//        webMap.forEach((k, v)-> System.out.println(k + "==" +  v));


    }

}
