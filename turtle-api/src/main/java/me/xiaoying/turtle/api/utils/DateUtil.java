package me.xiaoying.turtle.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类 日期
 */
public class DateUtil {
    /**
     * 字符串转换成日期或数字形式
     *
     * @param string String
     * @param clazz 需要转换出来的类型
     * @return
     */
    public static <T> T translate(String string, Class<?> clazz) {
        String text = string.substring(0, string.length() - 1);
        String unit = string.substring(string.length() - 1, string.length());
        Long time;
        switch (unit.toUpperCase()) {
            case "D": {
                try {
                    time = Long.parseLong(text);
                    time = 60 * 60 * 24 * time;
                } catch (Exception e) {
                    return null;
                }
                break;
            }
            case "S": {
                try {
                    time = Long.parseLong(text);
                } catch (Exception e) {
                    return null;
                }
                break;
            }
            case "MS": {
                try {
                    time = Long.parseLong(text);
                    time = time * 1000;
                } catch (Exception e) {
                    return null;
                }
                break;
            }
            case "Y": {
                try {
                    time = Long.parseLong(text);
                    time = 60 * 60 * 24 * 30 * 12 * time;
                } catch (Exception e) {
                    return null;
                }
                break;
            }
            case "M": {
                try {
                    time = Long.parseLong(text);
//                    time = 60 * 60 * 24 * 30 * time;
                    time = 60 * time;
                } catch (Exception e) {
                    return null;
                }
                break;
            }
            default:
                try {
                    time = Long.parseLong(string);
                } catch (Exception e) {
                    return null;
                }
        }

        if (Integer.class == clazz || Long.class == clazz)
            return (T) time;

        if (Date.class == clazz) {
            Date date = new Date();
            if (unit.equalsIgnoreCase("MS"))
                date.setTime(date.getTime() + time);
            else
                date.setTime(date.getTime() + time * 1000);
            return (T) date;
        }
        return null;
    }

    /**
     * 获取格式日期
     *
     * @param format 格式
     * @return String*Date
     */
    public static String getDate(String format) {
        return getDate(new Date(), format);
    }

    /**
     * 获取格式日期
     *
     * @param date Date
     * @param format 格式
     * @return String*Date
     */
    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @return 减后时间
     */
    public static long getDateReduce(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }

    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @param format 日期格式
     * @return 减后时间
     */
    public static long getDateReduce(String date, String date1, String format) {
        Date d = null;
        Date d1 = null;
        try {
            d = new SimpleDateFormat(format).parse(date);
            d1 = new SimpleDateFormat(format).parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d != null;
        assert d1 != null;
        return d.getTime() - d1.getTime();
    }

    /**
     * 通过特定格式转换 Date
     *
     * @param date 待被转换的特殊格式的 Date
     * @param format 转换格式
     * @return Date
     */
    public static Date parse(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}