package com.cjj.exer.controller;

import cn.hutool.json.JSONUtil;
import com.cjj.exer.service.LoginService;
import com.cjj.exer.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.cjj.exer.util.TableUtil.ipList;

//@CrossOrigin
@RestController
@RequestMapping("/ip")
public class LoginController {

    @Autowired
    private LoginService loginService;

    private String host = "192.168.100.100";

    private HashMap<String, Set<String>> map = new HashMap<>();

    private List<HostedIp> hostIpList = new ArrayList<>();



    @GetMapping("/{page}")
    public String getImage(@PathVariable Integer page) {
        List<String> ans = new ArrayList<>();

        //存放本次访问域名对应的ip集合
        HashSet<String> set = new HashSet<>();
        HashMap<String, List<String>> table = TableUtil.table;
        //获取图片
        List<String> images = TableUtil.getImages(page);
        for (String image : images) {
            //获取对应的Ip集合
            List<String> ipList = table.get(image);
            //随机选择列表的
            String ip = loginService.chooseIp(host, ipList);
            //优先选择最近的
//            String ip = loginService.chooseIpNear(host, ipList);
            set.add(ip);
            ans.add("http://" + ip + "/imag/all/" + image);
        }
        //主ip
        String hostIp = ipList.get(page - 1);
        //如果主ip在set集合，就去掉
        if(set.contains(hostIp)) {
            set.remove(hostIp);
        }
        map.put(hostIp, set);
        set.forEach(s-> System.out.println(s));
        System.out.println("==================");
        for(Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            Set<String> value = entry.getValue();
            System.out.print(key + '[');
            value.forEach(s-> System.out.println(s));
        }

        System.out.println("==================");
        HostedIp hostedIp = new HostedIp(hostIp, set);
        System.out.println(hostedIp);
        if(hostIpList.size() < TableUtil.ipList.size()) {
            hostIpList.add(hostedIp);
            System.out.println("hostIpList size:");
            System.out.println(hostIpList.size());
        } else {
            System.out.println("=====================");
            TableUtil.print();
            System.out.println("提取的ip列表为：");
            for (HostedIp h : hostIpList) {
                System.out.println(h);
            }
            IpWeight ipWeight = new IpWeight(hostIpList);
            HashMap<String, Double> ipWeightMap = ipWeight.createIpWeight();
            System.out.println("ip权重：");
            ipWeightMap.forEach((k, v)-> System.out.println(k + ":" + v));
            List<String> ipList = new ArrayList<>();
            for (String s : set) {
                ipList.add(s);
            }
            ipList.add(hostIp);
            String ip = ipWeight.checkIp(ipList, hostIpList, ipWeightMap);
            System.out.println("===================");
            System.out.println("本次访问的页面是index" + page);
            System.out.println("预测的ip地址为：" + ip);
            System.out.println("真实的ip地址为：" + TableUtil.ipList.get(page - 1));
        }


        String json = JSONUtil.toJsonStr(ans);
        return json;
    }


    //固定ip
    @GetMapping("/fixed/{page}")
    public String getImage1(@PathVariable Integer page) {
        List<String> ans = new ArrayList<>();

        //存放本次访问域名对应的ip集合
        HashSet<String> set = new HashSet<>();
        HashMap<String, List<String>> table = FixedTableUtil.table;
        //获取图片
        List<String> images = FixedTableUtil.getImages(page);
        for (String image : images) {
            //获取对应的Ip集合
            List<String> ipList = table.get(image);
            //随机选择列表的
            String ip = loginService.chooseIp(host, ipList);
            //优先选择最近的
//            String ip = loginService.chooseIpNear(host, ipList);
            set.add(ip);
            ans.add("http://" + ip + "/imag/all/" + image);
        }
        //主ip
        String hostIp = ipList.get(page - 1);
        //如果主ip在set集合，就去掉
        if(set.contains(hostIp)) {
            set.remove(hostIp);
        }
        map.put(hostIp, set);
        set.forEach(s-> System.out.println(s));
        System.out.println("==================");
        for(Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            Set<String> value = entry.getValue();
            System.out.print(key + '[');
            value.forEach(s-> System.out.println(s));
        }

        System.out.println("==================");
        HostedIp hostedIp = new HostedIp(hostIp, set);
        System.out.println(hostedIp);
        if(hostIpList.size() < FixedTableUtil.ipList.size()) {
            hostIpList.add(hostedIp);
            System.out.println("hostIpList size:");
            System.out.println(hostIpList.size());
        } else {
            System.out.println("=====================");
            FixedTableUtil.print();
            System.out.println("提取的ip列表为：");
            for (HostedIp h : hostIpList) {
                System.out.println(h);
            }
            IpWeight ipWeight = new IpWeight(hostIpList);
            HashMap<String, Double> ipWeightMap = ipWeight.createIpWeight();
            System.out.println("ip权重：");
            ipWeightMap.forEach((k, v)-> System.out.println(k + ":" + v));
            List<String> ipList = new ArrayList<>();
            for (String s : set) {
                ipList.add(s);
            }
            ipList.add(hostIp);
            String ip = ipWeight.checkIp(ipList, hostIpList, ipWeightMap);
            System.out.println("===================");
            System.out.println("本次访问的页面是index" + page);
            System.out.println("预测的ip地址为：" + ip);
            System.out.println("真实的ip地址为：" + FixedTableUtil.ipList.get(page - 1));
        }

        String json = JSONUtil.toJsonStr(ans);
        return json;
    }
}
