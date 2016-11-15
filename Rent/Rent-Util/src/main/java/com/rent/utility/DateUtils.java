package com.rent.utility;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by duck on 11/1/16.
 */
public class DateUtils {
    public static Timestamp getCurrentUtcTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static Timestamp getUtcTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }
}
