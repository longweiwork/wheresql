package cn.lw.wheresql.util;

import cn.lw.wheresql.pojo.SearchColumn;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/21
 */
public class StringUtils {

    private final static String UNDERLINE = "_";

    public static String humpToUnderline(String s) {
        StringBuilder sb = new StringBuilder(s);
        int temp = 0;
        if (s.contains(UNDERLINE)) {
            return sb.toString();
        }

        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                sb.insert(i + temp, UNDERLINE);
                temp++;
            }
        }
        return sb.toString().toUpperCase();
    }

}
