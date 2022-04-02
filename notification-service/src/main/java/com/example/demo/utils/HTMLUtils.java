package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class HTMLUtils {

    @Autowired
    private ResourceLoader resourceLoader;

    public String getResourceFileAsString(String fileName) throws IOException {
        Resource file = resourceLoader.getResource("classpath:" + fileName);

        return new String(file.getInputStream().readAllBytes());
    }

}
