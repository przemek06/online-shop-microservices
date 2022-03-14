package com.example.demo.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;

public class HTMLUtils {

    public static String getResourceFileAsString(String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + fileName);

        String content = new String(Files.readAllBytes(((File) file).toPath()));
        return  content;
    }

}
