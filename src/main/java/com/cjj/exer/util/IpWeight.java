package com.cjj.exer.util;

import cn.hutool.core.lang.hash.Hash;
import org.apache.catalina.Host;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.*;

public class IpWeight {

    //所有主ip及其托管ip的类
    private static List<HostedIp> hostList;

    public IpWeight() {
        hostList = new ArrayList<>();
        hostList.add(new HostedIp("192.168.72.128",
                removeExcessIp("192.168.72.128",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.129",
                removeExcessIp("192.168.72.129",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.130",
                removeExcessIp("192.168.72.130",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.131",
                removeExcessIp("192.168.72.131",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.132",
                removeExcessIp("192.168.72.132",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.133",
                removeExcessIp("192.168.72.133",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.134",
                removeExcessIp(RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.135",
                removeExcessIp("192.168.72.134",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.136",
                removeExcessIp("192.168.72.136",RandomIp.createIpList(10))));
        hostList.add(new HostedIp("192.168.72.137",
                removeExcessIp("192.168.72.137",RandomIp.createIpList(10))));
    }

    //返回ip集合
    public static List<HostedIp> getHostList() {
        return hostList;
    }

    //初始化构造进行去重
    public Set<String> removeExcessIp(String masterIp, List<String> ipList) {
        Set<String> res = new HashSet<>();
        for (String s : ipList) {
            if(s.equals(masterIp)) {
                continue;
            }
            res.add(s);
        }
        return res;
    }

    //对托管ip进行去重
    public static Set<String> removeExcessIp(List<String> ipList) {
        Set<String> res = new HashSet<>();
        for (String s : ipList) {
            res.add(s);
        }
        return res;
    }

    //为每个ip生成权值
    public static HashMap<String, Double> createIpWeight() {
        //统计每个ip被托管在其他ip下的个数(一个ip托管了多个相同ip只算一次)
        HashMap<String, Double> countIp = new HashMap<>();
        //每个主ip初始为1
        for (HostedIp hostedIp : hostList) {
            countIp.put (hostedIp.masterIp, 1d);
        }
        //依次变遍历secondIp集合，每个出现的ip都加1
        for (HostedIp hostedIp : hostList) {
            Set<String> secondIp = hostedIp.secondIp;
            for (String s : secondIp) {
                if (countIp.containsKey(s)) {
                    countIp.put(s, countIp.get(s) + 1);
                }
            }
        }
//        countIp.forEach((k, v)-> System.out.println(k + ":" + v));
        //统计ip权值，每个ip的权值为自己托管ip的平均值
        //-log2P(d)  p(d) = count / size
        HashMap<String, Double> res = new HashMap<>();
        for (Map.Entry<String, Double> entry : countIp.entrySet()) {
            String key = entry.getKey();
            double p = entry.getValue() / countIp.size();
//            System.out.println(p);
            double value = -Math.log(p) / Math.log(2);
            res.put (key, value);
        }
        return res;
    }

    //将未知的ip序列与生成的hostIp进行匹配，
    // 先进行主ip匹配，主ip一样就加入候选集，再进去副ip匹配，选择副ip权值和最大的
    public static String checkIp(List<String> ipList, HashMap<String, Double> ipWeight) {
        //候选集的ip集合
        List<HostedIp> waitList = new ArrayList<>();
        //先对ip序列进行去重
        Set<String> excessIp = removeExcessIp(ipList);
        //进行主ip匹配
        for (HostedIp hostedIp : hostList) {
            //包含主ip，先加入候选集
            if (excessIp.contains(hostedIp.masterIp)) {
                waitList.add(hostedIp);
            }
        }
        //候选集ip集合进行副ip匹配
        HostedIp ans = null;
        double maxWeight = Double.MIN_VALUE;
        for (HostedIp hostedIp : waitList) {
            double weight = 0;
            for (String s : hostedIp.secondIp) {
                //副ip匹配,把权值加入
                if (excessIp.contains(s)) {
                    weight += ipWeight.get(s);
                }
            }
            if (maxWeight < weight) {
                maxWeight = weight;
                ans = hostedIp;
            }
        }
        return ans.masterIp;
    }

    public static void main(String[] args) {

        IpWeight ipWeight1 = new IpWeight();
        System.out.println("第一次所有的ip序列");
        hostList.forEach(k-> System.out.println(k));
        HashMap<String, Double> map = createIpWeight();
        System.out.println("map1");
        map.forEach((k, v)-> System.out.println(k + ":" + v));
        IpWeight ipWeight2 = new IpWeight();
        System.out.println("第二次所有的ip序列");
        hostList.forEach(k-> System.out.println(k));
        HashMap<String, Double> map2 = createIpWeight();
        System.out.println("map2");
        map2.forEach((k, v)-> System.out.println(k + ":" + v));

        List<List<String>> testList = new ArrayList<>();
        System.out.println("================test==================");
        for (int i = 0; i < 5; i++) {
            List<String> ipList = RandomIp.createIpList(10);
            testList.add(new ArrayList<>(ipList));
            System.out.println("ip序列");
            ipList.forEach((k) -> System.out.println(k));
            String ip = checkIp(ipList, map);
            System.out.println("识别后的ip为:" + ip);
        }

        for (List<String> ipList : testList) {
            System.out.println("ip序列");
            ipList.forEach((k) -> System.out.println(k));
            String ip = checkIp(ipList, map2);
            System.out.println("识别后的ip为:" + ip);
        }
        System.out.println("=============");
    }


}

//封装的类，主ip和其托管的ip
class HostedIp {
    //主ip
    public String masterIp;
    //托管ip集合
    public Set<String> secondIp;

    public HostedIp() {

    }

    public HostedIp(String masterIp, Set<String> secondIp) {
        this.masterIp = masterIp;
        this.secondIp = secondIp;
    }

    @Override
    public String toString() {
        return "HostedIp{" +
                "masterIp='" + masterIp + '\'' +
                ", secondIp=" + secondIp +
                '}';
    }
}


