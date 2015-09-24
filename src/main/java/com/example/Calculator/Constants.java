package com.example.Calculator;

import java.util.HashMap;

/**
 * Created by ivaa on 9/23/2015.
 */
public class Constants
{
    private static HashMap<String, Double> constants;

    static
    {
        constants = new HashMap<String, Double>();
        Add("Pi", 3.14159);
        Add("e", 2.71);
    }

    public static boolean Add(String name, double value)
    {
        name = name.toLowerCase();
        if (!constants.containsKey(name))
        {
            constants.put(name, value);
            return true;
        }

        return false;
    }

    public static Double Get(String name)
    {
        name = name.toLowerCase();
        if (!constants.containsKey(name))
            return null;

        return constants.get(name);
    }

    public static void Delete(String name)
    {
        name = name.toLowerCase();
        constants.remove(name);
    }

    public static boolean IsExist(String name)
    {
        name = name.toLowerCase();
        return constants.containsKey(name);
    }
}
