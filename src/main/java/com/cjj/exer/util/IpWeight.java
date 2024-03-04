package com.cjj.exer.util;


import java.util.*;

public class IpWeight {

    //所有主ip及其托管ip的类
    private List<HostedIp> hostList;

    public IpWeight(List<HostedIp> hostList) {
        this.hostList = hostList;
    }

    //返回ip集合
    public List<HostedIp> getHostList() {
        return hostList;
    }


    //为每个ip生成权值
    public HashMap<String, Double> createIpWeight() {
        //统计每个ip被托管在其他ip下的个数(一个ip托管了多个相同ip只算一次)
        HashMap<String, Double> countIp = new HashMap<>();
        //每个主ip初始为1
        for (HostedIp hostedIp : hostList) {
            countIp.put(hostedIp.masterIp, 1d);
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
            res.put(key, value);
        }
        return res;
    }

    //将未知的ip序列与生成的hostIp进行匹配，
    // 先进行主ip匹配，主ip一样就加入候选集，再进去副ip匹配，选择副ip权值和最大的
    public String checkIp(List<String> ipList, List<HostedIp> hostList, HashMap<String, Double> ipWeight) {
        //候选集的ip集合
        List<HostedIp> waitList = new ArrayList<>();
        //先对ip序列进行去重
        Set<String> excessIp = new HashSet<>();
        for (String s : ipList) {
            excessIp.add(s);
        }
        //进行主ip匹配
        for (HostedIp hostedIp : hostList) {
            //包含主ip，先加入候选集
            if (excessIp.contains(hostedIp.masterIp)) {
                waitList.add(hostedIp);
            }
        }
        //候选集ip集合进行副ip匹配
        if(waitList.size() == 0) {
            System.out.println("没有匹配的ip地址");
            return null;
        }
        HostedIp ans = waitList.get(0);
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


}




