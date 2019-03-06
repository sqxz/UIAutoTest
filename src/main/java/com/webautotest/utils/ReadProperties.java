package com.webautotest.utils;

import java.util.ResourceBundle;

public class ReadProperties {


    public static ResourceBundle getBundle(String propertiesName){
        ResourceBundle bundle = ResourceBundle.getBundle(propertiesName);
        return bundle;
    }
}
