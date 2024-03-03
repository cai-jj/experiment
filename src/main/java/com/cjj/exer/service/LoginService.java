package com.cjj.exer.service;

import com.cjj.exer.util.RandomIp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class LoginService {
    //托管的图片数量
    private static final int IMAGE_COUNT = 10;
    public List<String> getIpList() {
        return RandomIp.createIpList(IMAGE_COUNT);
    }


    //根据主机ip选择合适的ip
    public String chooseIp(String host, List<String> ipList) {
        Random random = new Random();
        return ipList.get(random.nextInt(ipList.size()));
    }

    //优先选择离本机最近的ip
    public String chooseIpNear(String host, List<String> ipList) {
        String[] hostSplit = host.split("\\.");
        System.out.println(Arrays.toString(hostSplit));
        String ans = null;
        int max = Integer.MAX_VALUE;
        for (String ip : ipList) {
            String[] split = ip.split("\\.");
            System.out.println(Arrays.toString(split));
            int count = Math.abs(
                    Integer.parseInt(split[split.length - 1]) -
                            Integer.parseInt(hostSplit[hostSplit.length - 1]));
            if(count < max) {
                max = count;
                ans = ip;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String host = "192.168.100.10";

        List<String> list = new ArrayList<>();
        list.add("192.168.100.120");
        list.add("192.168.100.12");
//        list.add("192.168.100.13");
//        list.add("192.168.100.9");
//        list.add("192.168.100.8");
//        list.add("192.168.100.7");
//        list.add("192.168.100.6");
        LoginService loginService = new LoginService();
        String s = loginService.chooseIpNear(host, list);
        System.out.println(s);

    }
}
