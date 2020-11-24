package com.gree.testutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Create by yang_zzu on 2020/4/12 on 14:33
 */
public class StringUtilsMy {

    /**
     * 对字符串中所有包含该字符的字符进行替换
     * @param string 字符串
     * @param source 替换的源字符
     * @param target 替换的目标字符
     * @return 字符串
     */
    public String replace(String string, String source, String target) {
        Pattern pattern = compile(source);
        Matcher matcher = pattern.matcher(string);
        String targetString = matcher.replaceAll(target);
        return targetString;
    }

    /**
     * 判断字符串是否是全字符，不包含数字
     * @param string 要检验的字符串
     * @return 是true， 否false
     */
    public Boolean isString(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }



}
