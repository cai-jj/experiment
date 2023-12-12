package com.cjj.exer.util;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Image {
    public static List<String> images;
    //初始化所有图片
    static {
        images = new ArrayList<>();
//        String url = Paths.get("").toAbsolutePath().toString();
//        File file = new File(url + "/src/main/resources/static/imag/all");
//        File[] files = file.listFiles();
//        for (File f : files) {
//            images.add(f.getName());
//        }
        for (int i = 1; i <= 100; i++) {
            images.add(i + ".jpg");
        }

    }


    //根据不同的网页获取不同的图片
    public static List<String> getImages(int index) {
        List<String > ans = new ArrayList<>();
        for (int i = (index - 1) * 10; i < index * 10; i++) {
            ans.add(images.get(i));
        }
        return ans;
    }

    public static void main(String[] args) {

    }

}
