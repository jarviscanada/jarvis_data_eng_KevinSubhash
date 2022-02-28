package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp{

    public static void main(String[] args){
        if (args.length == 3) {
            JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
            javaGrepLambdaImp.setRegex(args[0]);
            javaGrepLambdaImp.setRootPath(args[1]);
            javaGrepLambdaImp.setOutFile(args[2]);

            try {
                javaGrepLambdaImp.process();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Implement using lambda and stream APIs
     */
    @Override
    public List<String> readLines(File inputFile){
        List<String> list = new ArrayList<>();
        try{
            Files.lines(inputFile.toPath()).collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Implement using lambda and stream APIs
     * @return
     */
    @Override
    public List<File> listFiles(String rootDir){
        List<File> list = new ArrayList<>();
        try{
            Files.walk(Paths.get(rootDir)).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
}