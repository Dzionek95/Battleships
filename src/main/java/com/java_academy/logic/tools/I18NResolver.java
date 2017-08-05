package com.java_academy.logic.tools;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18NResolver {

    private static I18NResolver instance;
    private Locale locale;

    private I18NResolver(Locale locale) {
        this.locale = locale;
    }

    public static void updateLocale(Locale locale) {
        getI18NResolverInstance().locale = locale;
    }
    
    public synchronized static I18NResolver getI18NResolverInstance() {
        if (instance == null){
            instance = new I18NResolver(new Locale("en", "EN"));
        }
        return instance;
    }

    public static String getMsgByKey(String key) {
        return ResourceBundle.getBundle("Messages", getI18NResolverInstance().locale).getString(key);
    }

    public static String getMsgByKey(String key, Object... args){
        return String.format(ResourceBundle.getBundle("Messages", getI18NResolverInstance().locale).getString(key), args);
    }
}
