package com.task.dynamic.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * cron表达式工具
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CronUtils {
    public static String getCronByDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss mm HH dd MM ?");
        return simpleDateFormat.format(date);
    }
}
