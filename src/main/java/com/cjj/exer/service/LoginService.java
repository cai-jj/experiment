package com.cjj.exer.service;

import com.cjj.exer.util.RandomIp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LoginService {
    //托管的图片数量
    private static final int IMAGE_COUNT = 10;
    public List<String> getIpList() {
        return RandomIp.createIpList(IMAGE_COUNT);
    }
}
