package net.zffu.hollowboard.utils;

public class ParseUtils {

    public static int getFirstCharInStr(String str, int index, char c) {
        for(int i = index; i < str.length(); ++i) {
            if(str.toCharArray()[i] == c) {
                return i;
            }
        }

        return str.length();
    }

    public static int getFirstOpeningCharInStr(String str, int index) {
        for(int i = index; i < str.length(); ++i) {
            char c = str.toCharArray()[i];

            if(c == '{' || c == '%') return i - 1;
        }

        return str.length();
    }

}
