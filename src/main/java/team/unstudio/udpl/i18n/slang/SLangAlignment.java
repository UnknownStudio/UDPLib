package team.unstudio.udpl.i18n.slang;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface SLangAlignment {
    static String[] align (String separator, String[] list) {
        // 清理注释
        list = Arrays.stream(list).filter(it -> !it.startsWith("#")).collect(Collectors.toList()).toArray(new String[0]);

        // clean
        if (list.length < 1) return list;

        // the first line's string array
        String[] locals = list[0].split(separator);
        String[][] data = new String[list.length][locals.length];
        int[] length = new int[locals.length];
        Arrays.fill(length, 0);

        data[0] = locals;

        // init data array
        for (int i = 1; i < list.length; i++) data[i] = list[i].split(separator);


        for (int i=0;i< data.length;i++) {
            for (int j = 0; j < data[i].length; j++) {
                int savedLength = length[j];
                String lang = data[i][j];
                if (!lang.endsWith("∞")) lang = lang.trim();
                data[i][j] = lang;
                int nowLength = lang.length();
                if (nowLength > savedLength) length[j] = nowLength;
            }
        }

        for (int i=0;i < data.length;i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = String.format("%1$-" + length[j] + "s",data[i][j]);
            }
        }

        return null;
    }
}
