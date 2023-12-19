package com.cjj.exer.controller;

import cn.hutool.json.JSONUtil;
import com.cjj.exer.service.LoginService;
import com.cjj.exer.util.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/ip")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/{page}")
    public String getImage(@PathVariable Integer page) {
        List<String> ipList = loginService.getIpList();
        List<String> images = Image.getImages(page);
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < ipList.size(); i++) {
            ans.add("http://" + ipList.get(i) + "/imag/all/" + images.get(i));
        }
        String json = JSONUtil.toJsonStr(ans);
        return json;
    }

    //固定ip
    @GetMapping("/fixed/{page}")
    public String getImage1(@PathVariable Integer page) {
        List<String> ipList = new ArrayList<>();
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
        List<String> images = Image.getImages(page);
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < ipList.size(); i++) {
            ans.add("http://" + ipList.get(i) + "/imag/all/" + images.get(i));
        }
        String json = JSONUtil.toJsonStr(ans);
        return json;
    }
}
