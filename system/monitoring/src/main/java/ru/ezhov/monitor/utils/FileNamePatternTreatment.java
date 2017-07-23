package ru.ezhov.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamePatternTreatment {
    private int countPartName;
    private AppConfig appConfig;
    private String nameFile;

    public FileNamePatternTreatment(String nameFile) {
        this.nameFile = nameFile;
        appConfig = AppConfigInstance.getConfig();
        countPartName = 2;
    }

    public FileJsonName treatment() throws ParseException, IllegalArgumentException {
        String[] names = nameFile.split(appConfig.delimeterPattern());

        if (names.length != countPartName) {
            throw getException();
        }

        Date date = getDate(names[0]);
        String ip = getIp(names[1]);

        return new FileJsonName(date, ip);
    }

    private RuntimeException getException() {
        return new IllegalArgumentException(
                "[" + nameFile + "] " +
                "- Not correct name file for treatment. Use " +
                        appConfig.patternDtFile() +
                        appConfig.delimeterPattern() +
                        String.format("%s.%s.%s.%s", "ip", "ip", "ip", "ip") +
                        " template.");
    }

    private Date getDate(String partDate) throws ParseException {
        return new SimpleDateFormat(appConfig.patternDtFile()).parse(partDate);
    }

    private String getIp(String partIp) {
        //сначала смотрим корректность ip с точкой от расширения файла
        Pattern pattern = Pattern.compile(appConfig.regExpCheckIp());
        Matcher matcher = pattern.matcher(partIp);

        if (!matcher.find()) throw getException();

        //затем берем только ip
        pattern = Pattern.compile(appConfig.regExpOutIp());
        matcher = pattern.matcher(partIp);

        if (!matcher.find()) throw getException();

        return matcher.group();
    }

}
