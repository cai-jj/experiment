package com.cjj.exer.util;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomIp {

    //本机托管的ip集合
    private static List<String> ipList;

    private static HashMap<String,List<String>> table = new HashMap<>();

    static {
        //初始化托管ip
        ipList = new ArrayList<>();
//        ipList.add("192.168.1.204");
        ipList.add("192.168.100.10");
        ipList.add("192.168.100.11");
        ipList.add("192.168.100.20");
        ipList.add("192.168.100.21");
        ipList.add("192.168.100.30");
        ipList.add("192.168.100.31");
        ipList.add("192.168.100.40");
        ipList.add("192.168.100.41");
        ipList.add("192.168.100.50");
        ipList.add("192.168.100.51");

    }


    //随机选择ip的算法
    private static String randomChooseIp() {
        Random random = new Random();
        int index = random.nextInt(ipList.size());
        return ipList.get(index);
    }

    //生成给定数量的ip集合
    public static List<String> createIpList(int count) {
        List<String> ips = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ips.add(randomChooseIp());
        }
        return ips;
    }

    public static void main(String[] args) {
        List<String> ips = createIpList(10);
        System.out.println(ips);
    }

}
