package com.cjj.exer.util;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomIp {

    //本机托管的ip集合
    private static List<String> ipList;

    static {
        //初始化托管ip
        ipList = new ArrayList<>();
//        ipList.add("192.168.1.204");
        ipList.add("192.168.67.128");
        ipList.add("192.168.67.129");
        ipList.add("192.168.67.130");
        ipList.add("192.168.67.131");
        ipList.add("192.168.67.132");
        ipList.add("192.168.67.133");
        ipList.add("192.168.67.134");
        ipList.add("192.168.67.135");
        ipList.add("192.168.67.136");
        ipList.add("192.168.67.137");
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
