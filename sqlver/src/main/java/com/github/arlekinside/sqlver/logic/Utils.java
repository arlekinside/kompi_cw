package com.github.arlekinside.sqlver.logic;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {

    public static <T> T nvl(T input, T def) {
        if (input == null) {
            return def;
        }
        return input;
    }

    public static String preProcess(String sql) {
        if (sql == null) {
            return null;
        }

        sql = sql.trim()
                .toUpperCase(Locale.ROOT)
                .replaceAll("(\\s{2,}|\\n)", " ");

        sql = Utils.removeQuotedStrings(sql);
        return sql;
    }

    public static int countOccurrences(String input, String regex) {
        var matcher = Pattern.compile(regex).matcher(input);
        var result = 0;
        while (matcher.find()) {
            result++;
        }
        return result;
    }

    public static String removeQuotedStrings(String input) {
        return input.replaceAll("'([^']|'')*'", "''");
    }

    public static String extractAndReplace(String input, List<String> extracted, char start, char end, String replacement) {
        Stack<Integer> bracketPositions = new Stack<>();
        StringBuilder modifiedInput = new StringBuilder(input);
        int offset = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == start) {
                bracketPositions.push(i);
            } else if (c == end && !bracketPositions.isEmpty()) {
                int openBracketIndex = bracketPositions.pop();
                if (bracketPositions.isEmpty()) {
                    String extractedText = input.substring(openBracketIndex + 1, i);

                    if (extractedText.indexOf(start) < 0) {
                        extracted.add(extractedText);
                    } else {
                        extracted.add(extractAndReplace(extractedText, extracted, start, end, replacement));
                    }

                    modifiedInput.replace(openBracketIndex - offset, i + 1 - offset, replacement);
                    offset += i - openBracketIndex - replacement.length() + 1; // Adjust offset for the length of replacement
                }
            }
        }

        return modifiedInput.toString();
    }

    public static String splitPopFirst(String line, String regex, List<String> extracted) {
        var splits = new ArrayList<>(List.of(line.split(regex)));
        if (!splits.isEmpty()) {
            line = splits.getFirst();
            splits.remove(line);
            extracted.addAll(splits);
        }
        return line;
    }

    /*
    "select * from (select * from table) "
                + "where name = 'one' and a='' and b='hello from bohdan''s' and var in (select * from (select * from (select * from table) where c='' and a='hell') where b='das''d')";
     */
}
