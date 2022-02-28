package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc{

    @Override
    public boolean matchJpeg(String filename) {
        Pattern p = Pattern.compile("(.+\\.jpg$)|(.+\\.jpeg$)");
        Matcher m = p.matcher(filename);
        return m.matches();
    }

    @Override
    public boolean matchIp(String ip) {
        Pattern p = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}");
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    @Override
    public boolean isEmptyLine(String line) {
        Pattern p = Pattern.compile("^\\s*$");
        Matcher m = p.matcher(line);
        return m.matches();
    }
}