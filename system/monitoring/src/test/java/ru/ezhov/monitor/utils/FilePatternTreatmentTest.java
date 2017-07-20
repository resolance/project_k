package ru.ezhov.monitor.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilePatternTreatmentTest {
    @Test
    public void treatment() throws Exception {

        String[] names = {
                "wefwfasgasg",
                "12313.13.13123.",
                "20170721000803_123.123.123.123.json",
                "20170721000803_123.123.123.1235",
        };

        for (String s : names) {
            try {
                FilePatternTreatment filePatternTreatment = new FilePatternTreatment();
                FileJsonName fileJsonName = filePatternTreatment.treatment(s);
                System.out.println(s + " - " + fileJsonName);
            } catch (Exception ex) {
                System.out.println(s + " - " + ex.getMessage());
            }
        }


    }

}