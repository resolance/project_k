package ru.ezhov.monitor.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


public class FileNamePatternTreatmentTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void treatmentErrorFile() throws Exception {

        expectedException.expectMessage(
                "[wefwfasgasg] - Not correct name file for treatment. Use yyyyMMddHHmmss_ip.ip.ip.ip template.");
        expectedException.expect(IllegalArgumentException.class);

        FileNamePatternTreatment fileNamePatternTreatment =
                new FileNamePatternTreatment("wefwfasgasg");
        FileJsonName fileJsonName = fileNamePatternTreatment.treatment();


    }

    @Test
    public void treatmentSecondErrorFile() throws Exception {
        expectedException.expectMessage(
                "[12313.13.13123.] - Not correct name file for treatment. Use yyyyMMddHHmmss_ip.ip.ip.ip template.");
        expectedException.expect(IllegalArgumentException.class);


        FileNamePatternTreatment fileNamePatternTreatment
                = new FileNamePatternTreatment("12313.13.13123.");
        FileJsonName fileJsonName = fileNamePatternTreatment.treatment();
    }

    @Test
    public void treatmentThirdErrorFile() throws Exception {
        expectedException.expectMessage(
                "[20170721000803_123.123.123.1235] - Not correct name file for treatment. Use yyyyMMddHHmmss_ip.ip.ip.ip template.");
        expectedException.expect(IllegalArgumentException.class);

        FileNamePatternTreatment fileNamePatternTreatment
                = new FileNamePatternTreatment("20170721000803_123.123.123.1235");
        FileJsonName fileJsonName
                = fileNamePatternTreatment.treatment();

    }

    @Test
    public void treatmentGoodFile() throws Exception {


        FileNamePatternTreatment fileNamePatternTreatment
                = new FileNamePatternTreatment("20170721000803_123.123.123.123.json");
        FileJsonName fileJsonName = fileNamePatternTreatment.treatment();

        assertEquals("FileJsonName{date=Fri Jul 21 00:08:03 MSK 2017, ip=123.123.123.123}", fileJsonName.toString());
    }

}
