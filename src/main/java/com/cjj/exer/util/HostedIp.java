package com.cjj.exer.util;

import java.util.Set;

//封装的类，主ip和其托管的ip
public class HostedIp {
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
