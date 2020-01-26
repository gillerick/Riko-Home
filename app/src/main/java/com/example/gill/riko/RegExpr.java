package com.example.gill.riko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpr {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("ON");
        Matcher matcher = pattern.matcher("Turn on the kitchen lights");

        while (matcher.find())
            System.out.println("Match: " + matcher.group());
    }
}
