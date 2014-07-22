package com.liang.java8.streams;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.*;

/**
 * @author Briliang
 * @date 2014/7/22
 * Description()
 */
public class FileStreams {
    final static Path path=new File("C:/Users/liang/Desktop/acpwd.txt").toPath();
    public static void main(String[] args) {
        try(Stream<String> lines= Files.lines(path, StandardCharsets.UTF_8)){
            lines.onClose(()-> System.out.println("Done!")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
