package ru.ezhov.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilePatternTreatment {
    protected int countPartName = 2;


    public FileJsonName treatment(String nameFile) throws ParseException {
        String[] names = nameFile.split(AppUtils.DELIMETER_PATTERNS);

        if (names.length != countPartName) {
            throw getException();
        }

        Date date = new SimpleDateFormat(AppUtils.PATTERN_DT_FILE).parse(names[0]);

        String ip = names[1];
        Pattern pattern = Pattern.compile(AppUtils.REGEXP_CHECK_IP);
        Matcher matcher = pattern.matcher(ip);

        if (!matcher.find()) throw getException();

        return new FileJsonName(date, ip);
    }

    private RuntimeException getException() {
        return new IllegalArgumentException(
                "Not correct name file for treatment. Use " +
                        AppUtils.PATTERN_DT_FILE +
                        AppUtils.DELIMETER_PATTERNS +
                        AppUtils.PATTERN_IP_FILE +
                        " template.");
    }

}
