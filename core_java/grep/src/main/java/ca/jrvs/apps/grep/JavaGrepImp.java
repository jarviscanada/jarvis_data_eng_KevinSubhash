package ca.jrvs.apps.grep;
import java.io.*;
import java.util.*;

import com.sun.org.slf4j.internal.LoggerFactory;
import com.sun.org.slf4j.internal.Logger;

import java.io.IOException;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args){
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<String>();

        for (File file : listFiles(getRootPath())){
            for (String line : readLines(file)){
                if(containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir){
        List<File> files = new ArrayList<File>();
        File filePath = new File(getRootPath());
        File[] totalFiles = filePath.listFiles();
        if (totalFiles != null) {
            for (File file : totalFiles) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    files.addAll(listFiles(file.getAbsolutePath()));
                }
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile){
            List<String> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(fr);
            String l;
            while ((l = br.readLine()) != null) {
                lines.add(l);
            }
            br.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line){
        return line.matches(this.regex);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException{
        File file = new File(this.outFile);
        FileOutputStream stream = new FileOutputStream(file);
        try {
            OutputStreamWriter osw = new OutputStreamWriter(stream);
            BufferedWriter bw = new BufferedWriter(osw);

            for(String l : lines){
                bw.write(l);
                bw.newLine();
            }
            bw.flush();
            bw.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getRootPath(){
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath){
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex(){
        return this.regex;
    }

    @Override
    public void setRegex(String regex){
        this.regex = regex;
    }

    @Override
    public String getOutFile(){
        return this.outFile;
    }

    @Override
    public void setOutFile(String outFile){
        this.outFile = outFile;
    }

}