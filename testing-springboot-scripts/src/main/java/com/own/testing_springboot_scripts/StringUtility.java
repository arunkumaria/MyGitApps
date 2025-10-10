package com.own.testing_springboot_scripts;


public class StringUtility {

    public String reverse(String input) {
        if (input == null) throw new IllegalArgumentException("Input cannot be null");
        return new StringBuilder(input).reverse().toString();
    }

    public String toUpperCase(String input) {
        if (input == null) throw new IllegalArgumentException("Input cannot be null");
        return input.toUpperCase();
    }

    public int countVowels(String input) {
        if (input == null) throw new IllegalArgumentException("Input cannot be null");
        int count = 0;
        for (char c : input.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) count++;
        }
        return count;
    }
}
