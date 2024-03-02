package com.test.task.bank.utils;

import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@UtilityClass
public class DateHelper {
    public ZonedDateTime fromDate(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
    }
}
