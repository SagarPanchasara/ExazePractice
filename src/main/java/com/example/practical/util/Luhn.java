package com.example.practical.util;

public class Luhn
{

    /**
     *
     * @return int
     */
    public static int generate(String number)
    {

        int length = number.length();
        int sum    = 0;
        int weight = 2;
        char[] numberArray = number.toCharArray();

        for (int i = length - 1; i >= 0; i--)
        {
            int digit = weight * (numberArray[i] - '0');
            sum += Math.floor(digit / 10) + digit % 10;
            weight = weight % 2 + 1;
        }

        return (10 - sum % 10) % 10;
    }
}
