package com.lms.api.utils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

public class DateHelper {

    public static LocalDate getDateFromXmlGregCalender(XMLGregorianCalendar xmlGregorianCalendar){

        LocalDate localDate = LocalDate.of(
                xmlGregorianCalendar.getYear(),
                xmlGregorianCalendar.getMonth(),
                xmlGregorianCalendar.getDay());

        return localDate;
    }
}
