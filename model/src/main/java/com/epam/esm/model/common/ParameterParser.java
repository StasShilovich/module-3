package com.epam.esm.model.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterParser {

    private ParameterParser() {
    }

    public static List<String> parseWithAndCondition(String data) {
        Pattern pattern = Pattern.compile("^\\w+&\\w+$");
        Matcher matcher = pattern.matcher(data);
        List<String> result = new ArrayList<>();
        if (matcher.matches()) {
            String[] strings = data.split("&");
            result = Arrays.asList(strings);
        } else {
            result.add(data);
        }
        return result;
    }
}
